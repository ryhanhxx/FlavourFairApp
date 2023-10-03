package com.ch.flavourfair.data.local.database.mapper

import com.ch.flavourfair.data.local.database.entity.CartEntity
import com.ch.flavourfair.data.local.database.relation.CartProductRelation
import com.ch.flavourfair.model.Cart
import com.ch.flavourfair.model.CartProduct

fun CartEntity?.toCart() = Cart(
    id = this?.id ?: 0,
    productId = this?.productId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    productId = this?.productId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)

fun CartProductRelation.toCartProduct() = CartProduct(
    cart = this.cart.toCart(),
    product = this.product.toProduct()
)