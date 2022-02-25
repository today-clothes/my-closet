package com.oclothes.mycloset.data.entities.remote.user.dto

import com.google.gson.annotations.SerializedName

data class UserDto (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)