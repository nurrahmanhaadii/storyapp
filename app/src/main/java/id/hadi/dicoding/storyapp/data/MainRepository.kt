package id.hadi.dicoding.storyapp.data

import id.hadi.dicoding.storyapp.data.network.request.LoginRequest
import id.hadi.dicoding.storyapp.data.network.request.RegisterRequest
import id.hadi.dicoding.storyapp.data.network.response.BaseResponse
import id.hadi.dicoding.storyapp.data.network.response.LoginResponse
import id.hadi.dicoding.storyapp.data.network.response.LoginResult
import id.hadi.dicoding.storyapp.data.network.response.StoryResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.http.Multipart

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
interface MainRepository {

    suspend fun register(request: RegisterRequest): BaseResponse

    suspend fun login(request: LoginRequest): LoginResponse

    suspend fun getAllStories(): StoryResponse

    suspend fun getDetailStory(id: String): StoryResponse

    suspend fun submitStory(description: String, fileMultipart: MultipartBody.Part): BaseResponse

    suspend fun setIsLogin(isLogin: Boolean)

    fun getIsLogin(): Flow<Boolean>

    suspend fun setToken(token: String)

    suspend fun getToken(): String

    suspend fun setUser(user: String)

    fun getUser(): Flow<String>
}