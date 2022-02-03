package com.oclothes.mycloset.data.entities.remote.auth

import com.google.gson.annotations.SerializedName

data class SignUpDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)