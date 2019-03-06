package com.example.dsm2016.barcodeproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.zxing.MultiFormatWriter
import com.google.zxing.integration.android.IntentIntegrator
import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import android.graphics.Color
import com.google.zxing.common.BitMatrix
import com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var codeResult : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkListButton.setOnClickListener {
            val activityIntent = Intent(this, ListActivity::class.java)
            if(!(codeResult.isEmpty())) {
                activityIntent.putExtra("data", codeResult)
                codeResult = ""
                startActivity(activityIntent)
            }
            else {
                startActivity(activityIntent)
                // dialog
            }
//            ListActivity().listAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.scanButton -> {
                runCaptureActivity(); true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null){
            if(result.contents == null) {
                Toast.makeText(this, "스캔에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "스캔에 성공했습니다. Data : ${result.contents}", Toast.LENGTH_SHORT).show()
//                Log.d("Succeed scan Code", result.contents)
                codeResult = result.contents
            }
        }

    }

    private fun runCaptureActivity() {
        val integrator = IntentIntegrator(this)
        integrator.captureActivity = CustomCaptureActivity::class.java
        integrator.setOrientationLocked(false)
        integrator.setPrompt("바코드나 QR코드를 사각형 안으로 넣어주세요.")
//        integrator.addExtra("SCAN_MODE", "QR_CODE_MODE")
        integrator.initiateScan()
    }


}