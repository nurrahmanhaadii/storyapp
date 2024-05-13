package id.hadi.dicoding.storyapp.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import id.hadi.dicoding.storyapp.data.network.request.LoginRequest
import id.hadi.dicoding.storyapp.data.network.request.RegisterRequest
import id.hadi.dicoding.storyapp.data.network.response.BaseResponse
import id.hadi.dicoding.storyapp.data.network.response.LoginResponse
import id.hadi.dicoding.storyapp.data.network.response.Story
import id.hadi.dicoding.storyapp.data.network.response.StoryResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
interface MainRepository {

    suspend fun register(request: RegisterRequest): BaseResponse

    suspend fun login(request: LoginRequest): LoginResponse

    fun getAllStories(): LiveData<PagingData<Story>>

    suspend fun getAllStoriesWithLocation(): StoryResponse

    suspend fun getDetailStory(id: String): StoryResponse

    suspend fun submitStory(description: String, fileMultipart: MultipartBody.Part, lat: Double? = null, long: Double? = null): BaseResponse

    suspend fun setIsLogin(isLogin: Boolean)

    fun getIsLogin(): Flow<Boolean>

    suspend fun setToken(token: String)

    suspend fun getToken(): String

    suspend fun setUser(user: String)

    fun getUser(): Flow<String>

    fun setDummyStories(stories: List<Story>)
}