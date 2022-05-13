package com.example.saladdetector.src.fragments.photo_fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.saladdetector.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotoFragment : Fragment() {
    private val photoPicker = registerForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.gotImageUri(it)
    }
    private val viewModel: PhotoViewModel by viewModels{ PhotoViewModelFactory(photoPicker) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val confirmFab: FloatingActionButton = view.findViewById(R.id.photoFragment_confirmPhotoFab)
        val takePhotoFab: FloatingActionButton = view.findViewById(R.id.photoFragment_takePhotoFab)
        val choosePhotoFab: FloatingActionButton = view.findViewById(R.id.photoFragment_choosePhotoFab)
        val imageView: ImageView = view.findViewById(R.id.photoFragment_imageView)

        confirmFab.setOnClickListener (viewModel.btnListener)
        takePhotoFab.setOnClickListener (viewModel.btnListener)
        choosePhotoFab.setOnClickListener (viewModel.btnListener)


        viewModel.takePhoto.observe(viewLifecycleOwner) {
            if (it) {
                takePhoto()
            }
        }

        viewModel.bitmap.observe(viewLifecycleOwner) {
            imageView.setImageBitmap(it)
        }

        viewModel.imageUri.observe(viewLifecycleOwner) {
            imageView.setImageURI(it)
        }

        viewModel.waitingForPhotoToast.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(),
                    R.string.waitingForPhoto_toast, Toast.LENGTH_LONG).show()
                viewModel.waitingForPhotoToastShown()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            viewModel.photoTaken(data?.extras?.get("data") as Bitmap)
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