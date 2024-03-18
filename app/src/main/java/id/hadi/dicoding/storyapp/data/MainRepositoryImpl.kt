package id.hadi.dicoding.storyapp.data

import id.hadi.dicoding.storyapp.data.network.api.ApiService
import id.hadi.dicoding.storyapp.data.network.request.LoginRequest
import id.hadi.dicoding.storyapp.data.network.request.RegisterRequest
import id.hadi.dicoding.storyapp.data.network.response.BaseResponse
import id.hadi.dicoding.storyapp.data.network.response.LoginResponse
import id.hadi.dicoding.storyapp.data.network.response.StoryResponse
import id.hadi.dicoding.storyapp.helper.StoryPreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
): MainRepository {

    override suspend fun register(request: RegisterRequest): BaseResponse {
        return apiService.register(request)
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.login(request)
    }

    override suspend fun getAllStories(): StoryResponse {
        return apiService.getAllStories()
    }

    override suspend fun getDetailStory(id: String): StoryResponse {
        return apiService.getDetailStories(id)
    }

    override suspend fun submitStory(description: String, fileMultipart: MultipartBody.Part): BaseResponse {
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        return apiService.addStory(requestBody, fileMultipart)
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
}