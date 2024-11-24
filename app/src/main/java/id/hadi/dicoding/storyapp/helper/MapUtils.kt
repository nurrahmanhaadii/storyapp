package id.hadi.dicoding.storyapp.helper

//import id.haadii.dicoding.submission.core.local.entity.StoryEntity
//import id.haadii.dicoding.submission.core.network.response.StoryBaseResponse
//import id.haadii.dicoding.submission.core.network.response.StoryResponse
//import id.haadii.dicoding.submission.domain.model.Story
//import id.haadii.dicoding.submission.domain.model.StoryBase
//
//fun StoryBaseResponse.mapToDomain(): id.haadii.dicoding.submission.domain.model.StoryBase {
//    return id.haadii.dicoding.submission.domain.model.StoryBase(
//        error = error,
//        listStory = (listStory ?: listOf()).map { it.mapToDomain() },
//        story = story.mapToDomain(),
//        message = message
//    )
//}
//
//fun StoryResponse.mapToDomain(): id.haadii.dicoding.submission.domain.model.Story {
//    return id.haadii.dicoding.submission.domain.model.Story(
//        id = id,
//        description = description,
//        lat = lat,
//        lon = lon,
//        name = name,
//        photoUrl = photoUrl
//    )
//}
//
//fun StoryEntity.mapToDomain(): id.haadii.dicoding.submission.domain.model.Story {
//    return id.haadii.dicoding.submission.domain.model.Story(
//        id = id,
//        description = description,
//        lat = lat,
//        lon = lon,
//        name = name,
//        photoUrl = photoUrl,
//        isFavorite = isFavorite
//    )
//}