package com.oclothes.mycloset.data.entities.remote.user.dto

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data : String
)