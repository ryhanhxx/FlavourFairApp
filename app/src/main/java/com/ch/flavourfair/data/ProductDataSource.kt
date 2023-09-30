package com.ch.flavourfair.data

import com.ch.flavourfair.model.Product

interface ProductDataSource {
    fun getProductData(): List<Product>
}

class ProductDataSourceImpl() : ProductDataSource {
    override fun getProductData(): List<Product> {
        return mutableListOf(
            Product(
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_sushi.webp",
                name = "Sushi",
                price = 18000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
                quantity = 1
            ),
            Product(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_taichan.webp",
                name = "Taichan",
                price = 12000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
                quantity = 1

            ),
            Product(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_spaghetti.webp",
                name = "Spaghetti",
                price = 13000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
                quantity = 1
            ),
            Product(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_ayamgpanggang.webp",
                name = "Ayam Panggang",
                price = 15000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
                quantity = 1
            ),
            Product(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_kopi.webp",
                name = "Kopi",
                price = 5000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
                quantity = 1
            ),
            Product(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_kentang.webp",
                name = "Kentang Goreng",
                price = 10000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
                quantity = 1
            ),
            Product(
                imgUrl="https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_burger.webp",
                name = "Burger",
                price = 25000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried.",
                quantity = 1
            )
        )
    }
}