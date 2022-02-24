package com.oclothes.mycloset.data.entities.remote.tag.dto

import com.google.gson.annotations.SerializedName
import com.oclothes.mycloset.data.entities.remote.domain.Tag

data class TagResponse(
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : TagResponseData
)

data class TagResponseData(
    @SerializedName("seasonTags") val seasonTags : List<Tag>,
    @SerializedName("moodTags") val moodTags : List<Tag>,
    @SerializedName("eventTags") val eventTags : List<Tag>
)

