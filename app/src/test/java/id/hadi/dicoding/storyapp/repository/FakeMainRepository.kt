package id.hadi.dicoding.storyapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.liveData
import id.hadi.dicoding.storyapp.data.MainRepository
import id.hadi.dicoding.storyapp.data.StoryPagingSource
import id.hadi.dicoding.storyapp.data.network.request.LoginRequest
import id.hadi.dicoding.storyapp.data.network.request.RegisterRequest
import id.hadi.dicoding.storyapp.data.network.response.BaseResponse
import id.hadi.dicoding.storyapp.data.network.response.LoginResponse
import id.hadi.dicoding.storyapp.data.network.response.Story
import id.hadi.dicoding.storyapp.data.network.response.StoryResponse
import id.hadi.dicoding.storyapp.utils.DataUtils
import id.hadi.dicoding.storyapp.utils.FakePagingSource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

/**
 * Created by nurrahmanhaadii on 13,May,2024
 */
class FakeMainRepository: MainRepository {
    override suspend fun register(request: RegisterRequest): BaseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        TODO("Not yet implemented")
    }

    override fun getAllStories(): LiveData<PagingData<Story>> {
        // Define your test data

        val testData = mutableListOf<Story>()
//        testData.addAll(DataUtils.generateDummyStory())

        // Create the fake paging source with test data
        val fakePagingSource = FakePagingSource<Story>(testData)

        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { fakePagingSource }
        ).liveData
        return pager
    }

    override suspend fun getAllStoriesWithLocation(): StoryResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getDetailStory(id: String): StoryResponse {
        TODO("Not yet implemented")
    }

    override suspend fun submitStory(
        description: String,
        fileMultipart: MultipartBody.Part,
        lat: Double?,
        long: Double?
    ): BaseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun setIsLogin(isLogin: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getIsLogin(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setToken(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getToken(): String {
        TODO("Not yet implemented")
    }

    override suspend fun setUser(user: String) {
        TODO("Not yet implemented")
    }

    override fun getUser(): Flow<String> {
        TODO("Not yet implemented")
    }
}