package com.ch.flavourfair.data.dummy

import com.ch.flavourfair.model.Category

interface DummyCategoryDataSource {
    fun getCategoryData(): List<Category>
}

class DummyCategoryDataSourceImpl() : DummyCategoryDataSource {
    override fun getCategoryData(): List<Category> {
        return mutableListOf(
            Category(

                name = "Burger",
                slug = "Burger"
            ),
            Category(
                name = "Mie",
                slug = "Mie"
            ),
            Category(
                name = "Snack",
                slug = "Snack"
            ),
            Category(
                name = "Drink",
                slug = "Drink"
            )
        )
    }
}
