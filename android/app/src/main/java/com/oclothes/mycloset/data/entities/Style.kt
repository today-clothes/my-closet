package com.oclothes.mycloset.data.entities


data class Style(
    val closetId : Int,
    val clothesId : Int,
    val imgUrl : String,
    val locked : Boolean,
    val content: String = "",
    val eventTags : ArrayList<Tag>,
    val moodTags : ArrayList<Tag>,
    val seasonTags : ArrayList<Tag>,
    var isSelected : Boolean = false
)
