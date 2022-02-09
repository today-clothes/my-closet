package com.oclothes.mycloset.ui.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.data.entities.remote.tag.TagService
import com.oclothes.mycloset.databinding.ActivityLoginBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.info.InfoSelectActivity
import com.oclothes.mycloset.ui.info.TagView
import com.oclothes.mycloset.ui.login.login.LoginLoginActivity
import com.oclothes.mycloset.ui.login.signup.SignUpEmailActivity
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.utils.saveJwt
import java.util.ArrayList

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate){
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

        binding.loginNaverBtn.setOnClickListener{
            startActivity(Intent(this, InfoSelectActivity::class.java))
        }

        binding.loginKakaoBtn.setOnClickListener {

        }

    }

    /**
     * 해야할 것
     * 먼저 여기 테스트용으로 많이 해놔서 다 정리 해야함 (태그라던지 클릭 리스너 등)
     */
}