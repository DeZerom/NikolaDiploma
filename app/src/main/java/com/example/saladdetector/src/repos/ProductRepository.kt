package com.example.saladdetector.src.repos

import android.content.Context
import com.example.saladdetector.src.bd.SaladDatabase
import com.example.saladdetector.src.bd.product.BdProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(context: Context) {
    private val dao = SaladDatabase.getInstance(context).productDao()

    suspend fun getByIds(ids: List<Int>): List<BdProduct> {
        return withContext(Dispatchers.IO) {
            dao.getFromIds(ids)
        }
    }

    suspend fun getById(id: Int): BdProduct {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }
}