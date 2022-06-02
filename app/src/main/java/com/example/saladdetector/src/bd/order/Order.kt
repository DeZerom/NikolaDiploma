package com.example.saladdetector.src.bd.order

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.saladdetector.src.bd.product_order.ProductInOrder

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "order_id") val id: Int,
    val email: String,
    val totalPrice: Double
)