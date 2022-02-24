package com.oclothes.mycloset.data.entities.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class LoginToken(
    @SerializedName("accessToken") val jwt : String
)

data class LoginResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data : LoginToken
)