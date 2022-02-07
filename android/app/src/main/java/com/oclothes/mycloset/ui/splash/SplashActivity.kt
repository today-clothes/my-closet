package com.oclothes.mycloset.ui.splash

import android.os.Handler
import android.os.Looper
import com.oclothes.mycloset.data.entities.remote.auth.AuthService
import com.oclothes.mycloset.databinding.ActivitySplashBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.login.LoginActivity
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.utils.saveJwt

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) , SplashView {

    override fun initAfterBinding() {
    Handler(Looper.getMainLooper()).postDelayed({
        autoLogin()
    }, 2000)
}

    private fun autoLogin() {

        AuthService.autoLogin(this)
        finish()
    }

    override fun onAutoLoginLoading() {

    }

    override fun onAutoLoginSuccess(jwt : String) {
        saveJwt(jwt)
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
        startActivityWithClear(LoginActivity::class.java)
        if(code == 1000){
            showToast("다시 로그인 해주세요.")
        }
    }
}