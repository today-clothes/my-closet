package com.oclothes.mycloset.data.entities.remote.closet

import com.oclothes.mycloset.data.entities.remote.auth.CreateClosetResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ClosetRetrofitInterface {
    @GET("/closets")
    fun getClosets(): Call<ClosetResponse>

    @POST("/closets")
    fun createCloset(@Body createClosetDto: CreateClosetDto) : Call<CreateClosetResponse>
}