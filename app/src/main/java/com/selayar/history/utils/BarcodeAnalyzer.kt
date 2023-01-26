package com.selayar.history.utils

import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_ALL_FORMATS
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(
    val onDetectedCode : (List<Barcode>)-> Unit
): ImageAnalysis.Analyzer {

    @ExperimentalGetImage
    override fun analyze(image: ImageProxy) {
        Log.d("TAG", "analyze: Running")
        image.image?.let { imageToAnalyz ->
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(FORMAT_ALL_FORMATS)
                .build()
            val barcodeScanner = BarcodeScanning.getClient(options)
            val imageProcess = InputImage.fromMediaImage(imageToAnalyz,image.imageInfo.rotationDegrees)
            barcodeScanner.process(imageProcess)
                .addOnSuccessListener {
                    if (it.isNotEmpty()){
                        onDetectedCode(it)
                    } else {
                        Log.e("Barocde Analyzer", "analyze: No barcode scanned")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Barocde Analyzer", "Barocde Analyzer: Something went wrong $e")
                }
                .addOnCompleteListener {
                    Log.d("TAG", "analyze: Complete")
                    imageToAnalyz.close()
                }
        }
    }

}