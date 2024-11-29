package id.hadi.dicoding.storyapp.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.haadii.dicoding.submission.core.StoryPreferenceManager
import id.haadii.dicoding.submission.core.local.database.StoryDatabase
import id.haadii.dicoding.submission.core.network.api.ApiService
import id.haadii.dicoding.submission.domain.repository.MainRepository
import id.haadii.dicoding.submission.domain.usecase.StoryUseCase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun provideApiService(): ApiService
    fun provideStoryPreferenceManager(): StoryPreferenceManager
    fun provideMainRepository(): MainRepository
    fun provideStoryUsecase(): StoryUseCase
    fun provideDatabase(): StoryDatabase
}