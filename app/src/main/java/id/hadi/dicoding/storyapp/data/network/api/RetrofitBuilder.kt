package id.hadi.dicoding.storyapp.data.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import id.hadi.dicoding.storyapp.data.network.api.ApiService
import id.hadi.dicoding.storyapp.data.network.api.AuthInterceptor
import id.hadi.dicoding.storyapp.helper.StoryPreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
object RetrofitBuilder {
    private const val BASE_URL = "https://story-api.dicoding.dev/v1/"

    private fun getRetrofit(context: Context): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .addInterceptor(AuthInterceptor(StoryPreferenceManager(context)))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun apiService(context: Context): ApiService = getRetrofit(context).create(ApiService::class.java)

}