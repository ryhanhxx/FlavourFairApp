package com.ch.flavourfair.data.network.api.model.category

import androidx.annotation.Keep
import com.ch.flavourfair.model.Category
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("slug")
    val slug: String?
)

fun CategoryResponse.toCategory() = Category(
    id = this.id.orEmpty(),
    name = this.nama.orEmpty(),
    slug = this.slug.orEmpty()
)

fun Collection<CategoryResponse>.toCategoryList() = this.map {
    it.toCategory()
}
