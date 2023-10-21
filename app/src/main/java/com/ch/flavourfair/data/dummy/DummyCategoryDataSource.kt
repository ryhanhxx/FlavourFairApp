package com.ch.flavourfair.data.dummy

import com.ch.flavourfair.model.Category

interface DummyCategoryDataSource{
    fun getCategoryData(): List<Category>
}

class DummyCategoryDataSourceImpl() : DummyCategoryDataSource {
    override fun getCategoryData(): List<Category> {
        return mutableListOf(
            Category(
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_burger_icon.webp",
                name = "Burger",
                slug = "Burger",
            ),
            Category(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_mie_icon.webp",
                name = "Mie",
                slug = "Mie",
            ),
            Category(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_snack_icon.webp",
                name = "Snack",
                slug = "Snack",
            ),
            Category(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_drink_icon.webp",
                name = "Drink",
                slug = "Drink",
            )
        )
    }
}