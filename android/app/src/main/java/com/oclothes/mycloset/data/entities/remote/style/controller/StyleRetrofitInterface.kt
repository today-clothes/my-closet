package com.oclothes.mycloset.data.entities.remote.style.controller

import com.oclothes.mycloset.data.entities.remote.style.dto.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface StyleRetrofitInterface {
    @GET("/clothes/search")
    fun searchClothes(
        @QueryMap queryNum : Map<String, Int>,
        @QueryMap queryString : Map<String, String>,
        @Query("eventTagIds") eventTagIds : ArrayList<Int>,
        @Query("moodTagIds") moodTagIds : ArrayList<Int>,
        @Query("seasonTagIds") seasonTagIds : ArrayList<Int>,
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
    fun getClothInfo(@Path("id") id : Int) : Call<InfoResponse>

    @DELETE("/clothes/{id}")
    fun deleteCloth(@Path("id") id : Int) : Call<DeleteResponse>

    @PATCH("/clothes/{id}/locked")
    fun lockCloth(@Path("id") id : Int) : Call<LockResponse>

    @GET("/clothes/recommend")
    fun recommendClothes() : Call<RecommendResponse>

}