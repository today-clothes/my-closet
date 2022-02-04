package com.oclothes.mycloset.ui.login.login

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.remote.auth.AuthService
import com.oclothes.mycloset.data.entities.remote.auth.UserDto
import com.oclothes.mycloset.databinding.ActivityLoginLoginBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.login.signup.SignUpEmailActivity
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.utils.saveJwt
import com.oclothes.mycloset.utils.saveLogin

class LoginLoginActivity : BaseActivity<ActivityLoginLoginBinding>(ActivityLoginLoginBinding::inflate), LoginView, View.OnClickListener {

    override fun initAfterBinding() {
        init()
    }

    private fun init() {
        binding.loginLoginLoginBtnTv.setOnClickListener(this)
        binding.loginLoginSignUpTv.setOnClickListener(this)
        binding.loginLoginFindPasswordTv.setOnClickListener(this)
        binding.loginLoginLoginBtnTv.isClickable = false
        binding.loginLoginBackgroundCl.setOnClickListener(this)
        setEditText()
    }

    private fun setEditText(){
        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(e: Editable?) {
                if(e!!.length >= 6){
                    binding.loginLoginLoginBtnTv.isClickable = true
                    binding.loginLoginLoginBtnTv.setBackgroundResource(R.drawable.basic_theme_button_active)
                }else{
                    binding.loginLoginLoginBtnTv.isClickable = false
                    binding.loginLoginLoginBtnTv.setBackgroundResource(R.drawable.basic_theme_button_inactive)
                }
            }
        }

        binding.loginLoginEditTextPasswordEt.addTextChangedListener(textWatcher)
    }


    override fun onClick(v: View?) {
        when(v){
            binding.loginLoginLoginBtnTv -> login()
            binding.loginLoginFindPasswordTv -> showToast("찾을 수 없습니다.")
            binding.loginLoginSignUpTv -> startActivity(Intent(this, SignUpEmailActivity::class.java))
            binding.loginLoginBackgroundCl -> hideKeyboard(v)
        }
    }

    private fun login() {
        when {
            binding.loginLoginEditTextEmailEt.text.toString().isEmpty() -> {
                showToast("이메일이 입력되지 않았습니다.")
            }
            else -> {
                val email = binding.loginLoginEditTextEmailEt.text.toString()
                val pw = binding.loginLoginEditTextPasswordEt.text.toString()
                val userDto = UserDto(email, pw)
                AuthService.login(this, userDto)
            }
        }
    }

    override fun onLoginLoading() {
        binding.loginLoginLoadingPb.visibility = View.VISIBLE
    }

    override fun onLoginSuccess(jwt: String, userDto: UserDto) {
        binding.loginLoginLoadingPb.visibility = View.GONE
        saveJwt(jwt)
        saveLogin(userDto)
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onLoginFailure(code: Int, message: String) {
        binding.loginLoginLoadingPb.visibility = View.GONE
        binding.loginLoginEditTextPasswordEt.setText("")
    }
}