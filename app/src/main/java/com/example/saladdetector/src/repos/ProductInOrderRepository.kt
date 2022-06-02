package com.example.saladdetector.src.repos

import android.content.Context
import com.example.saladdetector.src.bd.SaladDatabase
import com.example.saladdetector.src.bd.product_order.ProductInOrder
import com.example.saladdetector.src.domain_entyties.OrderInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductInOrderRepository(context: Context) {
    private val dao = SaladDatabase.getInstance(context).productInOrder()

    suspend fun insertAllProductsFromOrderInfo(orderInfo: OrderInfo, orderId: Int) {
        withContext(Dispatchers.IO) {
            val dbProducts = orderInfo.products.map {
                ProductInOrder(0, orderId, it.id)
            }
            dao.insertProducts(dbProducts)
        }
    }

}