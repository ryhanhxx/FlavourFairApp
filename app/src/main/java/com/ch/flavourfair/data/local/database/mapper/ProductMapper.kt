package com.ch.flavourfair.data.local.database.mapper

import com.ch.flavourfair.data.local.database.entity.ProductEntity
import com.ch.flavourfair.model.Product

fun ProductEntity?.toProduct() = Product(
    id = this?.id ?: 0,
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    rating = this?.rating ?: 0.0,
    desc = this?.desc.orEmpty(),
    imgUrl = this?.imgUrl.orEmpty(),
)

fun Product?.toProductEntity() = ProductEntity(
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    rating = this?.rating ?: 0.0,
    desc = this?.desc.orEmpty(),
    imgUrl = this?.imgUrl.orEmpty(),
).apply {
    this@toProductEntity?.id?.let {
        this.id = this@toProductEntity.id
    }
}
fun List<Product?>.toProductEntity() = this.map { it.toProductEntity() }
