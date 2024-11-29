package id.hadi.dicoding.storyapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.haadii.dicoding.submission.domain.model.BaseResponse
import id.haadii.dicoding.submission.domain.model.Login
import id.haadii.dicoding.submission.domain.model.Story
import id.haadii.dicoding.submission.domain.model.StoryBase
import id.haadii.dicoding.submission.domain.usecase.StoryUseCase
import id.hadi.dicoding.storyapp.utils.FakePagingSource
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * Created by nurrahmanhaadii on 13,May,2024
 */
class FakeStoryUseCase: StoryUseCase {

    private var dummyStories = listOf<Story>()
    override suspend fun register(userName: String, email: String, password: String): BaseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): Login {
        TODO("Not yet implemented")
    }

    override fun setDummyStories(stories: List<Story>) {
        dummyStories = stories
    }

    override suspend fun setFavorite(isFavorite: Boolean, id: String, story: Story) {
        TODO("Not yet implemented")
    }

    override suspend fun getIsFavorite(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getFavorite(): List<Story> {
        TODO("Not yet implemented")
    }

    override fun getAllStories(): LiveData<PagingData<Story>> {
        // Create the fake paging source with test data
        val fakePagingSource = FakePagingSource<Story>(dummyStories)

        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { fakePagingSource }
        ).liveData
        return pager
    }

    override suspend fun getAllStoriesWithLocation(): StoryBase {
        TODO("Not yet implemented")
    }

    override suspend fun getDetailStory(id: String): StoryBase {
        TODO("Not yet implemented")
    }

    override suspend fun submitStory(
        description: String,
        image: File,
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