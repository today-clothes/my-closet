package com.oclothes.mycloset.data.entities.remote.auth

import com.google.gson.annotations.SerializedName

data class CreateClosetResponseData(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
    )

data class CreateClosetResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data : CreateClosetResponseData
)