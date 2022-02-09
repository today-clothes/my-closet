package com.oclothes.mycloset.utils

import com.google.gson.Gson
import com.oclothes.mycloset.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.oclothes.mycloset.ApplicationClass.Companion.mSharedPreferences
import com.oclothes.mycloset.data.entities.remote.auth.SignUpDto
import com.oclothes.mycloset.data.entities.remote.auth.UserDto

fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)

fun getLogin(): SignUpDto? {
    val gson = Gson()
    return gson.fromJson(mSharedPreferences.getString("userDto", null), SignUpDto::class.java)
}


fun saveLogin(userDto : UserDto){
    val editor = mSharedPreferences.edit()
    val gson = Gson()
    var jsonStr : String = gson.toJson(userDto, SignUpDto::class.java)
    editor.putString("userDto", jsonStr)
    editor.apply()
}