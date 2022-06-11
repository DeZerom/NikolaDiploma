package com.example.saladdetector.src.fragments.orders_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.saladdetector.R
import com.example.saladdetector.src.domain_entyties.OrderInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : Fragment(R.layout.fragment_orders) {

    @Inject lateinit var adapter: UserOrdersRecyclerAdapter

    private val viewModel: OrdersViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler: RecyclerView = view.findViewById(R.id.ordersRV)
        recycler.adapter = adapter

        viewModel.orders.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        adapter.callback = object : UserOrdersRecyclerAdapter.Callback {
            override fun navigateToDetails(item: OrderInfo) {
                val action = OrdersFragmentDirections.actionOrdersFragmentToOrderDetails(item)
                findNavController().navigate(action)
            }
        }
    }
}