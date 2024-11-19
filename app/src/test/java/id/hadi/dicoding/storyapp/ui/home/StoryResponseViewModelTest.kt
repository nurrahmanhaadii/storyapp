package id.hadi.dicoding.storyapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import id.hadi.dicoding.storyapp.getOrAwaitValueTest
import id.hadi.dicoding.storyapp.repository.FakeMainRepository
import id.hadi.dicoding.storyapp.utils.DataUtils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by nurrahmanhaadii on 02,April,2024
 */
@OptIn(DelicateCoroutinesApi::class)
@ExperimentalCoroutinesApi
class StoryResponseViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: StoryViewModel
    private lateinit var repository: FakeMainRepository


    @Before
    fun setup() {
        repository = FakeMainRepository()
        viewModel = StoryViewModel(repository)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when Get Stories Should Not Null and Return Success`() = runTest {
        repository.setDummyStories(DataUtils.generateDummyStory())
        val items = viewModel.getAllStories().getOrAwaitValueTest()
        val flowItems = flowOf(items)

        val snapshotItem = flowItems.asSnapshot()

        flowItems.test {
            assertThat(awaitItem()).isNotNull()
            assertThat(snapshotItem.size).isEqualTo(30)
            assertThat(snapshotItem[0]).isEqualTo(DataUtils.generateDummyStory()[0])
            awaitComplete()
        }
    }

    @Test
    fun `when Get Stories Empty and Return Success`() = runTest {
        repository.setDummyStories(listOf())
        val items = viewModel.getAllStories().getOrAwaitValueTest()
        val flowItems = flowOf(items)

        val snapshotItem = flowItems.asSnapshot()

        flowItems.test {
            assertThat(awaitItem()).isNotNull()
            assertThat(snapshotItem.size).isEqualTo(0)
            awaitComplete()
        }
    }
}