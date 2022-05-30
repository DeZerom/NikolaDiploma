package com.example.saladdetector.src.fragments.photo_fragment

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.saladdetector.src.domain_entyties.DetectedProduct
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class DetectionManager(context: Context) {
    private val options = ObjectDetector.ObjectDetectorOptions.builder()
        .setMaxResults(5)
        .setScoreThreshold(0.3f)
        .build()

    private val detector = ObjectDetector.createFromFileAndOptions(
        context,
        "salad.tflite",
        options
    )

    fun detect(bitmap: Bitmap): Array<DetectedProduct> {
        val image = TensorImage.fromBitmap(bitmap)
        val results = detector.detect(image)

        var i = 1
        val tmp = ArrayList<DetectedProduct>(results.size)
        for (det in results) {
            for (cat in det.categories) {
                val detectedProduct = DetectedProduct(cat.label, i++.toDouble())
                if (!tmp.contains(detectedProduct)) tmp.add(detectedProduct)

            }
        }

        return tmp.toTypedArray()
    }
}