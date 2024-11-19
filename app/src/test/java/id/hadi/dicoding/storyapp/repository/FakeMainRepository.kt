package id.hadi.dicoding.storyapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.haadii.dicoding.submission.core.network.request.LoginRequest
import id.haadii.dicoding.submission.core.network.request.RegisterRequest
import id.haadii.dicoding.submission.core.network.response.BaseResponse
import id.haadii.dicoding.submission.core.network.response.LoginResponse
import id.haadii.dicoding.submission.core.network.response.StoryBaseResponse
import id.haadii.dicoding.submission.core.network.response.StoryResponse
import id.hadi.dicoding.storyapp.utils.FakePagingSource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

/**
 * Created by nurrahmanhaadii on 13,May,2024
 */
class FakeMainRepository: MainRepository {

    private var dummyStories = listOf<StoryResponse>()
    override suspend fun register(request: RegisterRequest): BaseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        TODO("Not yet implemented")
    }

    override fun setDummyStories(stories: List<StoryResponse>) {
        dummyStories = stories
    }

    override fun getAllStories(): LiveData<PagingData<StoryResponse>> {
        // Create the fake paging source with test data
        val fakePagingSource = FakePagingSource<StoryResponse>(dummyStories)

        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { fakePagingSource }
        ).liveData
        return pager
    }

    override suspend fun getAllStoriesWithLocation(): StoryBaseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getDetailStory(id: String): StoryBaseResponse {
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