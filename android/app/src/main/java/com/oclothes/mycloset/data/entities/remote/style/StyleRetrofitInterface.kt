package com.oclothes.mycloset.data.entities.remote.style

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface StyleRetrofitInterface {
    @GET("/clothes/search")
    fun searchClothes(
        @QueryMap queryNum : Map<String, Int>,
        @QueryMap queryString : Map<String, String>,
    ) : Call<SearchResponse>

    @Multipart
    @POST("/clothes")
    fun createCloth(
        @Part closetId : MultipartBody.Part,
        @Part content : MultipartBody.Part,
        @Part eventIds : MultipartBody.Part,
        @Part file : MultipartBody.Part,
        @Part moodIds : MultipartBody.Part,
        @Part seasonIds : MultipartBody.Part,
        @Part styleTitle : MultipartBody.Part,
        @Part locked : MultipartBody.Part
    ) : Call<CreateResponse>


    @GET("/clothes/{id}")
    fun getClothInfo(
        @Path("id") id : Int) : Call<InfoResponse>

    @DELETE("/clothes/{id}")
    fun deleteCloth(@Path("id") id : Int) : Call<DeleteResponse>

    @PATCH("/clothes/{id}/locked")
    fun lockCloth(@Path("id") id : Int) : Call<LockResponse>


}