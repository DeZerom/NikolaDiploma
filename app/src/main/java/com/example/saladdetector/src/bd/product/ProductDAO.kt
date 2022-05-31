package com.example.saladdetector.src.bd.product

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProductDAO {

    @Query("SELECT * FROM products WHERE model_id in (:ids)")
    suspend fun getFromIds(ids: List<Int>): List<BdProduct>

    @Query("SELECT * FROM products WHERE model_id = :id")
    suspend fun getById(id: Int): BdProduct

}