package com.oclothes.mycloset.data.entities.remote.style.dto

import com.google.gson.annotations.SerializedName
import com.oclothes.mycloset.data.entities.remote.domain.Tag

data class RecommendResponse (
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : ArrayList<RecommendData>
)

data class RecommendData(
    @SerializedName("closetId") val closetId : Int,
    @SerializedName("clothesId") val clothesId : Int,
    @SerializedName("imgUrl") val imgUrl : String,
    @SerializedName("eventTags") val eventTags : ArrayList<Tag>,
    @SerializedName("locked") val locked : Boolean,
    @SerializedName("moodTags") val moodTags : ArrayList<Tag>,
    @SerializedName("seasonTags") val seasonTags : ArrayList<Tag>,
    @SerializedName("styleTitle") val styleTitle : String,
    @SerializedName("updatedAt") val updatedAt : String
)
