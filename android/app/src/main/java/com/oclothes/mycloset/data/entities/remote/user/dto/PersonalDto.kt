package com.oclothes.mycloset.data.entities.remote.user.dto

import com.google.gson.annotations.SerializedName

data class PersonalDto (
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight : Int
)