package repository

sealed class RequestResult<T> {
    class Loading<T> : RequestResult<T>()
    data class Success<T>(val data: T) : RequestResult<T>()
    data class Error<T>(val errorMessage: String) : RequestResult<T>()
}