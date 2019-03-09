package com.example.dsm2016.barcodeproject

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.google.zxing.BarcodeFormat
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

        val divider = DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        val codeListData : ArrayList<String> = arrayListOf()
        val codeListImage : ArrayList<Bitmap> = arrayListOf()
        val codeListFormat : ArrayList<String> = arrayListOf()

        if (intent.getStringArrayListExtra("data") != null) {
            val tempCodeArray = intent.getStringArrayListExtra("data")
            val tempFormatArray = intent.getStringArrayListExtra("format")
            codeListData.addAll(tempCodeArray)
            codeListFormat.addAll(tempFormatArray)

            for(index in 0..codeListData.lastIndex){
                when(codeListFormat[index]) {
                    BarcodeFormat.AZTEC.toString(), BarcodeFormat.DATA_MATRIX.toString(), BarcodeFormat.MAXICODE.toString(), BarcodeFormat.QR_CODE.toString() -> {
                        codeListImage.add(ChangeCodeToImage().getQRCodeImageData(codeListData[index]))
                    }
                    else -> {
                        codeListImage.add(ChangeCodeToImage().getBarCodeImageData(codeListData[index]))
                    }
                }
            }

            for(index in 0..codeListData.lastIndex){
                intentDataArray.add(codeData(codeListImage[index], codeListData[index], codeFormat = codeListFormat[index]))
            }

            codeArray.addAll(intentDataArray)

            intent.removeExtra("data")
            intent.removeExtra("format")

            intentDataArray.clear()
        }

        listRecycler.adapter = listAdapter

        val listLayoutManager = LinearLayoutManager(this)
        listRecycler.layoutManager = listLayoutManager
        listRecycler.setHasFixedSize(true)

        listRecycler.addItemDecoration(divider)

        deleteButton.setOnClickListener {
            for(index in codeArray.lastIndex downTo 0){
                if(codeArray[index].checked){
                    codeArray.removeAt(index)
                }
            }
            listAdapter.notifyDataSetChanged()
        }

        selectAllButton.setOnClickListener {
            for(index in codeArray.indices) {
                listAdapter.codeList[index].checked = true
            }

            listAdapter.notifyDataSetChanged()
        }
    }
}