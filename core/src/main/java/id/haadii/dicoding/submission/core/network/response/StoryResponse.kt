package id.haadii.dicoding.submission.core.network.response

import com.google.gson.annotations.SerializedName

data class StoryResponse(
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lon")
    val lon: Double? = null,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("photoUrl")
    val photoUrl: String = ""
)