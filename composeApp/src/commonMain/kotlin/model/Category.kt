package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("idCategory")
    val id: String?,
    @SerialName("strCategory")
    val name: String?,
    @SerialName("strCategoryThumb")
    val thumbnail: String?,
    @SerialName("strCategoryDescription")
    val description: String?,
)

@Serializable
data class Categories(
    @SerialName("categories")
    val categories: List<Category>
)