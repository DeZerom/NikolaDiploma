package com.example.saladdetector.src.di

import android.content.Context
import com.example.saladdetector.src.repos.ProductInOrderRepository
import com.example.saladdetector.src.repos.OrderRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ReposProvider {

    @Provides
    fun provideOrderRepo(
        @ApplicationContext context: Context,
        @DetectedImagesFirebaseReference ref: StorageReference
    ): OrderRepository {
        return OrderRepository(context, ref)
    }

    @Provides
    fun provideProductInOrderRepo(@ApplicationContext context: Context): ProductInOrderRepository {
        return ProductInOrderRepository(context)
    }
}