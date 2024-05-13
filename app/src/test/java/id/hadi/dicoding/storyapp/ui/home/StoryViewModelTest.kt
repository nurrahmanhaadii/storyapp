package id.hadi.dicoding.storyapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import androidx.paging.testing.asSnapshot
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.Turbine
import app.cash.turbine.test
import com.google.gson.Gson
import id.hadi.dicoding.storyapp.data.MainRepository
import id.hadi.dicoding.storyapp.data.network.response.Story
import id.hadi.dicoding.storyapp.getOrAwaitValueTest
import id.hadi.dicoding.storyapp.repository.FakeMainRepository
import id.hadi.dicoding.storyapp.utils.DataUtils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import com.google.common.truth.Truth.assertThat
import id.hadi.dicoding.storyapp.utils.FakePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.internal.wait
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.Duration
import app.cash.turbine.test

/**
 * Created by nurrahmanhaadii on 02,April,2024
 */
@OptIn(DelicateCoroutinesApi::class)
@ExperimentalCoroutinesApi
class StoryViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: StoryViewModel

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    @Before
    fun setup() {
        viewModel = StoryViewModel(FakeMainRepository())
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when Get Stories Should Not Null and Return Success`() = runTest {
        val items = viewModel.getAllStories().getOrAwaitValueTest()
        val flowItems = flowOf(items)

        val snapshotItem = flowItems.asSnapshot()

        flowItems.test {
            assertThat(awaitItem()).isNotNull()
            assertThat(snapshotItem.size).isEqualTo(30)
            assertThat(snapshotItem[0].id).isEqualTo("0")
            awaitComplete()
        }
    }

    @Test
    fun `when Get Stories Empty and Return Success`() = runTest {
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