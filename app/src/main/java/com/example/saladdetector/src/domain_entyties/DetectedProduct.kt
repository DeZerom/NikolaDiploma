package com.example.saladdetector.src.domain_entyties

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetectedProduct(
    val name: String,
    val price: Double,
): Parcelable {
    val id: Int
        get() {
            return (name + price.toString()).hashCode()
        }
}
