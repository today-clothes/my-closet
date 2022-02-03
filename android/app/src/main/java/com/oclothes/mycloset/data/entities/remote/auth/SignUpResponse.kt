package com.oclothes.mycloset.data.entities.remote.auth

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("email") val email: String,
    @SerializedName("message") val message: String,
)