package id.hadi.dicoding.storyapp.utils

import id.haadii.dicoding.submission.core.network.response.StoryResponse

/**
 * Created by nurrahmanhaadii on 02,April,2024
 */
object DataUtils {
    fun generateDummyStory(): List<StoryResponse> {
        val storyList = mutableListOf<StoryResponse>()
        for (i in 0..100) {
            val story = StoryResponse(
                createdAt = "time",
                description = "description",
                id = i.toString(),
                lat = 0.00,
                lon = 0.00,
                name = "name-$i",
                photoUrl = "photoUrl"
            )
            storyList.add(story)
        }
        return storyList
    }
}