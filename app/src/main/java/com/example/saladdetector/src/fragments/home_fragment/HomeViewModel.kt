package com.example.saladdetector.src.fragments.home_fragment

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saladdetector.R
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel: ViewModel() {
    private val _navigateToPhotoFragment = MutableLiveData(false)
    val navigateToPhotoFragment = _navigateToPhotoFragment

    private val _navigateToAllOrdersFragment = MutableLiveData(false)
    val navigateToAllOrdersFragment = _navigateToAllOrdersFragment

    val btnListener = View.OnClickListener {
        when (it.id) {
            R.id.homeFragment_takePhotoBtn -> {
                _navigateToPhotoFragment.value = true
            }
            R.id.homeFragment_allOrdersBtn -> {
                _navigateToAllOrdersFragment.value = true
            }
        }
    }

    fun navigatedToPhotoFragment() {
        _navigateToPhotoFragment.value = false
    }

    fun navigatedToAllOrdersFragment() {
        _navigateToAllOrdersFragment.value = false
    }

}