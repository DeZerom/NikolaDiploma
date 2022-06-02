package com.example.saladdetector.src.fragments.bill_sending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.saladdetector.R
import com.example.saladdetector.src.domain_entyties.OrderInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillSendingFragment : Fragment() {

    private val viewModel: BillSendingViewModel by viewModels()
    private val orderInfo by lazy {
        val args by navArgs<BillSendingFragmentArgs>()
        args.orderInf
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.orderInfo = orderInfo

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_sending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailInp = view.findViewById<EditText>(R.id.billSending_email)
        val btn = view.findViewById<Button>(R.id.billSending_doneBtn)

        emailInp.addTextChangedListener(viewModel.textChangedListener)
        btn.setOnClickListener(viewModel.btnListener)

        viewModel.wrongEmailToast.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), getString(R.string.wrongEmailToast), Toast.LENGTH_LONG).show()
                viewModel.wrongEmailToastShown()
            }
        }

        viewModel.navigateToHome.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_billSendingFragment_to_homeFragment)
                viewModel.navigatedToHome()
            }
        }
    }
}