package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    @SerialName("idMeal")
    val id: String? = null,
    @SerialName("strMeal")
    val name: String? = null,
    @SerialName("strCategory")
    val category: String? = null,
    @SerialName("strArea")
    val area: String? = null,
    @SerialName("strInstructions")
    val instructions: String? = null,
    @SerialName("strMealThumb")
    val thumbNail: String? = null,
    @SerialName("strTags")
    val tags: String? = null,
    @SerialName("strYoutube")
    val youtube: String? = null,
    @SerialName("strIngredient1")
    val ingredient1: String? = null,
    @SerialName("strIngredient2")
    val ingredient2: String? = null,
    @SerialName("strIngredient3")
    val ingredient3: String? = null,
    @SerialName("strIngredient4")
    val ingredient4: String? = null,
    @SerialName("strIngredient5")
    val ingredient5: String? = null,
    @SerialName("strIngredient6")
    val ingredient6: String? = null,
    @SerialName("strIngredient7")
    val ingredient7: String? = null,
    @SerialName("strIngredient8")
    val ingredient8: String? = null,
    @SerialName("strIngredient9")
    val ingredient9: String? = null,
    @SerialName("strIngredient10")
    val ingredient10: String? = null,
    @SerialName("strIngredient11")
    val ingredient11: String? = null,
    @SerialName("strIngredient12")
    val ingredient12: String? = null,
    @SerialName("strIngredient13")
    val ingredient13: String? = null,
    @SerialName("strIngredient14")
    val ingredient14: String? = null,
    @SerialName("strIngredient15")
    val ingredient15: String? = null,
    @SerialName("strIngredient16")
    val ingredient16: String? = null,
    @SerialName("strIngredient17")
    val ingredient17: String? = null,
    @SerialName("strIngredient18")
    val ingredient18: String? = null,
    @SerialName("strIngredient19")
    val ingredient19: String? = null,
    @SerialName("strIngredient20")
    val ingredient20: String? = null,
    @SerialName("strMeasure1")
    val strMeasure1: String? = null,
    @SerialName("strMeasure2")
    val strMeasure2: String? = null,
    @SerialName("strMeasure3")
    val strMeasure3: String? = null,
    @SerialName("strMeasure4")
    val strMeasure4: String? = null,
    @SerialName("strMeasure5")
    val strMeasure5: String? = null,
    @SerialName("strMeasure6")
    val strMeasure6: String? = null,
    @SerialName("strMeasure7")
    val strMeasure7: String? = null,
    @SerialName("strMeasure8")
    val strMeasure8: String? = null,
    @SerialName("strMeasure9")
    val strMeasure9: String? = null,
    @SerialName("strMeasure10")
    val strMeasure10: String? = null,
    @SerialName("strMeasure11")
    val strMeasure11: String? = null,
    @SerialName("strMeasure12")
    val strMeasure12: String? = null,
    @SerialName("strMeasure13")
    val strMeasure13: String? = null,
    @SerialName("strMeasure14")
    val strMeasure14: String? = null,
    @SerialName("strMeasure15")
    val strMeasure15: String? = null,
    @SerialName("strMeasure16")
    val strMeasure16: String? = null,
    @SerialName("strMeasure17")
    val strMeasure17: String? = null,
    @SerialName("strMeasure18")
    val strMeasure18: String? = null,
    @SerialName("strMeasure19")
    val strMeasure19: String? = null,
    @SerialName("strMeasure20")
    val strMeasure20: String? = null
) {
    fun putMeasuresInArray(): List<String?> {
        return listOf(
            strMeasure1,
            strMeasure2,
            strMeasure3,
            strMeasure4,
            strMeasure5,
            strMeasure6,
            strMeasure7,
            strMeasure8,
            strMeasure9,
            strMeasure10,
            strMeasure11,
            strMeasure12,
            strMeasure13,
            strMeasure14,
            strMeasure15,
            strMeasure16,
            strMeasure17,
            strMeasure18,
            strMeasure19,
            strMeasure20
        ).filter { it.isNullOrBlank().not() }
    }

    fun putIngredientsInArray(): List<String?> {
        return listOf(
            ingredient1,
            ingredient2,
            ingredient3,
            ingredient4,
            ingredient5,
            ingredient6,
            ingredient7,
            ingredient8,
            ingredient9,
            ingredient10,
            ingredient11,
            ingredient12,
            ingredient13,
            ingredient14,
            ingredient15,
            ingredient16,
            ingredient17,
            ingredient18,
            ingredient19,
            ingredient20
        ).filter { it.isNullOrBlank().not() }
    }
}

@Serializable
data class Meals(
    @SerialName("meals")
    val meals: List<Meal>,
)