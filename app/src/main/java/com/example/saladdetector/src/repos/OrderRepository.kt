package com.example.saladdetector.src.repos

import android.content.Context
import android.util.Log
import androidx.room.Query
import com.example.saladdetector.src.bd.SaladDatabase
import com.example.saladdetector.src.bd.order.Order
import com.example.saladdetector.src.bd.order.OrderDao
import com.example.saladdetector.src.bd.product.ProductDAO
import com.example.saladdetector.src.bd.product_order.ProductInOrder
import com.example.saladdetector.src.domain_entyties.DetectedProduct
import com.example.saladdetector.src.domain_entyties.OrderInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderRepository(context: Context) {
    private val orderDao: OrderDao
    private val productDao: ProductDAO

    init {
        val db = SaladDatabase.getInstance(context)
        orderDao = db.orderDao()
        productDao = db.productDao()
    }

    suspend fun getOrdersInfo(): List<OrderInfo> {
        return withContext(Dispatchers.IO) {
            val map = orderDao.getOrdersWithProducts()
            Log.i("OrderRepository", map.toString())
            map.map {
                OrderInfo(
                    totalSum = it.key.totalPrice,
                    email = it.key.email,
                    products = getMatchingProducts(it.value)
                )
            }
        }
    }

    private suspend fun getMatchingProducts(
        productsInOrder: List<ProductInOrder>
    ): List<DetectedProduct> {
        return withContext(Dispatchers.IO) {
            val dbProducts = productDao.getFromIds(productsInOrder.map { it.productId })

            val res = ArrayList<DetectedProduct>(productsInOrder.size)
            for (i in productsInOrder.indices) {
                val dbProd = dbProducts[i]
                val prodInOrd = productsInOrder[i]
                res.add(DetectedProduct(
                    name = dbProd.name,
                    amount = prodInOrd.productAmount,
                    id = prodInOrd.id,
                    price = dbProd.price,
                    weight = dbProd.weight
                ))
            }
            res
        }
    }

    suspend fun insertOrder(orderInfo: OrderInfo): Int {
        return withContext(Dispatchers.IO) {
            val order = orderInfo.toDbOrder()
            orderDao.insertOrder(order).toInt()
        }
    }

}