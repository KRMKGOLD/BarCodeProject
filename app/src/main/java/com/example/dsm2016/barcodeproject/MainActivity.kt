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
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkListButton.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
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

                when(isStringNumber(result.contents)){
                    true -> { sampleImage1.setImageBitmap(getBarCodeImageData(result.contents)) }
                    false -> { sampleImage2.setImageBitmap(getQRCodeImageData(result.contents)) }
                }
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

    private val imageWriter = MultiFormatWriter()
    private val codeEncoder = BarcodeEncoder()

    private fun getBarCodeImageData(result : String) :  Bitmap{
        val _1dWidth = 320
        val _1dHeight = 180


        val BCbyteMap = imageWriter.encode(result, BarcodeFormat.CODE_128, _1dWidth, _1dHeight)
//        val BCbitmap = Bitmap.createBitmap(_1dWidth, _1dHeight, Bitmap.Config.ARGB_8888)
        val BCbitmap = codeEncoder.createBitmap(BCbyteMap)

        for (i in 0 until _1dWidth)
            for (j in 0 until _1dHeight) {
                BCbitmap.setPixel(i, j, if (BCbyteMap.get(i, j)) Color.BLACK else Color.WHITE)
                // 바코드 bitmap
            }

        return BCbitmap
    }

    private fun getQRCodeImageData(result : String) : Bitmap{
        val _2dWidth = 100
        val _2dHeight = 100

        val codeEncoder = BarcodeEncoder()

        val QRbyteMap = imageWriter.encode(result, BarcodeFormat.QR_CODE, _2dWidth, _2dHeight)
        val QRbitmap = codeEncoder.createBitmap(QRbyteMap)

        for (i in 0 until _2dWidth)
            for (j in 0 until _2dHeight) {
                QRbitmap.setPixel(i, j, if (QRbyteMap.get(i, j)) Color.BLACK else Color.WHITE)
                // QR코드 bitmap
            }

        return QRbitmap
    }

    fun isStringNumber(data : String) : Boolean {
        return try {
            println("${data.toDouble()}")
            true
        } catch (e : NumberFormatException) {
            false
        }

    }
}