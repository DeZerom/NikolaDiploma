package com.example.saladdetector.src.fragments.photo_fragment

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.saladdetector.src.repos.ProductRepository

class PhotoViewModelFactory(
    private val photoTaker: ActivityResultLauncher<Uri>,
    private val photoPicker: ActivityResultLauncher<String>,
    private val detectionManager: DetectionManager,
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhotoViewModel(photoTaker, photoPicker, detectionManager, productRepository) as T
    }
}