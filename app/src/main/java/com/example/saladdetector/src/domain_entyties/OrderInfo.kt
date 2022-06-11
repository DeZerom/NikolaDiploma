package com.example.saladdetector.src.domain_entyties

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderInfo(
    var totalSum: Double = 0.0,
    var email: String = "",
    var products: List<DetectedProduct> = emptyList(),
    var imageUri: Uri = Uri.EMPTY,
    val nameOnServer: String = ""
): Parcelable
