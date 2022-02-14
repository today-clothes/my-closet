package com.oclothes.mycloset.data.entities.remote.tag

import retrofit2.Call
import retrofit2.http.GET

interface TagRetrofitInterface {

    @GET("/tags")
    fun getTags(): Call<TagResponse>
}