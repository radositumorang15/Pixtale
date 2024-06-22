package com.dicoding.picodiploma.pixtale.data.network

import com.dicoding.picodiploma.pixtale.data.response.FileUploadResponse
import com.dicoding.picodiploma.pixtale.data.response.LoginResponse
import com.dicoding.picodiploma.pixtale.data.response.RegisterResponse
import com.dicoding.picodiploma.pixtale.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") userToken: String,
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun postStory(
        @Header("Authorization") userToken: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): FileUploadResponse


}