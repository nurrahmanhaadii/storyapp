package id.haadii.dicoding.submission.core

import id.haadii.dicoding.submission.core.local.entity.StoryEntity
import id.haadii.dicoding.submission.core.network.response.BaseResponse
import id.haadii.dicoding.submission.core.network.response.LoginResponse
import id.haadii.dicoding.submission.core.network.response.LoginResult
import id.haadii.dicoding.submission.core.network.response.StoryBaseResponse
import id.haadii.dicoding.submission.core.network.response.StoryResponse
import id.haadii.dicoding.submission.domain.model.Login
import id.haadii.dicoding.submission.domain.model.Story
import id.haadii.dicoding.submission.domain.model.StoryBase
import id.haadii.dicoding.submission.domain.model.BaseResponse as BaseResponseDomain
import id.haadii.dicoding.submission.domain.model.LoginResult as LoginResultDomain

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

fun BaseResponse.mapToDomain(): BaseResponseDomain {
    return BaseResponseDomain(
        error = error,
        message = message
    )
}

fun LoginResponse.mapToDomain(): Login {
    return Login(
        error = error,
        message = message,
        loginResult = loginResult.mapToDomain()
    )
}

fun LoginResult.mapToDomain(): LoginResultDomain {
    return LoginResultDomain(
        name = name,
        token = token,
        userId = userId
    )
}

fun StoryBaseResponse.mapToDomain(): StoryBase {
    return StoryBase(
        error = error,
        message = message,
        story = story.mapToDomain(),
        listStory = (listStory ?: listOf()).map { it.mapToDomain() }
    )
}

fun StoryEntity.mapToDomain(): Story {
    return Story(
        id = id,
        description = description,
        lat = lat,
        lon = lon,
        name = name,
        photoUrl = photoUrl
    )
}

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