package com.oclothes.mycloset.data.entities.remote.closet.dto

import com.google.gson.annotations.SerializedName

data class CreateClosetDto(
    @SerializedName("name") val name : String
)
