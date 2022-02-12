package com.oclothes.mycloset.data.entities.remote.style

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : InfoData
)

data class InfoData(
    @SerializedName("content") val content : String,
    @SerializedName("height") val height : Int,
    @SerializedName("styleTitle") val styleTitle: String,
    @SerializedName("updateAt") val updateAt : String,
    @SerializedName("userName") val userName : String,
    @SerializedName("weight") val weight : Int,
    @SerializedName("eventTags") val eventTags : ArrayList<Tag>,
    @SerializedName("moodTags") val moodTags : ArrayList<Tag>,
    @SerializedName("seasonTags") val seasonTags : ArrayList<Tag>
)

data class Tag(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)