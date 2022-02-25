package com.oclothes.mycloset.data.entities.remote.closet.dto

import com.google.gson.annotations.SerializedName

data class ClosetResponse(
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : ClosetData
)

data class ClosetData(
    @SerializedName("contentsCount") val contentsCount : Int,
    @SerializedName("contents") val contents : List<Closet>
)

data class Closet(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("thumbnail") val thumbnail : String
)

