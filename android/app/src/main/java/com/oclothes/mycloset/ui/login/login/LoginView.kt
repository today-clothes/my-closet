package com.oclothes.mycloset.ui.login.login


interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(jwt: String)
    fun onLoginFailure(code: Int, message: String)
}
