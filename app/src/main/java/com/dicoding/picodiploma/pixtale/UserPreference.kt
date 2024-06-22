package com.dicoding.picodiploma.pixtale

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.picodiploma.pixtale.data.pref.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreference {
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val LOGGED_IN_KEY = booleanPreferencesKey("isLoggedIn")

    suspend fun saveSession(context: Context, token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences[LOGGED_IN_KEY] = true
        }
    }

    fun getSession(context: Context): Flow<UserModel> {
        return context.dataStore.data.map { preferences ->
            val email = preferences[EMAIL_KEY] ?: ""
            val token = preferences[TOKEN_KEY] ?: ""
            val isLoggedIn = preferences[LOGGED_IN_KEY] ?: false
            UserModel(email, token, isLoggedIn)
        }
    }


    fun getToken(context: Context): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[TOKEN_KEY] ?: ""
            }
    }

    suspend fun clearSession(context: Context) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = ""
            preferences[LOGGED_IN_KEY] = false
        }
    }
}