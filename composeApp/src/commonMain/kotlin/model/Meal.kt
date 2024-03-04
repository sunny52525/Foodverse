package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    @SerialName("idMeal")
    val id: String?=null,
    @SerialName("strMeal")
    val name: String?=null,
    @SerialName("strCategory")
    val category: String?=null,
    @SerialName("strArea")
    val area: String?=null,
    @SerialName("strInstructions")
    val instructions: String?=null,
    @SerialName("strMealThumb")
    val thumbNail: String?=null,
    @SerialName("strTags")
    val tags: String?=null,
    @SerialName("strYoutube")
    val youtube: String?=null,
    @SerialName("strIngredient1")
    val ingredient1: String?=null,
    @SerialName("strIngredient2")
    val ingredient2: String?=null,
    @SerialName("strIngredient3")
    val ingredient3: String?=null,
    @SerialName("strIngredient4")
    val ingredient4: String?=null,
    @SerialName("strIngredient5")
    val ingredient5: String?=null,
    @SerialName("strIngredient6")
    val ingredient6: String?=null,
    @SerialName("strIngredient7")
    val ingredient7: String?=null,
    @SerialName("strIngredient8")
    val ingredient8: String?=null,
    @SerialName("strIngredient9")
    val ingredient9: String?=null,
    @SerialName("strIngredient10")
    val ingredient10: String?=null,
    @SerialName("strIngredient11")
    val ingredient11: String?=null,
    @SerialName("strIngredient12")
    val ingredient12: String?=null,
    @SerialName("strIngredient13")
    val ingredient13: String?=null,
    @SerialName("strIngredient14")
    val ingredient14: String?=null,
    @SerialName("strIngredient15")
    val ingredient15: String?=null,
    @SerialName("strIngredient16")
    val ingredient16: String?=null,
    @SerialName("strIngredient17")
    val ingredient17: String?=null,
    @SerialName("strIngredient18")
    val ingredient18: String?=null,
    @SerialName("strIngredient19")
    val ingredient19: String?=null,
    @SerialName("strIngredient20")
    val ingredient20: String?=null,
)

@Serializable
data class Meals(
    @SerialName("meals")
    val meals:List<Meal>,
)