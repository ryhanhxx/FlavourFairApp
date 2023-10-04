package com.ch.flavourfair.data.dummy

import com.ch.flavourfair.model.Product

interface DummyProductDataSource {
    fun getProductData(): List<Product>
}

class DummyProductDataSourceImpl() : DummyProductDataSource {
    override fun getProductData(): List<Product> {
        return mutableListOf(
            Product(
                id = 1,
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_sushi.webp",
                rating = 4.8,
                name = "Sushi",
                price = 18000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
            ),
            Product(
                id = 2,
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_taichan.webp",
                rating = 4.8,
                name = "Taichan",
                price = 12000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",

            ),
            Product(
                id = 3,
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_spaghetti.webp",
                rating = 4.8,
                name = "Spaghetti",
                price = 13000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
            ),
            Product(
                id = 4,
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_ayamgpanggang.webp",
                rating = 4.8,
                name = "Ayam Panggang",
                price = 15000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
            ),
            Product(
                id = 5,
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_kopi.webp",
                rating = 4.8,
                name = "Kopi",
                price = 5000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
            ),
            Product(
                id = 6,
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_kentang.webp",
                rating = 4.8,
                name = "Kentang Goreng",
                price = 10000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
            ),
            Product(
                id = 7,
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_burger.webp",
                rating = 4.8,
                name = "Burger",
                price = 25000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
            )
        )
    }
}