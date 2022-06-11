package com.example.saladdetector.src.bd.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "order_id") val id: Int,
    val email: String,
    val totalPrice: Double,
    @ColumnInfo(defaultValue = "", name = "photoDownloadUrl") val nameOnServer: String
)