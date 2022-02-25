package com.oclothes.mycloset.ui.splash

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.oclothes.mycloset.data.entities.remote.user.service.UserService
import com.oclothes.mycloset.databinding.ActivitySplashBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.login.LoginActivity
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.utils.deleteJwt
import com.oclothes.mycloset.utils.saveJwt

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) , SplashView {

    override fun initAfterBinding() {
    Handler(Looper.getMainLooper()).postDelayed({
        autoLogin()
    }, 2000)
}

    private fun autoLogin() {
        UserService.autoLogin(this)
        finish()
    }

    override fun onAutoLoginLoading() {

    }

    override fun onAutoLoginSuccess(jwt : String) {
        Log.d("AUTOLOGIN","성공")
        saveJwt(jwt)
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
        Log.d("AUTOLOGIN","실패")
        deleteJwt()
        startActivityWithClear(LoginActivity::class.java)
    }
}