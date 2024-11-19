package id.hadi.dicoding.storyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.haadii.dicoding.submission.core.local.entity.StoryEntity
import id.haadii.dicoding.submission.core.model.Resource
import id.haadii.dicoding.submission.core.repositories.MainRepository
import id.hadi.dicoding.storyapp.helper.Utils
import id.hadi.dicoding.storyapp.helper.mapToDomain
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * Created by nurrahmanhaadii on 13,March,2024
 */
@HiltViewModel
class StoryViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    fun getAllStories(): LiveData<PagingData<StoryEntity>> = repository.getAllStories()

    fun getAllFavorite(): LiveData<PagingData<StoryEntity>> = repository.getAllFavorite()

    fun getAllStoriesWithLocation() = flow {
        emit(repository.getAllStoriesWithLocation())
    }.map {
        Resource.Success(data = it.mapToDomain()) as Resource<*>
    }.onStart {
        emit(Resource.Loading)
    }.catch {
        emit(Resource.Error(data = it))
    }.asLiveData()

    fun getDetailStory(id: String) = flow {
        emit(repository.getDetailStory(id))
    }.map {
        Resource.Success(data = it.mapToDomain()) as Resource<*>
    }.onStart {
        emit(Resource.Loading)
    }.catch {
        emit(Resource.Error(data = it))
    }.asLiveData()

    fun submitStory(description: String, image: File, lat: Double?, long: Double?) = flow {
        val fileMultipart = Utils.getMultipartBodyFile("photo", image)
        emit(repository.submitStory(description, fileMultipart, lat, long))
    }.map {
        Resource.Success(data = it) as Resource<*>
    }.onStart {
        emit(Resource.Loading)
    }.catch {
        emit(Resource.Error(data = it))
    }.asLiveData()

    fun setFavorite(isFavorite: Boolean, id: String) {
        viewModelScope.launch {
            repository.setFavorite(isFavorite, id)
        }
    }

    fun getIsFavorite(id: String) = flow {
        emit(repository.getIsFavorite(id))
    }.asLiveData()
}