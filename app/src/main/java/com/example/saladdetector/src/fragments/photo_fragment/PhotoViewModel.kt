package com.example.saladdetector.src.fragments.photo_fragment

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saladdetector.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class PhotoViewModel(
    private val photoPicker: ActivityResultLauncher<String>
) : ViewModel() {
    private val _takePhoto = MutableLiveData(false)
    val takePhoto: LiveData<Boolean> = _takePhoto

    private val _bitmap = MutableLiveData<Bitmap?>(null)
    val bitmap: LiveData<Bitmap?> = _bitmap

    private val _imageUri = MutableLiveData<Uri?>(null)
    val imageUri: LiveData<Uri?> = _imageUri

    private val _waitingForPhotoToast = MutableLiveData(false)
    val waitingForPhotoToast: LiveData<Boolean> = _waitingForPhotoToast

    val btnListener = View.OnClickListener {
        when (it.id) {
            R.id.photoFragment_takePhotoFab -> {
                _takePhoto.value = true
            }
            R.id.photoFragment_choosePhotoFab -> {
                photoPicker.launch(MIME_IMAGE)
            }
            R.id.photoFragment_confirmPhotoFab -> {}
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

    companion object {
        const val MIME_IMAGE = "image/*"
    }
}