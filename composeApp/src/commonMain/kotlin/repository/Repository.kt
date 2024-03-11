package repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import makeGet
import model.Categories
import model.Meal
import model.Meals
import safeRun
import foodverse.composeapp.generated.resources.Res

//https://api.thecatapi.com/v1/images/search
/**
 * [{"id":"yFwzO96ds","url":"https://cdn2.thecatapi.com/images/yFwzO96ds.jpg","width":1200,"height":776}]
 */
// www.themealdb.com/api/json/v1/1/search.php?f=a


class Repository() {
    companion object {
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1"
        val client: HttpClient = HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    suspend fun searchMealsByCharacter(char: String): Result<Meals> {
        return safeRun<Meals> {
            client.makeGet<Meals>("/search.php") {
                url {
                    parameters.append("f", char)
                }
            }
        }
    }

    suspend fun filterMeals(filter: String): Result<Meals> {
        return safeRun<Meals> {
            client.makeGet<Meals>("/filter.php") {
                url {
                    parameters.append("c", filter)
                }
            }
        }
    }

    suspend fun fetchRandomMeal(): Result<Meals> {
        return safeRun<Meals> {
            client.makeGet<Meals>("/random.php")
        }
    }
    suspend fun fetchMealByID(id:String): Result<Meals> {
        return safeRun<Meals> {
            client.makeGet<Meals>("/lookup.php"){
                url {
                    parameters.append("i", id)
                }
            }
        }
    }

    suspend fun searchMeals(query: String): Result<Meals> {
        return withContext(Dispatchers.Default) {
            try {
                val data = client.makeGet<Meals>("/search.php") {
                    url {
                        parameters.append("s", query)
                    }
                }
                Result.success(data)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getCategories(): Result<Categories> {
        return safeRun<Categories> {
            client.makeGet("/categories.php")
        }
    }

    suspend fun getCat(): List<Cat>? {

//        val response = HttpRequestBuilder().apply {
//            url("https://api.thecatapi.com/v1/images/search")
//        }.build()

        val response = try {
            client.get("https://api.thecatapi.com/v1/images/search").body<List<Cat>>()
        } catch (e: Exception) {
            println(e.message)
            null
        }
        return response
    }
}

@Serializable
data class Cat(
    @SerialName("url") val url: String
)

//data class CatResponse(
//    
//)