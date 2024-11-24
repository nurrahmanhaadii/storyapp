package id.haadii.dicoding.submission.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import id.haadii.dicoding.submission.domain.model.BaseResponse
import id.haadii.dicoding.submission.domain.model.Login
import id.haadii.dicoding.submission.domain.model.Story
import id.haadii.dicoding.submission.domain.model.StoryBase
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
interface MainRepository {

    suspend fun register(name: String, email: String, password: String): BaseResponse

    suspend fun login(email: String, password: String): Login

    fun getAllStories(): LiveData<PagingData<Story>>

    suspend fun getAllStoriesWithLocation(): StoryBase

    suspend fun getDetailStory(id: String): StoryBase

    suspend fun submitStory(description: String, image: File, lat: Double? = null, long: Double? = null): BaseResponse

    suspend fun setIsLogin(isLogin: Boolean)

    fun getIsLogin(): Flow<Boolean>

    suspend fun setToken(token: String)

    suspend fun getToken(): String

    suspend fun setUser(user: String)

    fun getUser(): Flow<String>

    fun setDummyStories(stories: List<Story>)

    suspend fun setFavorite(isFavorite: Boolean, id: String)

    suspend fun getIsFavorite(id: String): Boolean

    suspend fun getFavorite(): List<Story>
}