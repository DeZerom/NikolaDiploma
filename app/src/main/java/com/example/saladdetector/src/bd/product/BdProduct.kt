package com.example.saladdetector.src.bd.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class BdProduct(
    @PrimaryKey @ColumnInfo(name = "model_id") val id: Int,
    val name: String,
    val price: Double,
    val weight: Double
)
