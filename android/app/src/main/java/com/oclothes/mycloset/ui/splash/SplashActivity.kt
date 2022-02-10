package com.oclothes.mycloset.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.oclothes.mycloset.data.entities.remote.auth.AuthService
import com.oclothes.mycloset.databinding.ActivitySplashBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.login.EmailAuthActivity
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
        deleteJwt()
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
        when(code){
            401 ->{
                startActivity(Intent(this, EmailAuthActivity::class.java))
            }
        }
    }
}