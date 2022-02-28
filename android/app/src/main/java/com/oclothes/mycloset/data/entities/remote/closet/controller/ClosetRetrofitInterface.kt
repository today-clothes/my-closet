package com.oclothes.mycloset.data.entities.remote.closet.controller

import com.oclothes.mycloset.data.entities.remote.closet.dto.*
import retrofit2.Call
import retrofit2.http.*

interface ClosetRetrofitInterface {
    @GET("/closets")
    fun getClosets(@Query("page") page : Int): Call<ClosetResponse>

    @POST("/closets")
    fun createCloset(@Body createClosetDto: CreateClosetDto) : Call<CreateResponse>

    @DELETE("/closets/{id}")
    fun deleteCloset(@Path("id") id : Int) : Call<DeleteResponse>

    @PATCH("/closets/{id}/name")
    fun updateCloset(@Path("id") id : Int, @Body updateClosetDto: UpdateClosetDto) : Call<UpdateResponse>
}