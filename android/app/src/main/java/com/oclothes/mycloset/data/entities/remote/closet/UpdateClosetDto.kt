package com.oclothes.mycloset.data.entities.remote.closet

import com.google.gson.annotations.SerializedName

data class UpdateClosetDto(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)
