package com.example.saladdetector.src.fragments.user_order_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.saladdetector.R
import com.example.saladdetector.src.fragments.order_overview.OrdersRecycleAdapter
import com.example.saladdetector.src.round

class OrderDetails : Fragment() {

    private val orderInfo by lazy {
        val args by navArgs<OrderDetailsArgs>()
        args.orderInfo
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailView: TextView = view.findViewById(R.id.orderDetails_emailTextView)
        val totalView: TextView = view.findViewById(R.id.orderDetails_totalTextView)
        val recycler: RecyclerView = view.findViewById(R.id.orderDetails_recycler)

        emailView.text = orderInfo.email
        totalView.text = requireContext().getString(R.string.price,
            round(orderInfo.totalSum, 2).toString())

        val adapter = OrdersRecycleAdapter()
        recycler.adapter = adapter

        adapter.submitList(orderInfo.products)
    }
}
