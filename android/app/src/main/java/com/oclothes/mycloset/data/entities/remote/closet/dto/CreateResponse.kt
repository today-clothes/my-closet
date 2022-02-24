package com.oclothes.mycloset.data.entities.remote.closet.dto

import com.google.gson.annotations.SerializedName

data class CreateResponse(
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : ClosetCreateData
)

data class ClosetCreateData(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)
