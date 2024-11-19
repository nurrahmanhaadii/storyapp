package id.haadii.dicoding.submission.core.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")
data class StoryEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "lat")
    val lat: Double?,
    @ColumnInfo(name = "lon")
    val lon: Double?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "photoUrl")
    val photoUrl: String,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
)
