package com.oclothes.mycloset.data.entities

import com.oclothes.mycloset.R

data class Closet(
    val id : Int,
    val name : String,
    val imageSource : Int = R.drawable.app_logo
)
