package id.hadi.dicoding.storyapp.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.hadi.dicoding.storyapp.R
import id.hadi.dicoding.storyapp.data.MainRepository
import id.hadi.dicoding.storyapp.data.MainRepositoryImpl
import id.hadi.dicoding.storyapp.data.api.RetrofitBuilder
import id.hadi.dicoding.storyapp.data.network.api.ApiService
import id.hadi.dicoding.storyapp.helper.StoryPreferenceManager
import id.hadi.dicoding.storyapp.ui.home.StoryAdapter
import kotlinx.coroutines.flow.first
import javax.inject.Singleton

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(@ApplicationContext context: Context): ApiService = RetrofitBuilder.apiService(context)

    @Singleton
    @Provides
    fun provideStoryPreferenceManager(@ApplicationContext context: Context) = StoryPreferenceManager(context)

    @Singleton
    @Provides
    fun provideMainRepository(
        apiService: ApiService,
        dataStoryPreferenceManager: StoryPreferenceManager
    ): MainRepository = MainRepositoryImpl(apiService, dataStoryPreferenceManager)

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context) =
        Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_launcher_foreground)
            )

    @Provides
    fun provideStoryAdapter(glide: RequestManager) = StoryAdapter(glide)
}