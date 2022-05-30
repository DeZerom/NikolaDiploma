package com.example.saladdetector.src.fragments.photo_fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.RectF
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.saladdetector.R
import com.example.saladdetector.src.repos.ProductRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private val photoPicker = registerForActivityResult(ActivityResultContracts.GetContent()) {
        progressBar.isVisible = true
        viewModel.gotImageUri(it)
    }
    private val viewModel: PhotoViewModel by viewModels {
        PhotoViewModelFactory(photoPicker, DetectionManager(requireContext()),
            ProductRepository(requireContext()))
    }

    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val confirmFab: FloatingActionButton = view.findViewById(R.id.photoFragment_confirmPhotoFab)
        val takePhotoFab: FloatingActionButton = view.findViewById(R.id.photoFragment_takePhotoFab)
        val choosePhotoFab: FloatingActionButton = view.findViewById(R.id.photoFragment_choosePhotoFab)
        val imageView: ImageView = view.findViewById(R.id.photoFragment_imageView)
        progressBar = view.findViewById(R.id.photoFragment_progressBar)

        confirmFab.setOnClickListener(viewModel.btnListener)
        takePhotoFab.setOnClickListener(viewModel.btnListener)
        choosePhotoFab.setOnClickListener(viewModel.btnListener)


        viewModel.isLoading.observe(viewLifecycleOwner) {
            progressBar.isVisible = it
        }

        viewModel.takePhoto.observe(viewLifecycleOwner) {
            if (it) takePhoto()
        }

        viewModel.bitmap.observe(viewLifecycleOwner) {
            imageView.setImageBitmap(it)
        }

        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            uri ?: return@observe
            imageView.setImageURI(uri)
            val bitmap = imageView.drawable.toBitmap()
            viewModel.gotBitmap(bitmap)
        }

        viewModel.waitingForPhotoToast.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    requireContext(),
                    R.string.waitingForPhoto_toast, Toast.LENGTH_LONG
                ).show()
                viewModel.waitingForPhotoToastShown()
            }
        }

        viewModel.detectedProducts.observe(viewLifecycleOwner) {
            it ?: return@observe
        }

        viewModel.navigateToOrderScreen.observe(viewLifecycleOwner) {
            it ?: return@observe
            val a = PhotoFragmentDirections.actionPhotoFragmentToOrderOverview(it)
            findNavController().navigate(a)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            viewModel.photoTaken(data?.extras?.get("data") as Bitmap)
            progressBar.isVisible = true
        }
    }

    private fun takePhoto() {
        viewModel.takePhotoHandled()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        requireActivity().startActivityFromFragment(this, intent, REQUEST_IMAGE_CAPTURE)
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}

data class DetectionResult(val boundingBox: RectF, val text: String)