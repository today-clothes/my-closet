package com.oclothes.mycloset.data.entities.remote.domain

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("errorMessage") val errorMessage : String
)
