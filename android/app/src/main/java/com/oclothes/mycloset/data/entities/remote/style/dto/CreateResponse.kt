package com.oclothes.mycloset.data.entities.remote.style.dto

import com.google.gson.annotations.SerializedName

data class CreateResponse(
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : CreateData
)

data class CreateData(
    @SerializedName("closetId") val closetId : Int,
    @SerializedName("clothesId") val clothesId : Int,
    @SerializedName("imgUrl") val imgUrl : String,
)