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
                name = "Sushi",
                price = 180000.0,
                address = "Lorem Ipsum",
                desc = "A variant of traditional Chinese dumplings served steamed and fried."
            ),
            Product(
                id = 2,
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_taichan.webp",
                address = "Lorem Ipsum",
                name = "Taichan",
                price = 120000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried."

            ),
            Product(
                id = 3,
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_spaghetti.webp",
                address = "Lorem Ipsum",
                name = "Spaghetti",
                price = 130000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried."
            ),
            Product(
                id = 4,
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_ayamgpanggang.webp",
                address = "Lorem Ipsum",
                name = "Ayam Panggang",
                price = 150000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried."
            ),
            Product(
                id = 5,
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_kopi.webp",
                address = "Lorem Ipsum",
                name = "Kopi",
                price = 50000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried."
            ),
            Product(
                id = 6,
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_kentang.webp",
                name = "Kentang Goreng",
                address = "Lorem Ipsum",
                price = 100000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried."
            ),
            Product(
                id = 7,
                imgUrl = "https://raw.githubusercontent.com/ryhanhxx/Challenge-BinarAndroid/feature_navigation_component/app/src/main/res/drawable/img_burger.webp",
                address = "Lorem Ipsum",
                name = "Burger",
                price = 250000.0,
                desc = "A variant of traditional Chinese dumplings served steamed and fried."
            )
        )
    }
}
