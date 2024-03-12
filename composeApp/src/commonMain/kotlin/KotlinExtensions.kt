import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

@Composable
fun SupportedPlatforms.getAlignmentHorizontal(): Alignment.Horizontal {
    return when (this) {
        SupportedPlatforms.IOS -> Alignment.Start
        SupportedPlatforms.ANDROID -> Alignment.Start
        SupportedPlatforms.DESKTOP -> Alignment.CenterHorizontally
        SupportedPlatforms.WEB -> Alignment.CenterHorizontally
    }
}

@Composable
fun SupportedPlatforms.getAlignment(): Alignment {
    return when (this) {
        SupportedPlatforms.IOS -> Alignment.TopStart
        SupportedPlatforms.ANDROID -> Alignment.TopStart
        SupportedPlatforms.DESKTOP -> Alignment.TopCenter
        SupportedPlatforms.WEB -> Alignment.TopCenter
    }
}


inline fun <T> Boolean.ifOrElse(ifValue: T, elseValue: T): T {
    return if (this)
        ifValue
    else
        elseValue
}
