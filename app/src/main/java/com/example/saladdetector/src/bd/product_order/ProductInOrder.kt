package com.example.saladdetector.src.bd.product_order

import androidx.room.*
import androidx.room.util.TableInfo
import com.example.saladdetector.src.bd.order.Order

@Entity(tableName = "Prod_in_ord")
data class ProductInOrder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "prod_in_ord_id") val id: Int,
    val orderId: Int,
    val productId: Int,
    @ColumnInfo(defaultValue = 0.toString()) val productAmount: Int
)