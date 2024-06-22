package com.dicoding.picodiploma.pixtale.view.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.pixtale.data.UserRepository
import kotlinx.coroutines.launch
class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean?>()
    val loginResult: LiveData<Boolean?>
        get() = _loginResult

    fun login(context: Context, email: String, password: String) {
        _loginResult.postValue(null)
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (!response.error && response.loginResult != null) {
                    repository.saveSession(response.loginResult.token)
                    _loginResult.postValue(true)
                } else {
                    _loginResult.postValue(false)
                }
            } catch (e: Exception) {
                _loginResult.postValue(false)
            }
        }
    }
}