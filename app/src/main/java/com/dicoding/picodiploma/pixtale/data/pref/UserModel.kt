package com.dicoding.picodiploma.pixtale.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLoggedIn: Boolean = false
)