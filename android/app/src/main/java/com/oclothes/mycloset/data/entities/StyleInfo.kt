package com.oclothes.mycloset.data.entities


data class StyleInfo(
    val content : String,
    val height : Int,
    val styleTitle: String,
    val updateAt : String,
    val userName : String,
    val weight : Int,
    val eventTags : ArrayList<Tag>,
    val moodTags : ArrayList<Tag>,
    val seasonTags : ArrayList<Tag>
)
