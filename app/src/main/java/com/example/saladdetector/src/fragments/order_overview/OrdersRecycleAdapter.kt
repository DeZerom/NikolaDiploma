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
import com.example.saladdetector.src.round

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
        val total = holder.itemView.findViewById<TextView>(R.id.detectedProductRow_total)
        val amount = holder.itemView.findViewById<TextView>(R.id.detectedProductRow_amount)

        val priceVal = currentList[position].price
        val amountVal = currentList[position].amount
        name.text = currentList[position].name
        price.text = context.getString(R.string.price, priceVal.toString())
        amount.text = context.getString(R.string.amount, amountVal.toString())
        total.text = context.getString(R.string.price,
            round(priceVal * amountVal, 2).toString())
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