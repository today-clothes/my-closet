package com.oclothes.mycloset.data.entities.remote.tag.controller

import com.oclothes.mycloset.data.entities.remote.tag.dto.TagResponse
import retrofit2.Call
import retrofit2.http.GET

interface TagRetrofitInterface {

    @GET("/tags")
    fun getTags(): Call<TagResponse>
}