package com.oclothes.mycloset.ui.login

import android.content.Intent
import com.oclothes.mycloset.databinding.ActivityEmailAuthBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.login.login.LoginLoginActivity

class EmailAuthActivity : BaseActivity<ActivityEmailAuthBinding>(ActivityEmailAuthBinding::inflate) {
    override fun initAfterBinding() {
        binding.emailAuthLoginBtnTv.setOnClickListener {
            startActivity(Intent(this, LoginLoginActivity::class.java))
        }
    }
}