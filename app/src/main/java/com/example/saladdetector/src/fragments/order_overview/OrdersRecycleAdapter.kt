package com.example.saladdetector.src.fragments.order_overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.saladdetector.R
import com.example.saladdetector.src.domain_entyties.DetectedProduct

class OrdersRecycleAdapter :
    ListAdapter<DetectedProduct, OrdersRecycleAdapter.DetectedProductViewHolder>
        (DIFF_CALLBACK) {
    class DetectedProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectedProductViewHolder {
        return DetectedProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.detected_product_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetectedProductViewHolder, position: Int) {
        val context = holder.itemView.context
        val name = holder.itemView.findViewById<TextView>(R.id.detectedProductRow_name)
        val price = holder.itemView.findViewById<TextView>(R.id.detectedProductRow_price)

        name.text = currentList[position].name
        price.text = context.getString(R.string.price, currentList[position].price.toString())
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetectedProduct>() {
            override fun areItemsTheSame(
                oldItem: DetectedProduct,
                newItem: DetectedProduct
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: DetectedProduct,
                newItem: DetectedProduct
            ) = oldItem == newItem
        }
    }
}