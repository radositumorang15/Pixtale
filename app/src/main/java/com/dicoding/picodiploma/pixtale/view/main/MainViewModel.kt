package com.dicoding.picodiploma.pixtale.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.pixtale.data.UserRepository
import com.dicoding.picodiploma.pixtale.data.pref.UserModel
import com.dicoding.picodiploma.pixtale.data.response.ListStoryItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _userSession = MutableLiveData<UserModel>()
    val userSession: LiveData<UserModel> get() = _userSession

    private val _stories = MutableLiveData<List<ListStoryItem>>()
    val stories: LiveData<List<ListStoryItem>> = _stories

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    init {
        loadUserSession()
    }

    private fun loadUserSession() {
        viewModelScope.launch {
            userRepository.getSession().collect { userModel ->
                _userSession.value = userModel
            }
        }
    }

    fun fetchStories() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val storyList = userRepository.getStories(userRepository.getToken().first())
                _stories.value = storyList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clearSession()
            loadUserSession()
        }
    }
}