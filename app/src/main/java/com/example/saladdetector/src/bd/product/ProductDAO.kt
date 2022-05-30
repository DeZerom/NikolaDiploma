package com.example.saladdetector.src.bd.product

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProductDAO {

    @Query("SELECT * FROM products WHERE model_id in (:ids)")
    fun getFromIds(ids: List<Int>): List<BdProduct>

}