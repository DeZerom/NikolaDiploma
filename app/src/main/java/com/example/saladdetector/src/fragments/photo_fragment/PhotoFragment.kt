package com.example.saladdetector.src.fragments.photo_fragment

import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.saladdetector.R
import com.example.saladdetector.src.repos.ProductRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private val photoPicker = registerForActivityResult(ActivityResultContracts.GetContent()) {
        progressBar.isVisible = true
        viewModel.gotImageUri(it)
    }

    private val photoTaker = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) viewModel.photoSaved()
    }

    private val viewModel: PhotoViewModel by viewModels()

    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.photoTaker = photoTaker
        viewModel.photoPicker = photoPicker

        val confirmBtn: Button = view.findViewById(R.id.photoFragment_confirmPhotoFab)
        val takePhotoBtn: ImageButton = view.findViewById(R.id.photoFragment_takePhotoFab)
        val choosePhotoBtn: ImageButton = view.findViewById(R.id.photoFragment_choosePhotoFab)
        val imageView: ImageView = view.findViewById(R.id.photoFragment_imageView)
        progressBar = view.findViewById(R.id.photoFragment_progressBar)

        confirmBtn.setOnClickListener(viewModel.btnListener)
        takePhotoBtn.setOnClickListener(viewModel.btnListener)
        choosePhotoBtn.setOnClickListener(viewModel.btnListener)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            progressBar.isVisible = it
        }

        viewModel.detectedProductsBitmap.observe(viewLifecycleOwner) {
            imageView.setImageBitmap(it)
        }

        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            uri ?: return@observe
            imageView.setImageURI(uri)
            val bitmap = imageView.drawable.toBitmap()
            viewModel.gotBitmap(bitmap, requireContext())
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
            confirmBtn.isVisible = it.isNullOrEmpty().not()
        }

        viewModel.navigateToOrderScreen.observe(viewLifecycleOwner) {
            it ?: return@observe
            val a = PhotoFragmentDirections.actionPhotoFragmentToOrderOverview(it)
            findNavController().navigate(a)
        }
    }
}

data class DetectionResult(val boundingBox: RectF, val text: String)