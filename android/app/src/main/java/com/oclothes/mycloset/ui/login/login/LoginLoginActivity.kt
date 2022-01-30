package com.oclothes.mycloset.ui.login.login

import android.view.View
import com.oclothes.mycloset.data.entities.User
import com.oclothes.mycloset.data.entities.remote.auth.Auth
import com.oclothes.mycloset.data.entities.remote.auth.AuthService
import com.oclothes.mycloset.databinding.ActivityLoginLoginBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.login.signup.SignUpEmailActivity
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.utils.saveJwt

class LoginLoginActivity : BaseActivity<ActivityLoginLoginBinding>(ActivityLoginLoginBinding::inflate), LoginView, View.OnClickListener {
    override fun initAfterBinding() {
        init()
    }

    private fun init() {
        binding.loginLoginLoginBtnTv.setOnClickListener(this)
        binding.loginLoginSignUpTv.setOnClickListener(this)
        binding.loginLoginFindPasswordTv.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v){
            binding.loginLoginLoginBtnTv -> login()
            binding.loginLoginFindPasswordTv -> showToast("찾을 수 없습니다.")
            binding.loginLoginSignUpTv -> startActivityWithClear(SignUpEmailActivity::class.java)
        }
    }

    private fun login() {
        when {
            binding.loginLoginEditTextEmailEt.text.toString().isEmpty() -> {
                showToast("이메일이 입력되지 않았습니다.")
            }
            binding.loginLoginEditTextPasswordEt.text.toString().isEmpty() -> {
                showToast("비밀번호가 입력되지 않았습니다.")
            }
            else -> {
                val email = binding.loginLoginEditTextEmailEt.text.toString()
                val pw = binding.loginLoginEditTextPasswordEt.text.toString()
                val user = User(email, pw, "")
                AuthService.login(this, user)
            }
        }
    }

    override fun onLoginLoading() {
        binding.loginLoginLoadingPb.visibility = View.VISIBLE
    }

    override fun onLoginSuccess(auth: Auth) {
        binding.loginLoginLoadingPb.visibility = View.GONE
        saveJwt(auth.jwt)
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onLoginFailure(code: Int, message: String) {
        binding.loginLoginLoadingPb.visibility = View.GONE
        binding.loginLoginEditTextPasswordEt.setText("")
    }
}