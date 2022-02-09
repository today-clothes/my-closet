package com.oclothes.mycloset.data.entities.remote.tag

import com.google.gson.annotations.SerializedName

data class TagResponse(
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : TagResponseData
)

data class TagResponseData(
    @SerializedName("seasonTags") val seasonTags : List<TagList>,
    @SerializedName("moodTags") val moodTags : List<TagList>,
    @SerializedName("eventTags") val eventTags : List<TagList>
)

data class TagList(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)