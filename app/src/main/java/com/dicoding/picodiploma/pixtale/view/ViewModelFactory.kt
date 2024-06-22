package com.dicoding.picodiploma.pixtale.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.pixtale.di.Injection
import com.dicoding.picodiploma.pixtale.view.camera.CameraViewModel
import com.dicoding.picodiploma.pixtale.view.login.LoginViewModel
import com.dicoding.picodiploma.pixtale.view.main.MainViewModel
import com.dicoding.picodiploma.pixtale.view.signup.SignupViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                val repository = Injection.provideUserRepository(context)
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                val repository = Injection.provideUserRepository(context)
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                val repository = Injection.provideUserRepository(context)
                SignupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                val repository = Injection.provideStoryRepository(context)
                CameraViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}