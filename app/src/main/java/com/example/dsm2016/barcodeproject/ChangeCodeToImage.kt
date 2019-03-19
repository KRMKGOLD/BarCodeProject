package com.example.dsm2016.barcodeproject

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class ChangeCodeToImage {
    private val imageWriter = MultiFormatWriter()
    private val codeEncoder = BarcodeEncoder()

    fun getBarCodeImageData(result : String) : Bitmap {
        val width = 320
        val height = 180

        val BCbyteMap = imageWriter.encode(result, BarcodeFormat.CODE_128, width, height)
        val BCbitmap = codeEncoder.createBitmap(BCbyteMap)

        for (i in 0 until width)
            for (j in 0 until height) {
                BCbitmap.setPixel(i, j, if (BCbyteMap.get(i, j)) Color.BLACK else Color.WHITE)
            }

        return BCbitmap
    }

    fun getQRCodeImageData(result : String) : Bitmap {
        val width = 100
        val height = 100

        val codeEncoder = BarcodeEncoder()

        val qrBitMatrix = imageWriter.encode(result, BarcodeFormat.QR_CODE, width, height)
        val qrBitmp = codeEncoder.createBitmap(qrBitMatrix)

        for (i in 0 until width)
            for (j in 0 until height) {
                qrBitmp.setPixel(i, j, if (qrBitMatrix.get(i, j)) Color.BLACK else Color.WHITE)
            }

        return qrBitmp
    }
}