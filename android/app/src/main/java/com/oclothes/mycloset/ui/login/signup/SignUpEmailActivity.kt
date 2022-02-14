package com.oclothes.mycloset.ui.login.signup

import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.remote.auth.AuthService
import com.oclothes.mycloset.data.entities.remote.auth.SignUpDto
import com.oclothes.mycloset.databinding.ActivityEmailSignUpBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.info.InfoSelectActivity
import com.oclothes.mycloset.ui.login.LoginActivity

class SignUpEmailActivity : BaseActivity<ActivityEmailSignUpBinding>(ActivityEmailSignUpBinding::inflate), View.OnClickListener {
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
            binding.loginSignUpEditTextPasswordConfirmEt.text.toString().isEmpty() &&
            binding.loginSignUpEditTextPasswordEt.text.toString().isEmpty()
        ) {
            showToast("입력되지 않은 칸이 있습니다.")
        } else if (binding.loginSignUpEditTextPasswordEt.text.toString() != binding.loginSignUpEditTextPasswordConfirmEt.text.toString()) {
            showToast("입력된 비밀번호가 같지 않습니다.")
        } else {
            val intent = Intent(this, InfoSelectActivity::class.java)
            intent.putExtra("email", binding.loginSignUpEditTextEmailEt.text.toString())
            intent.putExtra("pw", binding.loginSignUpEditTextPasswordEt.text.toString())
            startActivity(intent)
        }
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

}