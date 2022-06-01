package com.example.saladdetector.src.domain_entyties

import android.content.Context
import com.example.saladdetector.src.bd.SaladDatabase
import com.example.saladdetector.src.bd.product_order.ProductInOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductInOrderRepository(context: Context) {
    private val dao = SaladDatabase.getInstance(context).productInOrder()

    suspend fun insertAllProductsFromOrderInfo(orderInfo: OrderInfo) {
        TODO()
    }

}