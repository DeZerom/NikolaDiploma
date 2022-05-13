package com.example.saladdetector.src.di

import android.app.Activity
import android.app.Application
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultRegistry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityScoped {
    @Provides
    fun provideActivityResultRegistry(activity: Activity): ActivityResultRegistry {
        val a = activity as ComponentActivity
        return a.activityResultRegistry
    }
}