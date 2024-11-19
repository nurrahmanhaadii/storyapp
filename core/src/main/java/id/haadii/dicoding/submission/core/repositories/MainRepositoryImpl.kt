package id.haadii.dicoding.submission.core.repositories

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.haadii.dicoding.submission.core.StoryPreferenceManager
import id.haadii.dicoding.submission.core.StoryRemoteMediator
import id.haadii.dicoding.submission.core.local.database.StoryDatabase
import id.haadii.dicoding.submission.core.local.entity.Favorite
import id.haadii.dicoding.submission.core.local.entity.StoryEntity
import id.haadii.dicoding.submission.core.network.api.ApiService
import id.haadii.dicoding.submission.core.network.request.LoginRequest
import id.haadii.dicoding.submission.core.network.request.RegisterRequest
import id.haadii.dicoding.submission.core.network.response.BaseResponse
import id.haadii.dicoding.submission.core.network.response.LoginResponse
import id.haadii.dicoding.submission.core.network.response.StoryBaseResponse
import id.haadii.dicoding.submission.core.network.response.StoryResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
class MainRepositoryImpl(
    private val apiService: ApiService,
    private val dataStore: StoryPreferenceManager,
    private val storyDatabase: StoryDatabase
) : MainRepository {

    override suspend fun register(request: RegisterRequest): BaseResponse {
        return apiService.register(request)
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.login(request)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllStories(): LiveData<PagingData<StoryEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }
        ).liveData
    }

    override suspend fun getAllStoriesWithLocation(): StoryBaseResponse {
        return apiService.getAllStoriesWithLocation()
    }

    override suspend fun getDetailStory(id: String): StoryBaseResponse {
        return apiService.getDetailStories(id)
    }

    override suspend fun submitStory(
        description: String,
        fileMultipart: MultipartBody.Part,
        lat: Double?,
        long: Double?
    ): BaseResponse {
        val desc = description.toRequestBody("text/plain".toMediaType())
        val latitude = lat?.toString()?.toRequestBody("text/plain".toMediaType())
        val longitude = long?.toString()?.toRequestBody("text/plain".toMediaType())
        return apiService.addStory(desc, fileMultipart, latitude, longitude)
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

    override fun setDummyStories(stories: List<StoryResponse>) = Unit

    override suspend fun setFavorite(isFavorite: Boolean, id: String) {
        if (isFavorite) {
            storyDatabase.favoriteDao().insert(Favorite(id))
        } else {
            storyDatabase.favoriteDao().deleteFavorite(id)
        }
    }

    override suspend fun getIsFavorite(id: String): Boolean {
        return storyDatabase.favoriteDao().getFavorite(id) != null
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllFavorite(): LiveData<PagingData<StoryEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.favoriteDao().getAllFavorite()
            }
        ).liveData
    }

    override suspend fun getFavorite(): List<StoryEntity> {
        return storyDatabase.favoriteDao().getFavorite()
    }
}