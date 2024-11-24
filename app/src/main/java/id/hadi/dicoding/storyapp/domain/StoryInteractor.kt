package id.hadi.dicoding.storyapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import id.haadii.dicoding.submission.core.local.entity.StoryEntity
import id.haadii.dicoding.submission.core.network.request.LoginRequest
import id.haadii.dicoding.submission.core.network.request.RegisterRequest
import id.haadii.dicoding.submission.core.network.response.BaseResponse
import id.haadii.dicoding.submission.core.network.response.LoginResponse
import id.haadii.dicoding.submission.core.network.response.StoryBaseResponse
import id.haadii.dicoding.submission.core.network.response.StoryResponse
import id.haadii.dicoding.submission.core.repositories.MainRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class StoryInteractor(private val repository: MainRepository) : StoryUseCase {
    override suspend fun register(request: RegisterRequest): BaseResponse {
        return repository.register(request)
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        return repository.login(request)
    }

    override fun getAllStories(): LiveData<PagingData<StoryEntity>> {
        return repository.getAllStories()
    }

    override suspend fun getAllStoriesWithLocation(): StoryBaseResponse {
        return repository.getAllStoriesWithLocation()
    }

    override suspend fun getDetailStory(id: String): StoryBaseResponse {
        return repository.getDetailStory(id)
    }

    override suspend fun submitStory(
        description: String,
        fileMultipart: MultipartBody.Part,
        lat: Double?,
        long: Double?
    ): BaseResponse {
        return repository.submitStory(description, fileMultipart, lat, long)
    }

    override suspend fun setIsLogin(isLogin: Boolean) {
        return repository.setIsLogin(isLogin)
    }

    override fun getIsLogin(): Flow<Boolean> {
        return repository.getIsLogin()
    }

    override suspend fun setToken(token: String) {
        return repository.setToken(token)
    }

    override suspend fun getToken(): String {
        return repository.getToken()
    }

    override suspend fun setUser(user: String) {
        return repository.setUser(user)
    }

    override fun getUser(): Flow<String> {
        return repository.getUser()
    }

    override fun setDummyStories(stories: List<StoryResponse>) {
        return repository.setDummyStories(stories)
    }

    override suspend fun setFavorite(isFavorite: Boolean, id: String) {
        return repository.setFavorite(isFavorite, id)
    }

    override suspend fun getIsFavorite(id: String): Boolean {
        return repository.getIsFavorite(id)
    }

    override fun getAllFavorite(): LiveData<PagingData<StoryEntity>> {
        return repository.getAllFavorite()
    }

    override suspend fun getFavorite(): List<StoryEntity> {
        return repository.getFavorite()
    }

}