package id.hadi.dicoding.storyapp.data.network.response


import com.google.gson.annotations.SerializedName

data class StoryResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("listStory")
    val listStory: List<Story>,
    @SerializedName("story")
    val story: Story,
    @SerializedName("message")
    val message: String
)