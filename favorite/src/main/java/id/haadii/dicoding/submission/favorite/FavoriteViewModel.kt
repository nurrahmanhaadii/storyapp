package id.haadii.dicoding.submission.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import id.haadii.dicoding.submission.core.local.entity.StoryEntity
import id.hadi.dicoding.storyapp.domain.StoryUseCase
import id.hadi.dicoding.storyapp.helper.mapToDomain
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class FavoriteViewModel(private val useCase: StoryUseCase) : ViewModel() {
    private var listFavorite = listOf<StoryEntity>()
    fun getAllFavorite(): LiveData<PagingData<StoryEntity>> = useCase.getAllFavorite()

    fun getFavorite() = flow {
        emit(useCase.getFavorite())
    }.map {
        listFavorite = it
        it.map { data -> data.mapToDomain() }
    }.asLiveData()

    fun searchFavorite(query: String) = flow {
        emit(query)
    }
        .debounce(300)
        .distinctUntilChanged()
        .mapLatest { text ->
            if (text.isEmpty()) return@mapLatest listFavorite
            listFavorite.filter { it.description.contains(text) }
        }.map {
            it.map { data -> data.mapToDomain() }
        }
        .asLiveData()

}