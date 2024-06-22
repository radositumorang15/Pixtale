package com.dicoding.picodiploma.pixtale.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.pixtale.data.UserRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: UserRepository) : ViewModel() {

    private val _registrationResult = MutableLiveData<Result<String>?>()
    val registrationResult: LiveData<Result<String>?> = _registrationResult

    fun register(name: String, email: String, password: String) {
        _registrationResult.postValue(null)
        viewModelScope.launch {
            try {
                val response = repository.register(name, email, password)
                if (response.error == false) {
                    _registrationResult.postValue(Result.success("Pendaftaran berhasil silahkan login ke akun anda!!"))
                } else {
                    _registrationResult.postValue(Result.failure(Exception("Pendaftaran gagal akun dengan email tersebut sudah terdaftar")))
                }
            } catch (e: Exception) {
                _registrationResult.postValue(Result.failure(Exception("Pendaftaran gagal akun dengan email tersebut sudah terdaftar")))
            }
        }
    }
}