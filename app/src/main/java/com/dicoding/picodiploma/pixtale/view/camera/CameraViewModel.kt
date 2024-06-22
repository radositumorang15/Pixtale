package com.dicoding.picodiploma.pixtale.view.camera

import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody


class CameraViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun uploadStory(token: String, file: MultipartBody.Part, description: RequestBody) =
        storyRepository.uploadStory(token, file, description)
}