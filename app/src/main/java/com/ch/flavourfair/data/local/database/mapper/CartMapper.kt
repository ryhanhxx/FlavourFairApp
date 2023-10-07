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
// View Object > Entity
fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    productId = this?.productId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)
// list of entity > list of view object
fun List<CartEntity?>.toCartList() = this.map { it.toCart() }

// Entity > View Object
fun CartProductRelation.toCartProduct() = CartProduct(
    cart = this.cart.toCart(),
    product = this.product.toProduct()
)
// list of entity > list of view object
fun List<CartProductRelation>.toCartProductList() = this.map { it.toCartProduct() }