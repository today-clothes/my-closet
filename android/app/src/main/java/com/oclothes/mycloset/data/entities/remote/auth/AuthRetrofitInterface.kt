package com.oclothes.mycloset.data.entities.remote.auth

import com.oclothes.mycloset.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("users")
    fun signUp(@Body signUpDto: SignUpDto): Call<SignUpResponse>

    @POST("users/login")
    fun login(@Body userDto : UserDto): Call<LoginResponse>

    @GET("users/auto-login")
    fun autoLogin(): Call<LoginResponse>
}