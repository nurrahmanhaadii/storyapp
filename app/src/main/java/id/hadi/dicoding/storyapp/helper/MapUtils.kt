package id.hadi.dicoding.storyapp.helper

import id.haadii.dicoding.submission.core.local.entity.StoryEntity
import id.haadii.dicoding.submission.domain.model.Story

fun Story.mapToEntity(): StoryEntity {
    return StoryEntity(
        id = id,
        description = description,
        lat = lat,
        lon = lon,
        name = name,
        photoUrl = photoUrl,
        isFavorite = true
    )
}