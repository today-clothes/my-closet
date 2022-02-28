package com.oclothes.mycloset.data.entities.remote.user.dto

import com.google.gson.annotations.SerializedName

data class AccountDto(
    @SerializedName("age") val age: Int,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("gender") val gender : String
)