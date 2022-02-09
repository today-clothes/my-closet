package com.oclothes.mycloset.data.entities

import com.oclothes.mycloset.R

data class Style(
    val name : String = "예시 옷",
    val category : String = "예시 스타일",
    val imageSource : Int = R.drawable.temp_selfee,
    val tagList : ArrayList<Tag> = arrayListOf<Tag>(Tag(0, "임시태그")),
    var isSelected : Boolean = false
)
