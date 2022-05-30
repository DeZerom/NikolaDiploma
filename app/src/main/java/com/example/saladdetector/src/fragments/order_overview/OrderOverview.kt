package com.example.saladdetector.src.fragments.order_overview

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.saladdetector.R
import com.example.saladdetector.src.round

class OrderOverview : Fragment(R.layout.fragment_order_overview) {

    private val args by navArgs<OrderOverviewArgs>()
    private val detectedProducts by lazy { args.detectedProducts }
    private val recyclerAdapter = OrdersRecycleAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.ordersOverview_recycler)
        recycler.adapter = recyclerAdapter
        view.findViewById<TextView>(R.id.detectedProductRow_summary).text =
            getString(
                R.string.price,
                round(detectedProducts.sumOf { it.price }, 2).toString()
            )
        recyclerAdapter.submitList(detectedProducts.toList())
    }
}