package com.ch.flavourfair.data.network.api.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderRequest(
    /*@SerializedName("username")
    val username: List<OrderItemRequest>?,
    @SerializedName("total")
    val total: List<OrderItemRequest>?,*/
    @SerializedName("orders")
    val orders: List<OrderItemRequest>?
)
