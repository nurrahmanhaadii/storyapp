package id.haadii.dicoding.submission.domain.model

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
sealed class Resource<out T> private constructor() {
    data class Success<out T>(val data: T): Resource<T>()
    data class Error<out T>(val data: T?): Resource<T>()
    data object Loading: Resource<Nothing>()
}