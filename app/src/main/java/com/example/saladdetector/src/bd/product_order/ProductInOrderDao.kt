package com.example.saladdetector.src.bd.product_order

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ProductInOrderDao {

    @Insert
    suspend fun insertProducts(products: List<ProductInOrder>)

}