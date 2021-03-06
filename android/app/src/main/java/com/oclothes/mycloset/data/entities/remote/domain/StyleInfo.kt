package com.oclothes.mycloset.data.entities.remote.domain


data class StyleInfo(
    val content : String,
    val height : Int,
    val styleTitle: String? = "임시이름",
    val updatedAt : String,
    val userName : String,
    val weight : Int,
    val eventTags : ArrayList<Tag>,
    val moodTags : ArrayList<Tag>,
    val seasonTags : ArrayList<Tag>,
    val imgUrl : String,
    val locked : Boolean
)
