package com.dicoding.picodiploma.pixtale.data

import com.dicoding.picodiploma.pixtale.UserPreference
import com.dicoding.picodiploma.pixtale.data.network.ApiService
import com.dicoding.picodiploma.pixtale.data.pref.UserModel
import com.dicoding.picodiploma.pixtale.data.response.ListStoryItem
import com.dicoding.picodiploma.pixtale.data.response.LoginResponse
import com.dicoding.picodiploma.pixtale.data.response.RegisterResponse
import android.content.Context
import kotlinx.coroutines.flow.Flow

class UserRepository(private val apiService: ApiService, private val context: Context) {

    suspend fun saveSession(token: String) {
        UserPreference.saveSession(context, token)
    }

    fun getToken(): Flow<String>{
        return UserPreference.getToken(context)
    }

    fun getSession(): Flow<UserModel> {
        return UserPreference.getSession(context)
    }

    suspend fun clearSession() {
        UserPreference.clearSession(context)
    }

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        val response = apiService.register(name, email, password)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty response body")
        } else {
            throw Exception("Registration failed: ${response.errorBody()?.string()}")
        }
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(email, password)
        if (response.isSuccessful) {
            val loginResponse = response.body()
            loginResponse?.loginResult?.token?.let {
                saveSession(it)
            } ?: throw Exception("Login failed: Token is null")
            return loginResponse ?: throw Exception("Login failed: Response body is null")
        } else {
            throw Exception("Login failed: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getStories(token: String): List<ListStoryItem> {
        val response = apiService.getStories("Bearer $token")
        return if (response.error == false) {
            response.listStory?.filterNotNull() ?: emptyList()
        } else {
            throw Exception("Error fetching stories: ${response.message}")
        }
    }
}