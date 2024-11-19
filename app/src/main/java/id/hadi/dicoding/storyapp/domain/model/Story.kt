package id.hadi.dicoding.storyapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val description: String,
    val id: String,
    val lat: Double?,
    val lon: Double?,
    val name: String,
    val photoUrl: String,
    val isFavorite: Boolean = false
): Parcelable
