package com.oclothes.mycloset.data.entities.remote.auth

import com.google.gson.annotations.SerializedName

data class AuthTemp(@SerializedName("accessToken") val jwt: String)

data class AuthResponseTemp(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
)