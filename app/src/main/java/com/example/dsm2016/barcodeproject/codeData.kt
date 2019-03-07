package com.example.dsm2016.barcodeproject

import android.graphics.Bitmap

data class codeData(var codeImage : Bitmap?, var content : String, var checked : Boolean = false, var codeFormat : String)
// codeImage : 이미지뷰, content : 코드 내용
data class codeKindData(val code : String, val kind : String)