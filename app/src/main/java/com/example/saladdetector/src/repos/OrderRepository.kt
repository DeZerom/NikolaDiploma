package com.example.saladdetector.src.repos

import android.content.Context
import com.example.saladdetector.src.bd.SaladDatabase
import com.example.saladdetector.src.bd.order.Order
import com.example.saladdetector.src.bd.order.OrderDao
import com.example.saladdetector.src.bd.product.ProductDAO
import com.example.saladdetector.src.bd.product_order.ProductInOrder
import com.example.saladdetector.src.di.DetectedImagesFirebaseReference
import com.example.saladdetector.src.domain_entyties.DetectedProduct
import com.example.saladdetector.src.domain_entyties.OrderInfo
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(
    @ApplicationContext context: Context,
    @DetectedImagesFirebaseReference private val detectedImagesRef: StorageReference,
) {
    private val orderDao: OrderDao
    private val productDao: ProductDAO

    init {
        val db = SaladDatabase.getInstance(context)
        orderDao = db.orderDao()
        productDao = db.productDao()
    }

    suspend fun getOrdersInfo(): List<OrderInfo> {
        return withContext(Dispatchers.IO) {
            orderDao.getOrdersWithProducts()
                .map {
                    OrderInfo(
                        totalSum = it.key.totalPrice,
                        email = it.key.email,
                        products = getMatchingProducts(it.value),
                        nameOnServer = it.key.nameOnServer
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
                res.add(
                    DetectedProduct(
                        name = dbProd.name,
                        amount = prodInOrd.productAmount,
                        id = prodInOrd.id,
                        price = dbProd.price,
                        weight = dbProd.weight
                    )
                )
            }
            res
        }
    }

    suspend fun insertOrder(orderInfo: OrderInfo): Int {
        return withContext(Dispatchers.IO) {
            val downloadUrl = uploadOrderPhoto(orderInfo)

            val dbOrder = Order(
                id = 0,
                email = orderInfo.email,
                totalPrice = orderInfo.products.sumOf { it.price * it.amount },
                nameOnServer = downloadUrl
            )
            orderDao.insertOrder(dbOrder).toInt()
        }
    }

    private fun uploadOrderPhoto(orderInfo: OrderInfo): String {
        val filePathLastPart = orderInfo.imageUri.pathSegments.last()
        val ref = detectedImagesRef
            .child("${orderInfo.email}/$filePathLastPart")

        ref.putFile(orderInfo.imageUri)

        return filePathLastPart
    }

}