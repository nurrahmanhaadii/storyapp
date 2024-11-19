package id.haadii.dicoding.submission.core.network.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import id.haadii.dicoding.submission.core.StoryPreferenceManager
import okhttp3.OkHttpClient
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