package com.example.saladdetector.src.fragments.photo_fragment

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PhotoViewModelFactory(
    private val photoPicker: ActivityResultLauncher<String>,
    private val detectionManager: DetectionManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhotoViewModel(photoPicker, detectionManager) as T
    }
}