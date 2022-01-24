package com.oclothes.mycloset.ui.login

import android.content.Intent
import com.oclothes.mycloset.databinding.ActivityLoginBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.login.login.LoginLoginActivity
import com.oclothes.mycloset.ui.login.signup.SignUpEmailActivity
import com.oclothes.mycloset.ui.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun initAfterBinding() {

        binding.loginLoginBtn.setOnClickListener {
            startActivity(Intent(this, LoginLoginActivity::class.java))
        }

        binding.loginGoogleBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.loginEmailBtn.setOnClickListener {
            startActivity(Intent(this, SignUpEmailActivity::class.java))
        }
    }
}