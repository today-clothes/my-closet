package com.oclothes.mycloset.ui.login.login

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.remote.user.service.UserService
import com.oclothes.mycloset.data.entities.remote.user.dto.UserDto
import com.oclothes.mycloset.databinding.ActivityLoginLoginBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.login.EmailAuthActivity
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.ui.signup.SignUpActivity
import com.oclothes.mycloset.utils.saveJwt
import com.oclothes.mycloset.utils.saveLogin

class LoginLoginActivity : BaseActivity<ActivityLoginLoginBinding>(ActivityLoginLoginBinding::inflate), LoginView, View.OnClickListener {

    private var emailFilled = false
    private var passwordFilled = false

    override fun initAfterBinding() {
        setOnClickListeners()
        setEditText()
    }

    private fun setOnClickListeners() {
        binding.loginLoginLoginBtnTv.setOnClickListener(this)
        binding.loginLoginSignUpTv.setOnClickListener(this)
        binding.loginLoginFindPasswordTv.setOnClickListener(this)
        binding.loginLoginLoginBtnTv.isClickable = false
        binding.loginLoginBackgroundCl.setOnClickListener(this)
    }

    private fun setEditText(){
        val emailWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(e: Editable?) {
                emailFilled = e!!.isNotEmpty()
                setNextButtonState()
            }
        }

        val passwordWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(e: Editable?) {
                passwordFilled = e!!.isNotEmpty()
                setNextButtonState()
            }
        }
        binding.loginLoginEditTextEmailEt.addTextChangedListener(emailWatcher)
        binding.loginLoginEditTextPasswordEt.addTextChangedListener(passwordWatcher)
    }

    private fun setNextButtonState(){
        if(emailFilled && passwordFilled){
            binding.loginLoginLoginBtnTv.isClickable = true
            binding.loginLoginLoginBtnTv.setBackgroundResource(R.drawable.basic_theme_button_active)
        }
        else{
            binding.loginLoginLoginBtnTv.isClickable = false
            binding.loginLoginLoginBtnTv.setBackgroundResource(R.drawable.basic_theme_button_inactive)
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.loginLoginLoginBtnTv -> login()
            binding.loginLoginFindPasswordTv -> showToast("찾을 수 없습니다.")
            binding.loginLoginSignUpTv -> startActivity(Intent(this, SignUpActivity::class.java))
            binding.loginLoginBackgroundCl -> hideKeyboard(v)
        }
    }

    private fun login() {
        UserService.login(this, UserDto(binding.loginLoginEditTextEmailEt.text.toString(), binding.loginLoginEditTextPasswordEt.text.toString()))
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
        when(code){
            401 ->{
                startActivity(Intent(this, EmailAuthActivity::class.java))
            }
            else ->{
                binding.loginLoginLoadingPb.visibility = View.GONE
                binding.loginLoginEditTextPasswordEt.setText("")
                showToast(message)
            }
        }
    }
}