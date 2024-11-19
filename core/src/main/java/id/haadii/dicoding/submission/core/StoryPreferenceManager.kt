package id.haadii.dicoding.submission.core

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
class StoryPreferenceManager(private val context: Context) {

    fun getIsLogin() : Flow<Boolean> {
        return context.dataStore.data.map { pref ->
            pref[LOGIN_KEY] ?: false
        }
    }

    suspend fun setIsLogin(isLogin: Boolean) {
        context.dataStore.edit { pref ->
            pref[LOGIN_KEY] = isLogin
        }
    }

    suspend fun getToken() : String {
        return context.dataStore.data.map { pref ->
            pref[TOKEN_KEY] ?: ""
        }.first()
    }

    suspend fun setToken(token: String) {
        context.dataStore.edit { pref ->
            pref[TOKEN_KEY] = token
        }
    }

    fun getUser() : Flow<String> {
        return context.dataStore.data.map { pref ->
            pref[USER_KEY] ?: ""
        }
    }

    suspend fun setUser(user: String) {
        context.dataStore.edit { pref ->
            pref[USER_KEY] = user
        }
    }

    companion object {
        private const val DATA_STORE_NAME = "story_preference"
        private val LOGIN_KEY = booleanPreferencesKey("login_key")
        private val TOKEN_KEY = stringPreferencesKey("token_key")
        private val USER_KEY = stringPreferencesKey("user_key")
        private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    }
}