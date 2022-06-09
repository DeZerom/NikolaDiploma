package com.example.saladdetector.src.fragments.photo_fragment

import android.content.Context
import android.graphics.Bitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import javax.inject.Inject

class DetectionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val options = ObjectDetector.ObjectDetectorOptions.builder()
        .setMaxResults(50)
        .setScoreThreshold(0.3f)
        .build()

    private val detector = ObjectDetector.createFromFileAndOptions(
        context,
        "salad.tflite",
        options
    )

    suspend fun detect(bitmap: Bitmap): MutableList<Detection>? {
        val image = TensorImage.fromBitmap(bitmap)
        return detector.detect(image)
    }
}