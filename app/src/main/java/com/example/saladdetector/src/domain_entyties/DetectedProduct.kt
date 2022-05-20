package com.example.saladdetector.src.domain_entyties

data class DetectedProduct(
    val name: String,
    val price: Double,
) {
    val id: Int
        get() {
            return (name + price.toString()).hashCode()
        }
}
