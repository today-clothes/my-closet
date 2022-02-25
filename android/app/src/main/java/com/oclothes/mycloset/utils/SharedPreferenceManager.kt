package com.oclothes.mycloset.utils

import com.google.gson.Gson
import com.oclothes.mycloset.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.oclothes.mycloset.ApplicationClass.Companion.mSharedPreferences
import com.oclothes.mycloset.data.entities.remote.user.dto.UserDto
import com.oclothes.mycloset.data.entities.remote.domain.User

fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, jwtToken)

    editor.apply()
}

fun saveInt(key: String, body: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt(key, body)
    editor.apply()
}


fun getInt(key : String) : Int{
    return mSharedPreferences.getInt(key, -1)
}


fun saveString(key : String, body : String){
    val editor = mSharedPreferences.edit()
    editor.putString(key, body)
    editor.apply()
}

fun getString(key : String) : String?{
    return mSharedPreferences.getString(key, null)
}

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)

fun deleteJwt(){
    mSharedPreferences.edit().remove("jwt").apply()
}

fun getLogin(): UserDto? {
    return Gson().fromJson(mSharedPreferences.getString("userDto", null), UserDto::class.java)
}


fun saveLogin(userDto : UserDto){
    val editor = mSharedPreferences.edit()
    var jsonStr : String = Gson().toJson(userDto, UserDto::class.java)
    editor.putString("userDto", jsonStr)
    editor.apply()
}

fun saveUser(user: User) {
    val editor = mSharedPreferences.edit()
    user?.let{
        editor.putString("user", Gson().toJson(it, User::class.java))
    }
    editor.apply()
}

fun getUser(): User? {
    return Gson().fromJson(mSharedPreferences.getString("user", null), User::class.java)
}