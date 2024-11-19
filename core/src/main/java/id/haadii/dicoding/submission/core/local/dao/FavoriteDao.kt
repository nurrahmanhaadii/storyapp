package id.haadii.dicoding.submission.core.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.haadii.dicoding.submission.core.local.entity.Favorite
import id.haadii.dicoding.submission.core.local.entity.StoryEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE id = :id")
    suspend fun getFavorite(id: String): Favorite?

    @Query("""
        SELECT s.*
        FROM story s
        INNER JOIN favorite f ON s.id = f.id
    """)
    fun getAllFavorite(): PagingSource<Int, StoryEntity>

    @Query("""
        SELECT s.*
        FROM story s
        INNER JOIN favorite f ON s.id = f.id
    """)
    suspend fun getFavorite(): List<StoryEntity>

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun deleteFavorite(id: String)
}