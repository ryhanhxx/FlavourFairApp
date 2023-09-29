package com.ch.flavourfair.core

import com.ch.flavourfair.model.Product

interface ViewHolderBinder<T> {
    fun bind(item: Product)
}