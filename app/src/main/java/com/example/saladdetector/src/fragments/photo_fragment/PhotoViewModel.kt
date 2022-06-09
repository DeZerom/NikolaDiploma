package com.example.saladdetector.src.fragments.photo_fragment

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saladdetector.BuildConfig
import com.example.saladdetector.R
import com.example.saladdetector.src.domain_entyties.DetectedProduct
import com.example.saladdetector.src.domain_entyties.OrderInfo
import com.example.saladdetector.src.repos.ImagesRepository
import com.example.saladdetector.src.repos.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.detector.Detection
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val detectionManager: DetectionManager,
    private val productRepository: ProductRepository,
    private val imagesRepository: ImagesRepository
) : ViewModel() {

    lateinit var photoTaker: ActivityResultLauncher<Uri>
    lateinit var photoPicker: ActivityResultLauncher<String>

    private val orderInfo: OrderInfo = OrderInfo()

    private val _detectedProductsBitmap = MutableLiveData<Bitmap?>(null)
    val detectedProductsBitmap: LiveData<Bitmap?> = _detectedProductsBitmap

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
                orderInfo.imageUri = FileProvider.getUriForFile(it.context,
                    "${BuildConfig.APPLICATION_ID}.provider",
                    File(it.context.filesDir, "rawImages")
                )
                photoTaker.launch(orderInfo.imageUri)
            }
            R.id.photoFragment_choosePhotoFab -> {
                photoPicker.launch(MIME_IMAGE)
            }
            R.id.photoFragment_confirmPhotoFab -> {
                navigateToOrderScreen.value = _detectedProducts.value
            }
        }
    }

    fun waitingForPhotoToastShown() {
        _waitingForPhotoToast.value = false
    }

    fun gotImageUri(uri: Uri?) {
        uri ?: run { _waitingForPhotoToast.value = true }
        _imageUri.value = uri
    }

    fun photoSaved() {
        _imageUri.value = orderInfo.imageUri
    }

    fun gotBitmap(bitmap: Bitmap?, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            bitmap?.let { b ->
                val results: List<Detection> = detectionManager.detect(b) ?: emptyList()
                val tmp = ArrayList<DetectedProduct>(results.size)
                for (det in results) {
                    for (cat in det.categories) {
                        val dbProd = productRepository.getById(cat.index) ?: continue
                        if (tmp.any { return@any it.name == dbProd.name }) {
                            val idx = tmp.indexOf(tmp.find{ return@find it.name == dbProd.name })
                            tmp[idx] = tmp[idx].copy(amount = tmp[idx].amount + 1)
                        } else {
                            val prod = DetectedProduct(
                                name = dbProd.name,
                                price = dbProd.price,
                                weight = dbProd.weight,
                                amount = 1,
                                id = dbProd.id
                            )
                            tmp.add(prod)
                        }
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
                _detectedProductsBitmap.postValue(imgWithResult)
                orderInfo.imageUri = imagesRepository
                    .saveBitmapWithDetectedProducts(imgWithResult, context)
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