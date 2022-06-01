package com.example.saladdetector.src.domain_entyties

import android.os.Parcelable
import com.example.saladdetector.src.bd.order.Order
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderInfo(
    private var totalSum: Double = 0.0,
    var email: String = "",
    var products: List<DetectedProduct> = emptyList()
): Parcelable {

    fun toDbOrder(): Order {
        val sum = products.sumOf { it.price * it.amount }
        return Order(id = 0, email = email, totalPrice = sum)
    }

}