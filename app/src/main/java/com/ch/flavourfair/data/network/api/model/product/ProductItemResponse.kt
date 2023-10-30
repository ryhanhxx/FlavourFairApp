package com.ch.flavourfair.data.network.api.model.product

import androidx.annotation.Keep
import com.ch.flavourfair.model.Product
import com.google.gson.annotations.SerializedName

@Keep
data class ProductItemResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("alamat_resto")
    val alamatResto: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("harga")
    val harga: Double?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val nama: String?
)

fun ProductItemResponse.toProduct() = Product(
    id = this.id,
    name = this.nama.orEmpty(),
    price = this.harga ?: 0.0,
    desc = this.detail.orEmpty(),
    imgUrl = this.imageUrl.orEmpty(),
    address = this.alamatResto.orEmpty()
)

fun Collection<ProductItemResponse>.toProductList() = this.map { it.toProduct() }
