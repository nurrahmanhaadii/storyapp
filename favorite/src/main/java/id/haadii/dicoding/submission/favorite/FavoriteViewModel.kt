package id.haadii.dicoding.submission.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.haadii.dicoding.submission.domain.model.Story
import id.haadii.dicoding.submission.domain.usecase.StoryUseCase
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val useCase: StoryUseCase) : ViewModel() {
    private var listFavorite = listOf<Story>()

    fun getFavorite() = flow {
        emit(useCase.getFavorite())
    }.map {
        listFavorite = it
        it
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
            it
        }
        .asLiveData()

}