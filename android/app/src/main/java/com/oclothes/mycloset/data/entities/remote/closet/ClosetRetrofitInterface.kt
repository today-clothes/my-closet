package com.oclothes.mycloset.data.entities.remote.closet

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ClosetRetrofitInterface {
    @GET("/closets")
    fun getClosets(): Call<ClosetResponse>

    @POST("/closets")
    fun createCloset(@Body createClosetDto: CreateClosetDto) : Call<CreateResponse>
}