package com.example.saladdetector.src.domain_entyties

import android.net.Uri
import android.os.Parcelable
import com.example.saladdetector.src.bd.order.Order
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderInfo(
    var totalSum: Double = 0.0,
    var email: String = "",
    var products: List<DetectedProduct> = emptyList(),
    var imageUri: Uri = Uri.EMPTY
): Parcelable {

    fun toDbOrder(): Order {
        val sum = products.sumOf { it.price * it.amount }
        return Order(id = 0, email = email, totalPrice = sum)
    }

}
