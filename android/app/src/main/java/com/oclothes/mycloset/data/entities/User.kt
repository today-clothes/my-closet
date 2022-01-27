package com.oclothes.mycloset.data.entities

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("nickname") val nickname: String,
)