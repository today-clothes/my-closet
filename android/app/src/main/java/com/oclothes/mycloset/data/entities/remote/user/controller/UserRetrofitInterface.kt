package com.oclothes.mycloset.data.entities.remote.user.controller

import com.oclothes.mycloset.data.entities.remote.user.dto.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserRetrofitInterface {
    @POST("/users")
    fun signUp(@Body signUpDto: SignUpDto): Call<SignUpResponse>

    @POST("/users/login")
    fun login(@Body userDto : UserDto): Call<LoginResponse>

    @POST("/users/login")
    fun autoLogin(@Body UserDto : UserDto): Call<LoginResponse>

    @GET("/users")
    fun getUserInfo() : Call<InfoResponse>

    @PATCH("/users/my-account")
    fun updateAccountInfo(@Body accountDto: AccountDto) : Call<UpdateResponse>

    @PATCH("/users/my-profile")
    fun updatePersonalInfo(@Body profileDto: PersonalDto) : Call<UpdateResponse>
}