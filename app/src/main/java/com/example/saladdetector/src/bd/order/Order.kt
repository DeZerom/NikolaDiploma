package com.example.saladdetector.src.bd.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val email: String,
    val totalPrice: Double
)