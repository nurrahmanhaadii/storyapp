package id.haadii.dicoding.submission.core.network.response


import com.google.gson.annotations.SerializedName

data class StoryBaseResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("listStory")
    val listStory: List<StoryResponse>? = listOf(),
    @SerializedName("story")
    val story: StoryResponse?,
    @SerializedName("message")
    val message: String
)