package com.example.saladdetector.src.repos

import android.content.Context
import androidx.room.Query
import com.example.saladdetector.src.bd.SaladDatabase
import com.example.saladdetector.src.bd.order.Order
import com.example.saladdetector.src.bd.order.OrderDao
import com.example.saladdetector.src.bd.product_order.ProductInOrder
import com.example.saladdetector.src.domain_entyties.OrderInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderRepository(context: Context) {
    private val orderDao = SaladDatabase.getInstance(context).orderDao()

    suspend fun getOrdersWithProducts(): Map<Order, List<ProductInOrder>> {
        return withContext(Dispatchers.IO) {
            orderDao.getOrdersWithProducts()
        }
    }

    suspend fun insertOrder(orderInfo: OrderInfo) {
        withContext(Dispatchers.IO) {
            val order = orderInfo.toDbOrder()
        }
    }

}