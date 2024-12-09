package id.haadii.dicoding.submission.core.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.haadii.dicoding.submission.core.StoryPagingSource
import id.haadii.dicoding.submission.core.StoryPreferenceManager
import id.haadii.dicoding.submission.core.helpers.Utils
import id.haadii.dicoding.submission.core.local.database.StoryDatabase
import id.haadii.dicoding.submission.core.mapToDomain
import id.haadii.dicoding.submission.core.mapToEntity
import id.haadii.dicoding.submission.core.network.api.ApiService
import id.haadii.dicoding.submission.core.network.request.LoginRequest
import id.haadii.dicoding.submission.core.network.request.RegisterRequest
import id.haadii.dicoding.submission.domain.model.BaseResponse
import id.haadii.dicoding.submission.domain.model.Login
import id.haadii.dicoding.submission.domain.model.Story
import id.haadii.dicoding.submission.domain.model.StoryBase
import id.haadii.dicoding.submission.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
class MainRepositoryImpl(
    private val apiService: ApiService,
    private val dataStore: StoryPreferenceManager,
    private val storyDatabase: StoryDatabase
) : MainRepository {

    override suspend fun register(name: String, email: String, password: String): BaseResponse {
        val request = RegisterRequest(name, email, password)
        val response = apiService.register(request)
        return response.mapToDomain()
    }

    override suspend fun login(email: String, password: String): Login {
        return apiService.login(LoginRequest(email, password)).mapToDomain()
    }

    override fun getAllStories(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    override suspend fun getAllStoriesWithLocation(): StoryBase {
        return apiService.getAllStoriesWithLocation().mapToDomain()
    }

    override suspend fun getDetailStory(id: String): StoryBase {
        return apiService.getDetailStories(id).mapToDomain()
    }

    override suspend fun submitStory(
        description: String,
        image: File,
        lat: Double?,
        long: Double?
    ): BaseResponse {
        val fileMultipart = Utils.getMultipartBodyFile("photo", image)
        val desc = description.toRequestBody("text/plain".toMediaType())
        val latitude = lat?.toString()?.toRequestBody("text/plain".toMediaType())
        val longitude = long?.toString()?.toRequestBody("text/plain".toMediaType())
        return apiService.addStory(desc, fileMultipart, latitude, longitude).mapToDomain()
    }

    override suspend fun setIsLogin(isLogin: Boolean) {
        dataStore.setIsLogin(isLogin)
    }

    override fun getIsLogin(): Flow<Boolean> {
        return dataStore.getIsLogin()
    }

    override suspend fun setToken(token: String) {
        dataStore.setToken(token)
    }

    override suspend fun getToken(): String {
        return dataStore.getToken()
    }

    override suspend fun setUser(user: String) {
        dataStore.setUser(user)
    }

    override fun getUser(): Flow<String> {
        return dataStore.getUser()
    }

    override fun setDummyStories(stories: List<Story>) = Unit

    override suspend fun setFavorite(isFavorite: Boolean, id: String, story: Story) {
        if (isFavorite) {
            storyDatabase.storyDao().insert(story.mapToEntity())
        } else {
            storyDatabase.storyDao().deleteStoryById(id)
        }
    }

    override suspend fun getIsFavorite(id: String): Boolean {
        return storyDatabase.storyDao().getStoryById(id) != null
    }

    override suspend fun getFavorite(): List<Story> {
        return storyDatabase.storyDao().getStories().map { it.mapToDomain() }
    }
}