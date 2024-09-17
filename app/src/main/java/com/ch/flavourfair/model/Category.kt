package com.ch.flavourfair.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class Category(
    val id: String = UUID.randomUUID().toString(),
    val slug: String,
    val name: String
) : Parcelable
