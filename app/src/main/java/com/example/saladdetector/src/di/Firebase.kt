package com.example.saladdetector.src.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object FirestoreProviderModule {

    @Provides
    fun provideFirestoreStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    @BaseFirestoreReference
    fun provideBaseFirestoreReference(storage: FirebaseStorage): StorageReference {
        return storage.reference
    }

    @Provides
    @DetectedImagesFirebaseReference
    fun provideDetectedImagesFirebaseReference(
        @BaseFirestoreReference baseStorage: StorageReference
    ): StorageReference {
        return baseStorage.child("detectedImages")
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseFirestoreReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DetectedImagesFirebaseReference