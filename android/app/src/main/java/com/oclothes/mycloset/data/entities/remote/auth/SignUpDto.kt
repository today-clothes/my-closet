package com.oclothes.mycloset.data.entities.remote.auth

import com.google.gson.annotations.SerializedName

data class SignUpDto (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("gender") val gender : Int,
    @SerializedName("age") val age : Int,
    @SerializedName("height") val height : Int,
    @SerializedName("weight") val weight : Int,
    @SerializedName("tagList") val tagList : TagList
)

data class TagList(
    @SerializedName("tag") val tag : ArrayList<Tag>
)

data class Tag(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)