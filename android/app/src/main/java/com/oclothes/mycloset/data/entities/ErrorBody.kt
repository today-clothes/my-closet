package com.oclothes.mycloset.data.entities

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("errorMessage") val errorBody : String
)
