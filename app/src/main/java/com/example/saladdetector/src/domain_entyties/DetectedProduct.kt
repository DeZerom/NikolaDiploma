package com.example.saladdetector.src.domain_entyties

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetectedProduct(
    val name: String,
    val price: Double,
    val weight: Double,
    val amount: Int,
    val id: Int
): Parcelable
