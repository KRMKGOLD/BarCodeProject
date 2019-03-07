package com.example.dsm2016.barcodeproject

import android.graphics.Bitmap


import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list.*

var codeArray = arrayListOf<codeData>()
var intentDataArray = arrayListOf<codeData>()

class ListActivity : AppCompatActivity() {
    val listAdapter = ListAdapter(this, codeArray)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        var samepleToastString = ""

        for(index in listAdapter.codeList.indices){
            listAdapter.codeList[index].checked = false
        }
        // 모든 바코드의 checkbox를 false로 바꾸기

        deleteButton.setOnClickListener {
//            for(index in listAdapter.codeList.indices){
//                if(listAdapter.codeList[index].checked) {
//                    samepleToastString += "${index}번째 바코드가 선택되었습니다. "
//                }
//            }
//            Toast.makeText(this, samepleToastString, Toast.LENGTH_SHORT).show()
//            samepleToastString = ""

            for(index in listAdapter.codeList.indices){
                if(listAdapter.codeList[index].checked){
                    listAdapter.codeList.removeAt(index)
                }
            }
            listAdapter.notifyDataSetChanged()
        }

        val codeListData : ArrayList<String> = arrayListOf()
        val codeListImage : ArrayList<Bitmap> = arrayListOf()

        if(intent.getStringArrayListExtra("data") != null) {
            codeListData.addAll( intent.getStringArrayListExtra("data"))

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
