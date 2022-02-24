package com.oclothes.mycloset.data.entities.remote.style.dto

import com.google.gson.annotations.SerializedName
import com.oclothes.mycloset.data.entities.remote.domain.Tag

data class InfoResponse(
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : InfoData
)

data class InfoData(
    @SerializedName("content") val content : String,
    @SerializedName("height") val height : Int,
    @SerializedName("styleTitle") val styleTitle: String?,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("userName") val userName : String,
    @SerializedName("weight") val weight : Int,
    @SerializedName("eventTags") val eventTags : ArrayList<Tag>,
    @SerializedName("moodTags") val moodTags : ArrayList<Tag>,
    @SerializedName("seasonTags") val seasonTags : ArrayList<Tag>,
    @SerializedName("imgUrl") val imgUrl : String,
    @SerializedName("locked") val locked : Boolean
)