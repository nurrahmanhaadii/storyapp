package id.hadi.dicoding.storyapp.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.haadii.dicoding.submission.core.StoryPreferenceManager
import id.haadii.dicoding.submission.core.local.database.StoryDatabase
import id.haadii.dicoding.submission.core.network.api.ApiService
import id.haadii.dicoding.submission.core.network.api.RetrofitBuilder
import id.haadii.dicoding.submission.core.repositories.MainRepositoryImpl
import id.haadii.dicoding.submission.domain.repository.MainRepository
import id.haadii.dicoding.submission.domain.usecase.StoryInteractor
import id.haadii.dicoding.submission.domain.usecase.StoryUseCase
import id.hadi.dicoding.storyapp.R
import id.hadi.dicoding.storyapp.ui.home.StoryAdapter
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
        dataStoryPreferenceManager: StoryPreferenceManager,
        database: StoryDatabase
    ): MainRepository = MainRepositoryImpl(apiService, dataStoryPreferenceManager, database)

    @Singleton
    @Provides
    fun provideStoryUsecase(
        repository: MainRepository
    ): StoryUseCase = StoryInteractor(repository)


    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context) =
        Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_launcher_foreground)
            )

    @Provides
    fun provideStoryAdapter() = StoryAdapter()

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = StoryDatabase.getDatabase(context)

}