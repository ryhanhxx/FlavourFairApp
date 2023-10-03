package com.ch.flavourfair.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class Product(
    val id: Int? = null,
    val imgUrl: String,
    val rating: Double,
    val name: String,
    val price: Double,
    val desc: String,
): Parcelable
