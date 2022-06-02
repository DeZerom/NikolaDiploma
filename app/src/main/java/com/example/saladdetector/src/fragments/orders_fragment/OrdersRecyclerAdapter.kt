package com.example.saladdetector.src.fragments.orders_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.saladdetector.R
import com.example.saladdetector.src.domain_entyties.DetectedProduct
import com.example.saladdetector.src.domain_entyties.OrderInfo
import com.example.saladdetector.src.fragments.order_overview.OrdersRecycleAdapter

class UserOrdersRecyclerAdapter :
    ListAdapter<OrderInfo, UserOrdersRecyclerAdapter.UserOrderViewHolder>(DIFF_CALLBACK) {

    class UserOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserOrderViewHolder {
        return UserOrderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_order_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserOrderViewHolder, position: Int) {
        val emailView = holder.itemView.findViewById<TextView>(R.id.userOrderRow_email)
        val sumView = holder.itemView.findViewById<TextView>(R.id.userOrderRow_totalSum)

        val orderInfo = currentList[position]
        emailView.text = orderInfo.email
        sumView.text = orderInfo.totalSum.toString()
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OrderInfo>() {
            override fun areItemsTheSame(oldItem: OrderInfo, newItem: OrderInfo): Boolean {
                return areContentsTheSame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItem: OrderInfo, newItem: OrderInfo): Boolean {
                return oldItem == newItem
            }
        }
    }

}