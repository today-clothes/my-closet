package com.oclothes.mycloset.data.entities.remote.style.dto

import com.google.gson.annotations.SerializedName

data class DeleteResponse(
    @SerializedName("message") val message : String
)