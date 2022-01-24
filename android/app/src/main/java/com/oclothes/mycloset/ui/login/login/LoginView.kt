package com.oclothes.mycloset.ui.login.login

import com.oclothes.mycloset.data.entities.remote.auth.Auth

interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(auth: Auth)
    fun onLoginSuccess()
    fun onLoginFailure(code: Int, message: String)
}
