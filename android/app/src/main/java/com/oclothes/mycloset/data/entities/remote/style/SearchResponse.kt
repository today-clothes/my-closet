package com.oclothes.mycloset.data.entities.remote.style

import com.google.gson.annotations.SerializedName
import com.oclothes.mycloset.data.entities.Tag

data class SearchResponse(
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : SearchData
)

data class SearchData(
    @SerializedName("contents") val contents : ArrayList<Cloth>,
    @SerializedName("contentsCount") val contentsCount : Int,
    @SerializedName("hasNext") val hasNext : Boolean,
    @SerializedName("isEmpty") val isEmpty: Boolean,
    @SerializedName("isFirst") val isFirst : Boolean,
    @SerializedName("isLast") val isLast : Boolean,
    @SerializedName("pageNumber") val pageNumber : Int
)

data class Cloth(
    @SerializedName("closetId") val closetId : Int,
    @SerializedName("clothesId") val clothesId : Int,
    @SerializedName("imgUrl") val imgUrl : String,
    @SerializedName("locked") val locked : Boolean,
    @SerializedName("eventTags") val eventTags : ArrayList<Tag>,
    @SerializedName("moodTags") val moodTags : ArrayList<Tag>,
    @SerializedName("seasonTags") val seasonTags : ArrayList<Tag>,
)