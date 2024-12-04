package id.haadii.dicoding.submission.core.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
data class BaseResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)
