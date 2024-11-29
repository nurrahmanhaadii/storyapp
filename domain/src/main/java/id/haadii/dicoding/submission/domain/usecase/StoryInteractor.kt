package id.haadii.dicoding.submission.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import id.haadii.dicoding.submission.domain.model.BaseResponse
import id.haadii.dicoding.submission.domain.model.Login
import id.haadii.dicoding.submission.domain.model.Story
import id.haadii.dicoding.submission.domain.model.StoryBase
import id.haadii.dicoding.submission.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class StoryInteractor(private val repository: MainRepository) : StoryUseCase {
    override suspend fun register(name: String, email: String, password: String): BaseResponse {
        return repository.register(name, email, password)
    }

    override suspend fun login(email: String, password: String): Login {
        return repository.login(email, password)
    }

    override fun getAllStories(): LiveData<PagingData<Story>> {
        return repository.getAllStories()
    }

    override suspend fun getAllStoriesWithLocation(): StoryBase {
        return repository.getAllStoriesWithLocation()
    }

    override suspend fun getDetailStory(id: String): StoryBase {
        return repository.getDetailStory(id)
    }

    override suspend fun submitStory(
        description: String,
        image: File,
        lat: Double?,
        long: Double?
    ): BaseResponse {
        return repository.submitStory(description, image, lat, long)
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

    override fun setDummyStories(stories: List<Story>) {
        return repository.setDummyStories(stories)
    }

    override suspend fun setFavorite(isFavorite: Boolean, id: String, story: Story) {
        return repository.setFavorite(isFavorite, id, story)
    }

    override suspend fun getIsFavorite(id: String): Boolean {
        return repository.getIsFavorite(id)
    }

    override suspend fun getFavorite(): List<Story> {
        return repository.getFavorite()
    }

}