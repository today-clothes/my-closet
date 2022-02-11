package com.oclothes.mycloset.data.entities.remote.auth

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data : InfoResponseData
)

data class InfoResponseData(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("age") val age : Int,
    @SerializedName("height") val height : Int,
    @SerializedName("weight") val weight : Int,
)

