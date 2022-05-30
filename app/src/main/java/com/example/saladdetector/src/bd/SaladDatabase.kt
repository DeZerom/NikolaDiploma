package com.example.saladdetector.src.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.saladdetector.src.bd.product.BdProduct
import com.example.saladdetector.src.bd.product.ProductDAO

@Database(entities = [BdProduct::class], version = 1)
abstract class SaladDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDAO

    companion object {
        private var instance: SaladDatabase? = null

        fun getInstance(context: Context): SaladDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room
                        .databaseBuilder(context, SaladDatabase::class.java, "SaladDb")
                        .build()
                }
            }
            return instance!!
        }
    }
}