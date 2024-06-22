package com.dicoding.picodiploma.pixtale.view.camera

import android.util.Log
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.pixtale.data.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException


class StoryRepository(private val apiService: ApiService) {

    fun uploadStory(token: String, file: MultipartBody.Part, description: RequestBody) = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postStory("Bearer $token", file, description)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("StoryRepository", "uploadStory: ${e.message}")
            emit(Result.Error(e))
        }
    }
}