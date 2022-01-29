package com.oclothes.mycloset.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.oclothes.mycloset.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.oclothes.mycloset.ApplicationClass.Companion.mSharedPreferences
import com.oclothes.mycloset.databinding.ActivitySplashBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.main.MainActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) , SplashView {

    override fun initAfterBinding() {
    Handler(Looper.getMainLooper()).postDelayed({
        autoLogin()
    }, 1000)
}

    private fun autoLogin() {
//        AuthService.autoLogin(this)
        tempLogin()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun tempLogin() {
        val editor = mSharedPreferences.edit()
        editor.putString(X_ACCESS_TOKEN, "1234")
        editor.putString("nickname", "허쟁")
        editor.apply()
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