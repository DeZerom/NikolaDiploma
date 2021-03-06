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

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun saveBitmapWithDetectedProducts(bitmap: Bitmap, context: Context): Uri {
        return withContext(Dispatchers.IO) {
            val file = File(context.filesDir, "${System.currentTimeMillis()}.jpeg")
            file.createNewFile()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos)
            file.writeBytes(bos.toByteArray())
            val uri = FileProvider.getUriForFile(
                context, "${BuildConfig.APPLICATION_ID}.provider", file)
            uri
        }
    }

}