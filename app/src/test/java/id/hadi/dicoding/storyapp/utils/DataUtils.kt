package id.hadi.dicoding.storyapp.utils

import id.haadii.dicoding.submission.domain.model.Story

/**
 * Created by nurrahmanhaadii on 02,April,2024
 */
object DataUtils {
    fun generateDummyStory(): List<Story> {
        val storyList = mutableListOf<Story>()
        for (i in 0..100) {
            val story = Story(
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