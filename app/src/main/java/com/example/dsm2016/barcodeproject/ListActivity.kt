package com.example.dsm2016.barcodeproject

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*

var codeArray = arrayListOf<codeData>()
var intentDataArray = arrayListOf<codeData>()

class ListActivity : AppCompatActivity() {

    val listAdapter = ListAdapter(this, codeArray)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        var codeListData : ArrayList<String> = arrayListOf()
        val codeListImage : ArrayList<Bitmap> = arrayListOf()

        if(intent.getStringArrayListExtra("data") != null) {
            codeListData = intent.getStringArrayListExtra("data")

            for(index in codeListData.indices){
                when(ChangeCodeToImage().isStringNumber(codeListData[index])) {
                    true -> codeListImage.add(ChangeCodeToImage().getBarCodeImageData(codeListData[index]))
                    false -> codeListImage.add(ChangeCodeToImage().getQRCodeImageData(codeListData[index]))
                }
            }

            if(codeListData.lastIndex == codeListImage.lastIndex){
                for(index in 0..codeListData.lastIndex){
                    intentDataArray.add(codeData(codeListImage[index], codeListData[index]))
                }
            }
            codeArray.addAll(intentDataArray)
        }
        intent.removeExtra("data")
        intentDataArray.clear()

        listRecycler.adapter = listAdapter

        val listLayoutManager = LinearLayoutManager(this)
        listRecycler.layoutManager = listLayoutManager
        listRecycler.setHasFixedSize(true)
    }
}
