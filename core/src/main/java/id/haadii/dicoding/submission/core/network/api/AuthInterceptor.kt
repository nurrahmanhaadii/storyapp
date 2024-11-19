package id.haadii.dicoding.submission.core.network.api

import id.haadii.dicoding.submission.core.StoryPreferenceManager
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