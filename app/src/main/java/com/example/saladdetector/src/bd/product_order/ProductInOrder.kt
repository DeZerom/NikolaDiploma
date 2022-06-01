package com.example.saladdetector.src.bd.product_order

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import com.example.saladdetector.src.bd.order.Order

@Entity(tableName = "Prod_in_ord")
data class ProductInOrder(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val orderId: Int,
    val productId: Int
)