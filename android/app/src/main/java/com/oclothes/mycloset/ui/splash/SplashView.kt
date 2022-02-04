package com.oclothes.mycloset.ui.splash

interface SplashView {
    fun onAutoLoginLoading()
    fun onAutoLoginSuccess(jwt : String)
    fun onAutoLoginFailure(code: Int, message: String)
}
