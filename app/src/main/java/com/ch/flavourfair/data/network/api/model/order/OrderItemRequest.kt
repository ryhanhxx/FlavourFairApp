package com.ch.flavourfair.data.network.api.model.order


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OrderItemRequest(
    @SerializedName("catatan")
    val catatan: String?,
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("qty")
    val qty: Int?
)