package com.example.saladdetector.src.fragments.home_fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.saladdetector.R

class PasswordDialogFragment : DialogFragment(R.layout.dialog_password) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AppCompatEditText>(R.id.passwordET)
            .addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (s.toString() == "admin") {
                            findNavController().navigate(R.id.action_passwordDialogFragment_to_ordersFragment)
                            dismiss()
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {}
                }
            )
    }
}