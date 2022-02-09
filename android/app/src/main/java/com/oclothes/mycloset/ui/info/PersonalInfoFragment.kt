package com.oclothes.mycloset.ui.info

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.oclothes.mycloset.ApplicationClass.Companion.MAN
import com.oclothes.mycloset.ApplicationClass.Companion.WOMAN
import com.oclothes.mycloset.R
import com.oclothes.mycloset.databinding.FragmentPersonalInfoBinding
import com.oclothes.mycloset.ui.BaseFragment

class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>(FragmentPersonalInfoBinding::inflate), View.OnClickListener{
    private var gender = 0
    lateinit var myActivity : InfoSelectActivity
    override fun initAfterBinding() {
        initListener()
        myActivity = requireActivity() as InfoSelectActivity
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.personalInfoSelectionManTv -> {
                manButtonFun()
            }

            binding.personalInfoSelectionWomanTv -> {
                womanButtonFun()
            }

            binding.personalInfoBackgroundCl -> {
                hideKeyboard()
            }
            binding.personalInfoNextBtnTv -> {
                setInfo()
                moveNextPage()
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
        myActivity.nickname = binding.personalInfoEditTextNicknameEt.toString()
    }

    private fun moveNextPage() {
        myActivity.getBinding().infoContentVp.currentItem = 1
    }

    private fun initListener() {
        binding.personalInfoSelectionManTv.setOnClickListener(this)
        binding.personalInfoSelectionWomanTv.setOnClickListener(this)
        binding.personalInfoBackgroundCl.setOnClickListener(this)
        binding.personalInfoCl.setOnClickListener(this)
        binding.personalInfoNextBtnTv.setOnClickListener(this)
    }



    private fun deactivateNextButton() {
        binding.personalInfoNextBtnTv.setBackgroundResource(R.drawable.basic_theme_button_inactive)
        binding.personalInfoNextBtnTv.isClickable = false
    }

    private fun activateNextButton() {
        binding.personalInfoNextBtnTv.setBackgroundResource(R.drawable.basic_theme_button_active)
        binding.personalInfoNextBtnTv.isClickable = true
    }

    private fun checkButtonActive() : Boolean{
        return !(binding.personalInfoEditTextAgeEt.text.toString().isEmpty()
                || binding.personalInfoEditTextHeightEt.text.toString().isEmpty()
                || binding.personalInfoEditTextWeightEt.text.toString().isEmpty()
                || gender == 0)
    }

    private fun womanButtonFun() {
        if (gender != WOMAN) {
            binding.personalInfoSelectionWomanTv.setBackgroundResource(R.drawable.personal_info_gender_active)
            binding.personalInfoSelectionManTv.setBackgroundResource(R.drawable.personal_info_edit_text_background)
            gender = WOMAN
        } else {
            binding.personalInfoSelectionWomanTv.setBackgroundResource(R.drawable.personal_info_edit_text_background)
            gender = 0
        }
    }

    private fun manButtonFun() {
        if (gender != MAN) {
            binding.personalInfoSelectionManTv.setBackgroundResource(R.drawable.personal_info_gender_active)
            binding.personalInfoSelectionWomanTv.setBackgroundResource(R.drawable.personal_info_edit_text_background)
            gender = MAN
        } else {
            binding.personalInfoSelectionManTv.setBackgroundResource(R.drawable.personal_info_edit_text_background)
            gender = 0
        }
    }

    private fun hideKeyboard (){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
}