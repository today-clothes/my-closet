package com.oclothes.mycloset.data.entities

data class User (
    val email: String,
    val password: String,
    val nickname: String,
    val gender : Boolean,
    val age : Int,
    val weight : Int,
    val height : Int,
)