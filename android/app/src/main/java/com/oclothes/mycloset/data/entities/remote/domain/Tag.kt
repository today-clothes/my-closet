package com.oclothes.mycloset.data.entities.remote.domain

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)
