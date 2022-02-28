package com.oclothes.mycloset.ui.login

import android.content.Intent
import com.oclothes.mycloset.databinding.ActivityLoginBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.login.login.LoginLoginActivity
import com.oclothes.mycloset.ui.signup.SignUpActivity
import com.oclothes.mycloset.ui.signup.SignUpEmailFragment

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate){
    override fun initAfterBinding() {

        binding.loginLoginBtn.setOnClickListener {
            startActivity(Intent(this, LoginLoginActivity::class.java))
        }

        binding.loginGoogleBtn.setOnClickListener{
        }

        binding.loginEmailBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginNaverBtn.setOnClickListener{
        }

        binding.loginKakaoBtn.setOnClickListener {

        }
    }
}