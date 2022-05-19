package com.example.saladdetector.src.fragments.order_overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.saladdetector.R

class OrderOverview : Fragment() {

    private val args by navArgs<OrderOverviewArgs>()
    private val detectedProducts by lazy { args.detectedValues }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.textView)

        val builder = StringBuilder()
        detectedProducts.forEach {
            builder.append(it).append(" ")
        }

        textView.text = builder.toString()
    }
}