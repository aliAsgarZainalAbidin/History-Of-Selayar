package com.example.historyofselayar.utils

import android.content.Context
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.common.Barcode.BarcodeFormat
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_ALL_FORMATS
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(
    val onDetectedCode : (List<Barcode>)-> Unit
): ImageAnalysis.Analyzer {

    @ExperimentalGetImage
    override fun analyze(image: ImageProxy) {
        image.image?.let { imageToAnalyz ->
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(FORMAT_ALL_FORMATS)
                .build()
            val barcodeScanner = BarcodeScanning.getClient(options)
            val imageProcess = InputImage.fromMediaImage(imageToAnalyz,image.imageInfo.rotationDegrees)
            barcodeScanner.process(imageProcess)
                .addOnSuccessListener {
                    if (it != null) {
                        onDetectedCode(it)
                    }
                }
                .addOnCompleteListener {
                    imageToAnalyz.close()
                }
        }
    }

}