package com.example.saladdetector.src.fragments.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.saladdetector.R

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toPhotoFr: Button = view.findViewById(R.id.homeFragment_takePhotoBtn)
        val toAllOrdersFr: Button = view.findViewById(R.id.homeFragment_allOrdersBtn)

        toPhotoFr.setOnClickListener (viewModel.btnListener)
        toAllOrdersFr.setOnClickListener (viewModel.btnListener)

        viewModel.navigateToPhotoFragment.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_homeFragment_to_photoFragment)
                viewModel.navigatedToPhotoFragment()
            }
        }

        viewModel.navigateToAllOrdersFragment.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(),
                    "Navigated to all orders", Toast.LENGTH_SHORT).show()
                viewModel.navigatedToAllOrdersFragment()
            }
        }
    }
}