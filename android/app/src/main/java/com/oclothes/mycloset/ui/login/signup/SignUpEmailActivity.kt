package com.oclothes.mycloset.ui.login.signup

import android.graphics.Color
import android.view.View
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.User
import com.oclothes.mycloset.data.entities.remote.auth.AuthService
import com.oclothes.mycloset.data.entities.remote.auth.SignUpDto
import com.oclothes.mycloset.data.entities.remote.auth.UserDto
import com.oclothes.mycloset.databinding.ActivityEmailSignUpBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.main.MainActivity

class SignUpEmailActivity : BaseActivity<ActivityEmailSignUpBinding>(ActivityEmailSignUpBinding::inflate), SignUpView, View.OnClickListener {
    override fun initAfterBinding() {
        init()
    }

    private fun init() {
        binding.loginSignUpSignUpBtnTv.setOnClickListener(this)
        binding.loginSignUpAgreeAllCb.setOnClickListener(this)
        binding.loginSignUpAgree1Cb.setOnClickListener(this)
        binding.loginSignUpAgree2Cb.setOnClickListener(this)
        binding.loginSignUpAgree3Cb.setOnClickListener(this)
        binding.loginSignUpSignUpBtnTv.isClickable = false
        binding.loginSignUpMainBackgroundCl.setOnClickListener(this)
    }
    
    override fun onClick(v: View?) {
        when (v){
            binding.loginSignUpAgreeAllCb -> {
                checkLogic1()
            }
            binding.loginSignUpSignUpBtnTv -> {
                signUp()
            }
            binding.loginSignUpMainBackgroundCl ->{
                hideKeyboard(binding.loginSignUpMainBackgroundCl)
            }
        }
        checkLogic2()
    }

    private fun signUp() {
        if (binding.loginSignUpEditTextEmailEt.text.toString().isEmpty() &&
            binding.loginSignUpEditTextNickNameEt.text.toString().isEmpty() &&
            binding.loginSignUpEditTextPasswordConfirmEt.text.toString().isEmpty() &&
            binding.loginSignUpEditTextPasswordEt.text.toString().isEmpty()
        ) {
            showToast("입력되지 않은 칸이 있습니다.")
        } else if (binding.loginSignUpEditTextPasswordEt.text.toString() != binding.loginSignUpEditTextPasswordConfirmEt.text.toString()) {
            showToast("입력된 비밀번호가 같지 않습니다.")
        } else {
            AuthService.signUp(this, getSignUpDto())
        }
    }

    private fun getSignUpDto(): SignUpDto {
        val email : String = binding.loginSignUpEditTextEmailEt.text.toString()
        val pw = binding.loginSignUpEditTextPasswordEt.text.toString()
        val nickname : String = binding.loginSignUpEditTextNickNameEt.text.toString()
        return SignUpDto(email, pw)
    }

    private fun checkLogic2() {
        if (binding.loginSignUpAgree1Cb.isChecked && binding.loginSignUpAgree2Cb.isChecked && binding.loginSignUpAgree3Cb.isChecked) {
            binding.loginSignUpAgreeAllCb.isChecked = true
            binding.loginSignUpSignUpBtnTv.setBackgroundResource(R.drawable.basic_theme_button_active)
            binding.loginSignUpSignUpBtnTv.setTextColor(Color.WHITE)
            binding.loginSignUpSignUpBtnTv.isClickable = true
        }else {
            binding.loginSignUpAgreeAllCb.isChecked = false
            binding.loginSignUpSignUpBtnTv.setBackgroundResource(R.drawable.basic_theme_button_inactive)
            binding.loginSignUpSignUpBtnTv.setTextColor(Color.parseColor("#707070"))
            binding.loginSignUpSignUpBtnTv.isClickable = false
        }
    }

    private fun checkLogic1() {
        if (binding.loginSignUpAgreeAllCb.isChecked) {
            binding.loginSignUpAgree1Cb.isChecked = true
            binding.loginSignUpAgree2Cb.isChecked = true
            binding.loginSignUpAgree3Cb.isChecked = true
        } else {
            binding.loginSignUpAgree1Cb.isChecked = false
            binding.loginSignUpAgree2Cb.isChecked = false
            binding.loginSignUpAgree3Cb.isChecked = false
        }
    }

    override fun onSignUpLoading() {
        binding.loginSignUpLoadingPb.visibility = View.VISIBLE
    }

    override fun onSignUpSuccess() {
        binding.loginSignUpLoadingPb.visibility = View.GONE
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onSignUpFailure(code: Int, message: String) {
        binding.loginSignUpLoadingPb.visibility = View.GONE
        showToast(code.toString() + "그리고" + message)
    }

}