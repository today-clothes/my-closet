package com.oclothes.mycloset.data.entities

import com.oclothes.mycloset.R

data class Closet(
    val name : String = "tempCloset",
    val lock : Boolean = false,
    val status : Boolean = false,
    val imageSource : Int = R.drawable.temp_selfee
)
