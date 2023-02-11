package android.example.foodapp.util

sealed class NetworkResult<T>(
    //data from API
    val data: T? = null,
    //represent message (error, success, loading)
    val message: String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()
}