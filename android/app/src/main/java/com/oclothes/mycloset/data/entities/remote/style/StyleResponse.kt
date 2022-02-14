package com.oclothes.mycloset.data.entities.remote.style

import com.google.gson.annotations.SerializedName
import com.oclothes.mycloset.data.entities.Tag

data class StyleResponse(
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : StyleData
)

data class StyleData(
    @SerializedName("contents") val contents : ArrayList<StyleContent>,
    @SerializedName("contentsCount") val contentsCount : Int,
    @SerializedName("hasNext") val hasNext : Boolean,
    @SerializedName("isEmpty") val isEmpty: Boolean,
    @SerializedName("isFirst") val isFirst : Boolean,
    @SerializedName("isLast") val isLast : Boolean,
    @SerializedName("pageNumber") val pageNumber : Int
)

data class StyleContent(
    @SerializedName("closetId") val closetId : Int,
    @SerializedName("clothesId") val clothesId : Int,
    @SerializedName("imgUrl") val imgUrl : String,
    @SerializedName("locked") val locked : Boolean,
    @SerializedName("eventTags") val eventTags : ArrayList<Tag>,
    @SerializedName("moodTags") val moodTags : ArrayList<Tag>,
    @SerializedName("seasonTags") val seasonTags : ArrayList<Tag>,
)
