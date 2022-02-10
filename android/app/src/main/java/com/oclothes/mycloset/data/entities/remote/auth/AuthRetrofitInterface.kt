package com.oclothes.mycloset.data.entities.remote.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body signUpDto: SignUpDto): Call<SignUpResponse>

    @POST("/users/login")
    fun login(@Body userDto : UserDto): Call<LoginResponse>

    @POST("/users/login")
    fun autoLogin(@Body UserDto : UserDto): Call<LoginResponse>
}