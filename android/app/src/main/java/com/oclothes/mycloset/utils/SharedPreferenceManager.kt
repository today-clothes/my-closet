package com.oclothes.mycloset.utils

import com.oclothes.mycloset.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.oclothes.mycloset.ApplicationClass.Companion.mSharedPreferences

fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)
