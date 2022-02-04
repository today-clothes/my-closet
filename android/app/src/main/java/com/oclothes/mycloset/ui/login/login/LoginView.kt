package com.oclothes.mycloset.ui.login.login

import com.oclothes.mycloset.data.entities.remote.auth.UserDto


interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(jwt: String, userDto: UserDto)
    fun onLoginFailure(code: Int, message: String)
}
