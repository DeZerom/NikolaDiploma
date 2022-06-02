package com.example.saladdetector.src.bd

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.saladdetector.src.bd.order.Order
import com.example.saladdetector.src.bd.order.OrderDao
import com.example.saladdetector.src.bd.product.BdProduct
import com.example.saladdetector.src.bd.product.ProductDAO
import com.example.saladdetector.src.bd.product_order.ProductInOrder
import com.example.saladdetector.src.bd.product_order.ProductInOrderDao

@Database(
    entities = [BdProduct::class, Order::class, ProductInOrder::class],
    version = 4, autoMigrations = [
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4)
    ],
    exportSchema = true,
)
abstract class SaladDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO
    abstract fun orderDao(): OrderDao
    abstract fun productInOrder(): ProductInOrderDao

    companion object {
        private var instance: SaladDatabase? = null

        private val callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "INSERT INTO products VALUES\n" +
                            "    (0, \"Baked Good\", 15.5, 25),\n" +
                            "    (1, \"Cheese\", 44, 30),\n" +
                            "    (2, \"Salad\", 23, 15),\n" +
                            "    (3, \"Seafood\", 55, 30),\n" +
                            "    (4, \"Tomato\", 20, 25)"
                )
            }
        }

        fun getInstance(context: Context): SaladDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room
                        .databaseBuilder(context, SaladDatabase::class.java, "SaladDb")
                        .addCallback(callback)
                        .build()
                }
            }
            return instance!!
        }
    }
}