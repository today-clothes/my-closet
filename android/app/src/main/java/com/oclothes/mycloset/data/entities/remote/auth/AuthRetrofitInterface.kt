package com.oclothes.mycloset.data.entities.remote.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body userDto: UserDto): Call<SignUpResponse>

    @POST("/users/login")
    fun login(@Body userDto : UserDto): Call<LoginResponse>

    @GET("/users/auto-login")
    fun autoLogin(): Call<LoginResponse>
}