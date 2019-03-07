package com.example.dsm2016.barcodeproject

import android.graphics.Bitmap

data class codeData(var codeImage : Bitmap?, var content : String?, var check: Boolean = false)
// codeImage : 이미지뷰, content : 코드 내용