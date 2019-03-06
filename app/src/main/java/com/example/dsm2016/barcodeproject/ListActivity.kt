package com.example.dsm2016.barcodeproject

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*

var codeArray = arrayListOf<codeData>()

class ListActivity : AppCompatActivity() {

    val listAdapter = ListAdapter(this, codeArray)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        var codeData : String
        var codeImage : Bitmap

        if(intent.getStringExtra("data") is String){
            codeData = intent.getStringExtra("data")
            intent.removeExtra("data")

            codeImage = when(ChangeCodeToImage().isStringNumber(codeData)) {
                true -> ChangeCodeToImage().getBarCodeImageData(codeData)
                false -> ChangeCodeToImage().getQRCodeImageData(codeData)
            }
            codeArray.add(codeData(codeImage, codeData))
        }

        listRecycler.adapter = listAdapter

        val listLayoutManager = LinearLayoutManager(this)
        listRecycler.layoutManager = listLayoutManager
        listRecycler.setHasFixedSize(true)

    }
}
