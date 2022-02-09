package com.oclothes.mycloset.data.entities.remote.style

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StyleRetrofitInterface {
    @GET("/clothes")
    fun getStyle(@Query("closetId") closetId : Int, @Query("page") page : Int) : Call<StyleResponse>
}