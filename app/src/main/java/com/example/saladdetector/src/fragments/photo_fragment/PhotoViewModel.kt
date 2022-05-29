package com.example.saladdetector.src.fragments.photo_fragment

import android.graphics.*
import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saladdetector.R
import com.example.saladdetector.src.domain_entyties.DetectedProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.detector.Detection

class PhotoViewModel(
    private val photoPicker: ActivityResultLauncher<String>,
    private val detectionManager: DetectionManager
) : ViewModel() {
    private val _takePhoto = MutableLiveData(false)
    val takePhoto: LiveData<Boolean> = _takePhoto

    private val _bitmap = MutableLiveData<Bitmap?>(null)
    val bitmap: LiveData<Bitmap?> = _bitmap

    private val _imageUri = MutableLiveData<Uri?>(null)
    val imageUri: LiveData<Uri?> = _imageUri

    private val _waitingForPhotoToast = MutableLiveData(false)
    val waitingForPhotoToast: LiveData<Boolean> = _waitingForPhotoToast

    private var currentBitmap: Bitmap? = null

    private val _detectedProducts = MutableLiveData<Array<DetectedProduct>?>()
    val detectedProducts: LiveData<Array<DetectedProduct>?> = _detectedProducts
    val navigateToOrderScreen = MutableLiveData<Array<DetectedProduct>?>()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val btnListener = View.OnClickListener {
        when (it.id) {
            R.id.photoFragment_takePhotoFab -> {
                _takePhoto.value = true
            }
            R.id.photoFragment_choosePhotoFab -> {
                photoPicker.launch(MIME_IMAGE)
            }
            R.id.photoFragment_confirmPhotoFab -> {
                navigateToOrderScreen.value = _detectedProducts.value
            }
        }
    }

    fun takePhotoHandled() {
        _takePhoto.value = false
    }

    fun waitingForPhotoToastShown() {
        _waitingForPhotoToast.value = false
    }

    fun gotImageUri(uri: Uri?) {
        uri ?: run { _waitingForPhotoToast.value = true }
        _imageUri.value = uri
    }

    fun photoTaken(bitmap: Bitmap?) {
        bitmap ?: run { _waitingForPhotoToast.value = true }
        _bitmap.value = bitmap
    }

    fun gotBitmap(bitmap: Bitmap?) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            bitmap?.let { b ->
                val results: List<Detection> = detectionManager.detect(b) ?: emptyList()
                val tmp = ArrayList<DetectedProduct>(results.size)
                var i = 0 //TODO placeholder
                for (det in results) {
                    for (cat in det.categories) {
                        val prod = DetectedProduct(cat.label, (i++).toDouble())
                        if (!tmp.contains(prod)) tmp.add(prod)
                    }
                }

                _detectedProducts.postValue(tmp.toTypedArray())

                val resultToDisplay = results.map {
                    // Get the top-1 category and craft the display text
                    val category = it.categories.first()
                    val text = "${category.label}, ${category.score.times(100).toInt()}%"

                    // Create a data object to display the detection result
                    DetectionResult(it.boundingBox, text)
                }
                val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)

                currentBitmap = imgWithResult
                _bitmap.postValue(imgWithResult)
                isLoading.postValue(false)
            } ?: run {
                _waitingForPhotoToast.postValue(true)
                isLoading.postValue(false)
            }
        }
    }

    /**
     * Draw a box around each objects and show the object's name.
     */
    private suspend fun drawDetectionResult(
        bitmap: Bitmap,
        detectionResults: List<DetectionResult>
    ): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 4F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)

            val tagSize = Rect(0, 0, 0, 0)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.YELLOW
            pen.strokeWidth = 2F
            pen.textSize = 20f
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            // adjust the font size so texts are inside the bounding box
            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }

    companion object {
        const val MIME_IMAGE = "image/*"
    }
}