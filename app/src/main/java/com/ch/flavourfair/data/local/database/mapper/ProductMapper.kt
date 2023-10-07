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
    id = this?.id,
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    rating = this?.rating ?: 0.0,
    desc = this?.desc.orEmpty(),
    imgUrl = this?.imgUrl.orEmpty(),
)

fun List<ProductEntity?>.toProductList() = this.map { it.toProduct() }
fun List<Product?>.toProductEntity() = this.map { it.toProductEntity() }
