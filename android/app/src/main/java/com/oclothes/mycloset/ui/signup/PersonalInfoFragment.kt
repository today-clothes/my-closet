package com.oclothes.mycloset.ui.signup

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.oclothes.mycloset.ApplicationClass.Companion.MAN
import com.oclothes.mycloset.ApplicationClass.Companion.WOMAN
import com.oclothes.mycloset.R
import com.oclothes.mycloset.databinding.FragmentPersonalInfoBinding
import com.oclothes.mycloset.ui.BaseFragment

class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>(FragmentPersonalInfoBinding::inflate), View.OnClickListener{
    private var gender = 0
    lateinit var myActivity : SignUpActivity
    private var ageFilled = false
    private var heightFilled = false
    private var weightFilled = false
    private var nicknameFilled = false

    override fun initAfterBinding() {
        initListener()
        setTextWatcher()
        myActivity = requireActivity() as SignUpActivity
        binding.personalInfoSelectionManTv.performClick()
        (activity as SignUpActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun setTextWatcher() {
        val ageWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(e: Editable?) {
                ageFilled = e!!.isNotEmpty()
                setNextButtonState()
            }
        }

        val heightWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(e: Editable?) {
                heightFilled = e!!.isNotEmpty()
                setNextButtonState()
            }
        }

        val weightWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(e: Editable?) {
                weightFilled = e!!.isNotEmpty()
                setNextButtonState()
            }
        }

        val nicknameWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(e: Editable?) {
                nicknameFilled = e!!.isNotEmpty()
                setNextButtonState()
            }
        }
        binding.personalInfoEditTextAgeEt.addTextChangedListener(ageWatcher)
        binding.personalInfoEditTextHeightEt.addTextChangedListener(heightWatcher)
        binding.personalInfoEditTextWeightEt.addTextChangedListener(weightWatcher)
        binding.personalInfoEditTextNicknameEt.addTextChangedListener(nicknameWatcher)
    }

    private fun setNextButtonState(){
        if(ageFilled && heightFilled && weightFilled && nicknameFilled && gender != 0){
            binding.personalInfoNextBtnTv.setBackgroundResource(R.drawable.basic_theme_button_active)
            binding.personalInfoNextBtnTv.isClickable = true
        }
        else{
            binding.personalInfoNextBtnTv.setBackgroundResource(R.drawable.basic_theme_button_inactive)
            binding.personalInfoNextBtnTv.isClickable = false
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.personalInfoSelectionManTv -> {
                manButtonFun()
                setNextButtonState()
            }

            binding.personalInfoSelectionWomanTv -> {
                womanButtonFun()
                setNextButtonState()
            }

            binding.personalInfoBackgroundCl -> {
                hideKeyboard()
            }
            binding.personalInfoNextBtnTv -> {
                setInfo()
                hideKeyboard()
                myActivity.getBinding().infoContentVp.currentItem = 2

            }
            binding.personalInfoCl -> {
                hideKeyboard()
            }
        }
    }

    private fun setInfo() {
        myActivity.gender = gender
        myActivity.age = binding.personalInfoEditTextAgeEt.text.toString().toInt()
        myActivity.height = binding.personalInfoEditTextHeightEt.text.toString().toInt()
        myActivity.weight = binding.personalInfoEditTextWeightEt.text.toString().toInt()
        myActivity.nickname = binding.personalInfoEditTextNicknameEt.text.toString()
    }

    private fun initListener() {
        binding.personalInfoSelectionManTv.setOnClickListener(this)
        binding.personalInfoSelectionWomanTv.setOnClickListener(this)
        binding.personalInfoBackgroundCl.setOnClickListener(this)
        binding.personalInfoCl.setOnClickListener(this)
        binding.personalInfoNextBtnTv.setOnClickListener(this)
    }

    private fun womanButtonFun() {
        gender = if (gender != WOMAN) {
            binding.personalInfoSelectionWomanTv.setBackgroundResource(R.drawable.personal_info_gender_active)
            binding.personalInfoSelectionManTv.setBackgroundResource(R.drawable.personal_info_edit_text_background)
            WOMAN
        } else {
            binding.personalInfoSelectionWomanTv.setBackgroundResource(R.drawable.personal_info_edit_text_background)
            0
        }
    }

    private fun manButtonFun() {
        gender = if (gender != MAN) {
            binding.personalInfoSelectionManTv.setBackgroundResource(R.drawable.personal_info_gender_active)
            binding.personalInfoSelectionWomanTv.setBackgroundResource(R.drawable.personal_info_edit_text_background)
            MAN
        } else {
            binding.personalInfoSelectionManTv.setBackgroundResource(R.drawable.personal_info_edit_text_background)
            0
        }
    }

    private fun hideKeyboard (){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
}