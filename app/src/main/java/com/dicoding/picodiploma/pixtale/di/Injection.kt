package com.dicoding.picodiploma.pixtale.di

import android.content.Context
import com.dicoding.picodiploma.pixtale.data.UserRepository
import com.dicoding.picodiploma.pixtale.data.network.ApiConfig
import com.dicoding.picodiploma.pixtale.view.camera.StoryRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService, context)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}