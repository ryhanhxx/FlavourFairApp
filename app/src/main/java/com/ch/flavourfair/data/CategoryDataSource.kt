package com.ch.flavourfair.data

import com.ch.flavourfair.model.Category

interface CategoryDataSource{
    fun getCategoryData(): List<Category>
}

class CategoryDataSourceImpl() : CategoryDataSource {
    override fun getCategoryData(): List<Category> {
        return mutableListOf(
            Category(
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_burger_icon.png",
                name = "Burger"
            ),
            Category(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_mie_icon.png",
                name = "Mie"
            ),
            Category(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_snack_icon.webp",
                name = "Snack"
            ),
            Category(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/FlavourFairApp/master/app/src/main/res/drawable/img_drink_icon.png",
                name = "Drink"
            )
        )
    }
}