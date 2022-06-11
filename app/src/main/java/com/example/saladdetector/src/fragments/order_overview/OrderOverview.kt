package com.example.saladdetector.src.fragments.order_overview

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.saladdetector.R
import com.example.saladdetector.src.round

class OrderOverview : Fragment(R.layout.fragment_order_overview) {

    private val args by navArgs<OrderOverviewArgs>()
    private val orderInfo by lazy {args.orderInfo}
    private val recyclerAdapter = OrdersRecycleAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.ordersOverview_recycler)
        recycler.adapter = recyclerAdapter
        view.findViewById<TextView>(R.id.oredersOverview_summary).text =
            getString(
                R.string.price,
                round(orderInfo.totalSum, 2).toString()
            )
        recyclerAdapter.submitList(orderInfo.products.toList())

        view.findViewById<Button>(R.id.orderOverview_pay).setOnClickListener {
            val a = OrderOverviewDirections.actionOrderOverviewToBillSendingFragment(orderInfo)
            findNavController().navigate(a)
        }
    }
}