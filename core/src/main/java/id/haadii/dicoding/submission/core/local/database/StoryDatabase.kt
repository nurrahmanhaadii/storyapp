package id.haadii.dicoding.submission.core.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.haadii.dicoding.submission.core.local.dao.FavoriteDao
import id.haadii.dicoding.submission.core.local.dao.RemoteKeysDao
import id.haadii.dicoding.submission.core.local.dao.StoryDao
import id.haadii.dicoding.submission.core.local.entity.Favorite
import id.haadii.dicoding.submission.core.local.entity.RemoteKeys
import id.haadii.dicoding.submission.core.local.entity.StoryEntity

@Database(entities = [StoryEntity::class, RemoteKeys::class, Favorite::class], version = 2)
abstract class StoryDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}