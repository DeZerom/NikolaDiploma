package com.example.saladdetector.src.fragments.bill_sending

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saladdetector.R

class BillSendingViewModel: ViewModel() {

    private var isValidEmail = false

    private val _wrongEmailToast = MutableLiveData(false)
    val wrongEmailToast: LiveData<Boolean> = _wrongEmailToast

    private val _navigateToHome = MutableLiveData(false)
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    val textChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            s?: run {
                isValidEmail = false
                return
            }

            isValidEmail = s.contains('@') && s.contains('.')
        }
    }

    val btnListener = View.OnClickListener {
        when (it.id) {
            R.id.billSending_doneBtn -> {
                if (isValidEmail) {
                    _navigateToHome.value = true
                } else {
                    _wrongEmailToast.value = true
                }
            }
        }
    }

    fun wrongEmailToastShown() {
        _wrongEmailToast.value = false
    }

    fun navigatedToHome() {
        _navigateToHome.value = false
    }

}