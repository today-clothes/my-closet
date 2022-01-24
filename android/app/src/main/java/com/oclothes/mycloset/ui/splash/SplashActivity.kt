package com.oclothes.mycloset.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.oclothes.mycloset.databinding.ActivitySplashBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.login.LoginActivity
import com.oclothes.mycloset.ui.main.MainActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) , SplashView {

    override fun initAfterBinding() {
    Handler(Looper.getMainLooper()).postDelayed({
        autoLogin()
    }, 3000)
}

    private fun autoLogin() {
//        AuthService.autoLogin(this)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onAutoLoginLoading() {

    }

    override fun onAutoLoginSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
//        startActivityWithClear(LoginActivity::class.java)
    }
}