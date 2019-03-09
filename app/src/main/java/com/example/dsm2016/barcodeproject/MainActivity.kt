package com.example.dsm2016.barcodeproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

class MainActivity : AppCompatActivity() {

    var codeResult : ArrayList<String> = arrayListOf()
    var codeFormat : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val pref2 = getSharedPreferences("pref2", Context.MODE_PRIVATE)

        checkListButton.setOnClickListener {
            val activityIntent = Intent(this, ListActivity::class.java)
            if (!(codeResult.isEmpty())) {
                codeResult.reverse()
                codeFormat.reverse()
                activityIntent.putExtra("data", codeResult)
                activityIntent.putExtra("format", codeFormat)
                startActivity(activityIntent)
            } else startActivity(activityIntent)

            codeResult.clear()
            codeFormat.clear()
        }

        saveDataButton.setOnClickListener {
            if(!(codeArray.isEmpty())) {
                val editor1 : SharedPreferences.Editor = pref.edit()
                val editor2 : SharedPreferences.Editor = pref2.edit()

                val saveCodeArray = arrayListOf<String>()
                val saveFormatArray = arrayListOf<String>()

                for(index in codeArray) {
                    saveCodeArray.add(index.content)
                    saveFormatArray.add(index.codeFormat)
                }

                for(index in codeArray.indices) {
                    editor1.putString("array_$index", saveCodeArray[index])
                    editor2.putString("array2_$index", saveFormatArray[index])
                }
                editor1.apply()
                editor2.apply()
                Toast.makeText(this, "데이터를 저장했습니다.", Toast.LENGTH_SHORT).show()

            }
            else {
                Toast.makeText(this, "등록할 데이터가 존재하지 않습니다. 데이터 목록을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        reloadDataButton.setOnClickListener {
            val loadDialog = AlertDialog.Builder(this)
            loadDialog.setTitle("주의!")
            loadDialog.setMessage("불러올 시 모든 리스트 데이터를 삭제하고 덮어씌웁니다. 동의하십니까?")
            loadDialog.setPositiveButton("예"){ _: DialogInterface, _: Int ->
                val reloadCodeData = pref.all
                val reloadFormatData = pref2.all

                if(!reloadCodeData.isEmpty()){
                    var temp = 0
                    for(item in reloadCodeData) {
                        val stringData = reloadCodeData["array_$temp"]
                        val formatData = reloadFormatData["array2_${temp++}"]

                        if(stringData is String && formatData is String){
                            codeResult.add(stringData)
                            codeFormat.add(formatData)
                        }
                    }
                    Toast.makeText(this, "데이터를 불러왔습니다.", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(this, "불러올 데이터가 없습니다.", Toast.LENGTH_SHORT).show()
            }
            loadDialog.setNegativeButton("아니오"){ _: DialogInterface, _: Int -> }
            loadDialog.show()
        }

        deleteDataButton.setOnClickListener {
            val editor1 = pref.edit()
            val editor2 = pref2.edit()

            val loadDialog = AlertDialog.Builder(this)
            loadDialog.setTitle("주의!")
            loadDialog.setMessage("데이터를 삭제할 시 저장된 데이터가 없어집니다.")

            loadDialog.setPositiveButton("예") { _: DialogInterface, _: Int ->
                editor1.clear(); editor2.clear()
                editor1.apply(); editor2.apply()

                Toast.makeText(this, "데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show()
            }
            loadDialog.setNegativeButton("아니오") { _: DialogInterface, _: Int -> }
            loadDialog.show()
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
                codeResult.add(result.contents)
                codeFormat.add(result.formatName)
            }
        }

    }

    private fun runCaptureActivity() {
        val integrator = IntentIntegrator(this)
        integrator.captureActivity = CustomCaptureActivity::class.java
        integrator.setOrientationLocked(false)
        integrator.setPrompt("바코드나 QR코드를 사각형 안으로 넣어주세요.")
        integrator.initiateScan()
    }


}