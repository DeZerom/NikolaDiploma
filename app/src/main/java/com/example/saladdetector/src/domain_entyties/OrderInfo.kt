package com.example.saladdetector.src.domain_entyties

import com.example.saladdetector.src.bd.order.Order

class OrderInfo {
    private var totalSum: Double = 0.0
    var email: String = ""
    var products: List<DetectedProduct> = emptyList()
}