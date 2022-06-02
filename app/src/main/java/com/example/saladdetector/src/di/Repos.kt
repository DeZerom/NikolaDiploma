package com.example.saladdetector.src.di

import android.content.Context
import com.example.saladdetector.src.domain_entyties.ProductInOrderRepository
import com.example.saladdetector.src.repos.OrderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ReposProvider {

    @Provides
    fun provideOrderRepo(@ApplicationContext context: Context): OrderRepository {
        return OrderRepository(context)
    }

    @Provides
    fun provideProductInOrderRepo(@ApplicationContext context: Context): ProductInOrderRepository {
        return ProductInOrderRepository(context)
    }
}