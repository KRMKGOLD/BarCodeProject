package com.example.dsm2016.barcodeproject

import android.graphics.Bitmap


import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.aztec.encoder.AztecCode
import kotlinx.android.synthetic.main.activity_list.*

var codeArray = arrayListOf<codeData>()
var intentDataArray = arrayListOf<codeData>()

class ListActivity : AppCompatActivity() {
    val listAdapter = ListAdapter(this, codeArray)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        for(index in listAdapter.codeList.indices){
            listAdapter.codeList[index].checked = false
        }

        deleteButton.setOnClickListener {
            for(index in listAdapter.codeList.lastIndex downTo 0){
                if(listAdapter.codeList[index].checked){
                    listAdapter.codeList.removeAt(index)
                }
            }
            listAdapter.notifyDataSetChanged()
        }

        val codeListData : ArrayList<String> = arrayListOf()
        val codeListImage : ArrayList<Bitmap> = arrayListOf()
        val codeListFormat : ArrayList<String> = arrayListOf()

        if (intent.getStringArrayListExtra("data") != null) {
            codeListData.addAll(intent.getStringArrayListExtra("data"))
            codeListFormat.addAll(intent.getStringArrayListExtra("format"))

            for(index in codeListData.indices){
                when(codeListFormat[index]) {
                    BarcodeFormat.AZTEC.toString(), BarcodeFormat.DATA_MATRIX.toString(), BarcodeFormat.MAXICODE.toString(), BarcodeFormat.QR_CODE.toString() -> {
                        codeListImage.add(ChangeCodeToImage().getQRCodeImageData(codeListData[index]))
                    }
                    else -> {
                        codeListImage.add(ChangeCodeToImage().getBarCodeImageData(codeListData[index]))
                    }
                }
            }

            if(codeListData.lastIndex == codeListImage.lastIndex){
                for(index in 0..codeListData.lastIndex){
                    intentDataArray.add(codeData(codeListImage[index], codeListData[index]))
                }
            }

            codeArray.addAll(intentDataArray)

            intent.removeExtra("data")
            intentDataArray.clear()
        }

        listRecycler.adapter = listAdapter

        val listLayoutManager = LinearLayoutManager(this)
        listRecycler.layoutManager = listLayoutManager
        listRecycler.setHasFixedSize(true)

    }

}
