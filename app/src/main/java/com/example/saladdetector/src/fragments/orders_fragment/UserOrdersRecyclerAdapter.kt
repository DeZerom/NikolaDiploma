package com.example.saladdetector.src.fragments.orders_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.saladdetector.R
import com.example.saladdetector.src.di.DetectedImagesFirebaseReference
import com.example.saladdetector.src.domain_entyties.OrderInfo
import com.example.saladdetector.src.downloadImageIntoImageView
import com.example.saladdetector.src.round
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class UserOrdersRecyclerAdapter @Inject constructor(
    @DetectedImagesFirebaseReference private val detectedImagesRef: StorageReference
) :
    ListAdapter<OrderInfo, UserOrdersRecyclerAdapter.UserOrderViewHolder>(DIFF_CALLBACK) {

    var callback: Callback? = null

    class UserOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserOrderViewHolder {
        return UserOrderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_order_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserOrderViewHolder, position: Int) {
        val context = holder.itemView.context
        val emailView = holder.itemView.findViewById<TextView>(R.id.userOrderRow_email)
        val sumView = holder.itemView.findViewById<TextView>(R.id.userOrderRow_totalSum)
        val layout = holder.itemView.findViewById<ConstraintLayout>(R.id.userOrderRow_layout)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.userOrderRow_image)

        val orderInfo = currentList[position]
        emailView.text = orderInfo.email
        sumView.text = context.getString(R.string.price, round(orderInfo.totalSum, 2).toString())
        val ref = detectedImagesRef.child("${orderInfo.email}/${orderInfo.nameOnServer}")
        downloadImageIntoImageView(context, ref, imageView)

        layout.setOnClickListener {
            callback?.navigateToDetails(orderInfo)
        }
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

    interface Callback {
        fun navigateToDetails(item: OrderInfo)
    }
}