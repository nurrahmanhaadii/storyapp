package id.haadii.dicoding.submission.core.network.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import id.haadii.dicoding.submission.core.StoryPreferenceManager
import id.haadii.dicoding.submission.core.helpers.SSLCertificateConfigurator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.X509TrustManager

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
object RetrofitBuilder {
    private const val BASE_URL = "https://story-api.dicoding.dev/v1/"

    private fun getRetrofit(context: Context): Retrofit {
        val trustManagerFactory = SSLCertificateConfigurator.getTrustManager(context)
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers:" + trustManagers.contentToString())
        }
        val trustManager = trustManagers[0] as X509TrustManager
        val client = OkHttpClient.Builder()
            .sslSocketFactory(SSLCertificateConfigurator.getSSLConfiguration(context).socketFactory, trustManager)
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