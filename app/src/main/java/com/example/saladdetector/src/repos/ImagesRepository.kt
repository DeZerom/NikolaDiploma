package com.example.saladdetector.src.repos

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.saladdetector.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class ImagesRepository @Inject constructor() {

    suspend fun saveBitmapWithDetectedProducts(bitmap: Bitmap, context: Context): Uri {
        return withContext(Dispatchers.IO) {
            val file = File(context.filesDir, "detectedProducts/${System.currentTimeMillis()}.jpg")
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos)
            file.writeBytes(bos.toByteArray())
            FileProvider.getUriForFile(
                context, "${BuildConfig.APPLICATION_ID}.provider", file)
        }
    }

}