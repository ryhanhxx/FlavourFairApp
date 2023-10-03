package com.ch.flavourfair.data.dummy

import com.ch.flavourfair.model.Category

interface CategoryDataSource{
    fun getCategoryData(): List<Category>
}

class CategoryDataSourceImpl() : CategoryDataSource {
    override fun getCategoryData(): List<Category> {
        return mutableListOf(
            Category(
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_burger_icon.webp",
                name = "Burger"
            ),
            Category(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_mie_icon.webp",
                name = "Mie"
            ),
            Category(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_snack_icon.webp",
                name = "Snack"
            ),
            Category(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_drink_icon.webp",
                name = "Drink"
            )
        )
    }
}