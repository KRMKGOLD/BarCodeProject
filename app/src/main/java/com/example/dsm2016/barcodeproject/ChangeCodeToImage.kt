package com.example.dsm2016.barcodeproject

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class ChangeCodeToImage {
    val imageWriter = MultiFormatWriter()
    val codeEncoder = BarcodeEncoder()

    fun isStringNumber(data : String) : Boolean {
        return try {
            println("${data.toDouble()}")
            true
        } catch (e : NumberFormatException) {
            false
        }
    }

    fun getBarCodeImageData(result : String) : Bitmap {
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

    fun getQRCodeImageData(result : String) : Bitmap {
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
}