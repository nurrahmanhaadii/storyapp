package id.hadi.dicoding.storyapp.data.network.api

import id.hadi.dicoding.storyapp.helper.StoryPreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by nurrahmanhaadii on 13,March,2024
 */
class AuthInterceptor(private val dataStoryPreferenceManager: StoryPreferenceManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()


        val token = runBlocking {
            dataStoryPreferenceManager.getToken()
        }
        // Get token from your storage

        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(authorizedRequest)
    }


}