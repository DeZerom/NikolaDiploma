package com.example.saladdetector.src.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.saladdetector.src.bd.product.BdProduct
import com.example.saladdetector.src.bd.product.ProductDAO

@Database(entities = [BdProduct::class], version = 2)
abstract class SaladDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDAO

    companion object {
        private var instance: SaladDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("INSERT INTO products VALUES\n    (0, \"Baked Good\", 15.5, 25),\n    (1, \"Cheese\", 44, 30),\n    (2, \"Salad\", 23, 15),\n    (3, \"Seafood\", 55, 30),\n    (4, \"Tomato\", 20, 25)")
            }
        }

        fun getInstance(context: Context): SaladDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room
                        .databaseBuilder(context, SaladDatabase::class.java, "SaladDb")
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return instance!!
        }
    }
}