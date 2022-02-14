package com.oclothes.mycloset.data.entities.remote.style

import com.google.gson.annotations.SerializedName

data class LockResponse(
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : LockData
)

data class LockData(
    @SerializedName("closetId") val closetId : Int,
    @SerializedName("locked") val isLocked : Boolean
)