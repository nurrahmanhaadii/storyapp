package id.haadii.dicoding.submission.core.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import id.haadii.dicoding.submission.core.local.entity.StoryEntity
import id.haadii.dicoding.submission.core.network.request.LoginRequest
import id.haadii.dicoding.submission.core.network.request.RegisterRequest
import id.haadii.dicoding.submission.core.network.response.BaseResponse
import id.haadii.dicoding.submission.core.network.response.LoginResponse
import id.haadii.dicoding.submission.core.network.response.StoryBaseResponse
import id.haadii.dicoding.submission.core.network.response.StoryResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
interface MainRepository {

    suspend fun register(request: RegisterRequest): BaseResponse

    suspend fun login(request: LoginRequest): LoginResponse

    fun getAllStories(): LiveData<PagingData<StoryEntity>>

    suspend fun getAllStoriesWithLocation(): StoryBaseResponse

    suspend fun getDetailStory(id: String): StoryBaseResponse

    suspend fun submitStory(description: String, fileMultipart: MultipartBody.Part, lat: Double? = null, long: Double? = null): BaseResponse

    suspend fun setIsLogin(isLogin: Boolean)

    fun getIsLogin(): Flow<Boolean>

    suspend fun setToken(token: String)

    suspend fun getToken(): String

    suspend fun setUser(user: String)

    fun getUser(): Flow<String>

    fun setDummyStories(stories: List<StoryResponse>)

    suspend fun setFavorite(isFavorite: Boolean, id: String)

    suspend fun getIsFavorite(id: String): Boolean

    fun getAllFavorite(): LiveData<PagingData<StoryEntity>>

    suspend fun getFavorite(): List<StoryEntity>
}