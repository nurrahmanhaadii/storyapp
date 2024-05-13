package id.hadi.dicoding.storyapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.hadi.dicoding.storyapp.data.network.api.ApiService
import id.hadi.dicoding.storyapp.data.network.request.LoginRequest
import id.hadi.dicoding.storyapp.data.network.request.RegisterRequest
import id.hadi.dicoding.storyapp.data.network.response.BaseResponse
import id.hadi.dicoding.storyapp.data.network.response.LoginResponse
import id.hadi.dicoding.storyapp.data.network.response.Story
import id.hadi.dicoding.storyapp.data.network.response.StoryResponse
import id.hadi.dicoding.storyapp.helper.StoryPreferenceManager
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
class MainRepositoryImpl(
    private val apiService: ApiService,
    private val dataStore: StoryPreferenceManager
) : MainRepository {

    override suspend fun register(request: RegisterRequest): BaseResponse {
        return apiService.register(request)
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.login(request)
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

    override suspend fun getAllStoriesWithLocation(): StoryResponse {
        return apiService.getAllStoriesWithLocation()
    }

    override suspend fun getDetailStory(id: String): StoryResponse {
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

    override fun setDummyStories(stories: List<Story>) = Unit
}