package id.haadii.dicoding.submission.core.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.haadii.dicoding.submission.core.local.entity.StoryEntity

@Dao
interface StoryDao {
    @Query("SELECT * FROM story")
    fun getAllStories(): PagingSource<Int, StoryEntity>
    @Query("SELECT * FROM story")
    suspend fun getStories(): List<StoryEntity>
    @Query("DELETE FROM story")
    fun deleteAll()
    @Query("SELECT * FROM story WHERE id = :id")
    suspend fun getStoryById(id: String): StoryEntity?
    @Query("DELETE FROM story WHERE id = :id")
    fun deleteStoryById(id: String)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(storyEntity: List<StoryEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(storyEntity: StoryEntity)
    @Query("""
        UPDATE story
        SET isFavorite = CASE
            WHEN id IN (SELECT id FROM favorite) THEN 1
            ELSE 0
        END
    """)
    suspend fun updateStoryFavorites()
}