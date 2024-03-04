import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import repository.Repository.Companion.BASE_URL

suspend inline fun <reified T> HttpClient.makeGet(
    path: String,
    baseURL: String = BASE_URL,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    val data = get { url(baseURL + path); block() }.body<T>()
    return data
}

fun <T> List<T>?.orEmpty(): List<T> {
    return this ?: emptyList()
}

suspend fun <T> safeRun(block: suspend () -> T): Result<T> {
    return withContext(Dispatchers.Default) {
        try {
            Result.success(block())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}