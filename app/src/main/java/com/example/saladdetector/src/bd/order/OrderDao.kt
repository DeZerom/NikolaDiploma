package com.example.saladdetector.src.bd.order

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.saladdetector.src.bd.product_order.ProductInOrder

@Dao
interface OrderDao {

        @Query("SELECT * FROM orders JOIN prod_in_ord ON orders.order_id = prod_in_ord.orderId")
        suspend fun getOrdersWithProducts(): Map<Order, List<ProductInOrder>>

        @Insert
        suspend fun insertOrder(order: Order): Long

}