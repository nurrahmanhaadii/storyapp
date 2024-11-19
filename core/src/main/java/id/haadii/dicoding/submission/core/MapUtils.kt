package id.haadii.dicoding.submission.core

import id.haadii.dicoding.submission.core.local.entity.StoryEntity
import id.haadii.dicoding.submission.core.network.response.StoryResponse




fun StoryResponse.mapToEntity(): StoryEntity {
    return StoryEntity(
        id = id,
        description = description,
        lat = lat,
        lon = lon,
        name = name,
        photoUrl = photoUrl
    )
}