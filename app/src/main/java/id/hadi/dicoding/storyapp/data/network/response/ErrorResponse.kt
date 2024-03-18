package id.hadi.dicoding.storyapp.data.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by nurrahmanhaadii on 18,March,2024
 */
data class ErrorResponse(
    @field:SerializedName("error")
    val error: Boolean? = null,
    @field:SerializedName("message")
    val message: String? = null
)
