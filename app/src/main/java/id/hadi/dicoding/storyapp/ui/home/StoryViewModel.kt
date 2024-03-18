package id.hadi.dicoding.storyapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hadi.dicoding.storyapp.data.MainRepository
import id.hadi.dicoding.storyapp.data.model.Resource
import id.hadi.dicoding.storyapp.helper.Utils
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.io.File
import javax.inject.Inject

/**
 * Created by nurrahmanhaadii on 13,March,2024
 */
@HiltViewModel
class StoryViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {

    fun getAllStories() = flow {
        emit(repository.getAllStories())
    }.map {
        Resource.Success(data = it) as Resource<*>
    }.onStart {
        emit(Resource.Loading)
    }.catch {
        emit(Resource.Error(data = it))
    }.asLiveData()

    fun getDetailStory(id: String) = flow {
        emit(repository.getDetailStory(id))
    }.map {
        Resource.Success(data = it) as Resource<*>
    }.onStart {
        emit(Resource.Loading)
    }.catch {
        emit(Resource.Error(data = it))
    }.asLiveData()

    fun submitStory(description: String, image: File) = flow {
        val fileMultipart = Utils.getMultipartBodyFile("photo", image)
        emit(repository.submitStory(description, fileMultipart))
    }.map {
        Resource.Success(data = it) as Resource<*>
    }.onStart {
        emit(Resource.Loading)
    }.catch {
        emit(Resource.Error(data = it))
    }.asLiveData()
}