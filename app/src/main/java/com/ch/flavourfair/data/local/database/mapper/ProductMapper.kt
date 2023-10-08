package com.ch.flavourfair.data.local.database.mapper

import com.ch.flavourfair.data.local.database.entity.ProductEntity
import com.ch.flavourfair.model.Product

fun ProductEntity?.toProduct() = Product(
    id = this?.id ?: 0,
    imgUrl = this?.imgUrl.orEmpty(),
    rating = this?.rating ?: 0.0,
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    desc = this?.desc.orEmpty(),
)

fun Product?.toProductEntity() = ProductEntity(
    id = this?.id,
    imgUrl = this?.imgUrl.orEmpty(),
    rating = this?.rating ?: 0.0,
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    desc = this?.desc.orEmpty(),
)

fun List<ProductEntity?>.toProductList() = this.map { it.toProduct() }
fun List<Product?>.toProductEntity() = this.map { it.toProductEntity() }
