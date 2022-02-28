package com.oclothes.mycloset.data.entities.remote.closet.dto

import com.google.gson.annotations.SerializedName

data class UpdateResponse (
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : UpdateClosetData
)

data class UpdateClosetData(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)
