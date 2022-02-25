package com.oclothes.mycloset.ui.signup

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.oclothes.mycloset.R
import com.oclothes.mycloset.databinding.FragmentSignUpEmailBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.utils.RegulationUtils

class SignUpEmailFragment : BaseFragment<FragmentSignUpEmailBinding>(FragmentSignUpEmailBinding::inflate), View.OnClickListener {
    private var emailFilled = false
    private var password1Filled = false
    private var password2Filled = false


    override fun initAfterBinding() {
        init()
    }

    private fun init() {
        setOnClickListeners()
        setTextWatchers()
    }

    private fun setTextWatchers() {
        val emailWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(e: Editable?) {
                emailFilled = e!!.isNotEmpty() && e!!.contains("@") && e!!.contains(".")
                checkLogic()
            }
        }

        val password1Watcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(e: Editable?) {
                password1Filled = e!!.isNotEmpty()
                checkLogic()
            }
        }

        val password2Watcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(e: Editable?) {
                password2Filled = e!!.isNotEmpty()
                checkLogic()
            }
        }

        binding.loginSignUpEditTextEmailEt.addTextChangedListener(emailWatcher)
        binding.loginSignUpEditTextPasswordEt.addTextChangedListener(password1Watcher)
        binding.loginSignUpEditTextPasswordConfirmEt.addTextChangedListener(password2Watcher)
    }

    private fun setOnClickListeners() {
        binding.loginSignUpSignUpBtnTv.setOnClickListener(this)
        binding.loginSignUpAgreeAllCb.setOnClickListener(this)
        binding.loginSignUpAgree1Cb.setOnClickListener(this)
        binding.loginSignUpAgree2Cb.setOnClickListener(this)
        binding.loginSignUpAgree3Cb.setOnClickListener(this)
        binding.loginSignUpSignUpBtnTv.isClickable = false
        binding.loginSignUpMainBackgroundCl.setOnClickListener(this)
        binding.loginSignUpReg1Tv.setOnClickListener(this)
        binding.loginSignUpReg2Tv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v){
            binding.loginSignUpAgreeAllCb -> {
                checkAll()
            }
            binding.loginSignUpSignUpBtnTv -> {
                moveToNext()
            }
            binding.loginSignUpMainBackgroundCl ->{
                hideKeyboard()
            }
            binding.loginSignUpReg1Tv->{
                RegulationUtils.showUsageRegulation(requireContext())
            }
            binding.loginSignUpReg2Tv->{
                RegulationUtils.showPersonalInfoRegulation(requireContext())

            }
        }
        checkLogic()
    }

    private fun moveToNext() {
        if (binding.loginSignUpEditTextPasswordEt.text.toString() != binding.loginSignUpEditTextPasswordConfirmEt.text.toString()) {
            showToast("입력된 비밀번호가 같지 않습니다.")
        } else {
            (requireContext() as SignUpActivity).email = binding.loginSignUpEditTextEmailEt.text.toString()
            (requireContext() as SignUpActivity).password = binding.loginSignUpEditTextPasswordEt.text.toString()
            (requireContext() as SignUpActivity).getBinding().infoContentVp.currentItem = 1
        }
    }

    private fun checkLogic() {
        binding.loginSignUpAgreeAllCb.isChecked = binding.loginSignUpAgree1Cb.isChecked && binding.loginSignUpAgree2Cb.isChecked && binding.loginSignUpAgree3Cb.isChecked

        if(binding.loginSignUpAgree1Cb.isChecked && binding.loginSignUpAgree2Cb.isChecked && emailFilled && password1Filled && password2Filled){
            binding.loginSignUpSignUpBtnTv.setBackgroundResource(R.drawable.basic_theme_button_active)
            binding.loginSignUpSignUpBtnTv.setTextColor(Color.WHITE)
            binding.loginSignUpSignUpBtnTv.isClickable = true
        }else{
            binding.loginSignUpSignUpBtnTv.setBackgroundResource(R.drawable.basic_theme_button_inactive)
            binding.loginSignUpSignUpBtnTv.setTextColor(Color.parseColor("#707070"))
            binding.loginSignUpSignUpBtnTv.isClickable = false
        }
    }

    private fun checkAll() {
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

    private fun hideKeyboard (){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

}