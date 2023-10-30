package com.ch.flavourfair.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Int? = null,
    val imgUrl: String,
    val address: String,
    val name: String,
    val price: Double,
    val desc: String
) : Parcelable
