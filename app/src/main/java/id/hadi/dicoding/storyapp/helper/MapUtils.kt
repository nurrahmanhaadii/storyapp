package id.hadi.dicoding.storyapp.helper

import id.haadii.dicoding.submission.core.local.entity.StoryEntity
import id.haadii.dicoding.submission.core.network.response.StoryBaseResponse
import id.haadii.dicoding.submission.core.network.response.StoryResponse
import id.hadi.dicoding.storyapp.domain.model.Story
import id.hadi.dicoding.storyapp.domain.model.StoryBase

fun StoryBaseResponse.mapToDomain(): StoryBase {
    return StoryBase(
        error = error,
        listStory = (listStory ?: listOf()).map { it.mapToDomain() },
        story = story.mapToDomain(),
        message = message
    )
}

fun StoryResponse.mapToDomain(): Story {
    return Story(
        id = id,
        description = description,
        lat = lat,
        lon = lon,
        name = name,
        photoUrl = photoUrl
    )
}

fun StoryEntity.mapToDomain(): Story {
    return Story(
        id = id,
        description = description,
        lat = lat,
        lon = lon,
        name = name,
        photoUrl = photoUrl,
        isFavorite = isFavorite
    )
}