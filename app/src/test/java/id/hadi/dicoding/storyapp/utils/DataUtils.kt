package id.hadi.dicoding.storyapp.utils

import id.hadi.dicoding.storyapp.data.network.response.Story

/**
 * Created by nurrahmanhaadii on 02,April,2024
 */
object DataUtils {
    fun generateDummyStory(): List<Story> {
        val storyList = mutableListOf<Story>()
        for (i in 0..100) {
            val story = Story(
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