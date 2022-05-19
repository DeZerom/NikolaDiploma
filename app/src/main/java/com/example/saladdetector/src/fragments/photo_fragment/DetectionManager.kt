package com.example.saladdetector.src.fragments.photo_fragment

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
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

    fun detect(bitmap: Bitmap): Array<String> {
        val image = TensorImage.fromBitmap(bitmap)
        val results = detector.detect(image)

        val tmp = ArrayList<String>(results.size)
        for (det in results) {
            for (cat in det.categories) {
                val label = cat.label
                if (!tmp.contains(label)) tmp.add(label)
            }
        }

        return tmp.toTypedArray()
    }
}