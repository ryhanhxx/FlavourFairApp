package com.ch.flavourfair.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Cart(
    var id: Int? = null,
    var productId: Int? = null,
    var productName: String,
    var productPrice: Double,
    var productImgUrl: String,
    var itemQuantity: Int = 0,
    var itemNotes: String? = null,
)