package com.example.saladdetector.src

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import kotlin.math.pow
import kotlin.math.roundToInt

fun round(value: Double, places: Int): Double {
    require(places >= 0)
    val factor = 10.0.pow(places.toDouble()).toLong()
    val tmp = (value * factor).roundToInt()
    return tmp.toDouble() / factor
}

fun downloadImageIntoImageView(
    context: Context,
    reference: StorageReference,
    imageView: ImageView
) {
    Glide
        .with(context)
        .load(reference)
        .into(imageView)
}
