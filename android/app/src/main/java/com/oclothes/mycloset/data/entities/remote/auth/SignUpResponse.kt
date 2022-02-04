package com.oclothes.mycloset.data.entities.remote.auth

import com.google.gson.annotations.SerializedName

data class Email(
    @SerializedName("email") val email : String
)

data class SignUpResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data : Email
)