package com.oclothes.mycloset.ui.signup

import android.content.Intent
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.oclothes.mycloset.data.entities.remote.user.service.UserService
import com.oclothes.mycloset.data.entities.remote.user.dto.SignUpDto
import com.oclothes.mycloset.databinding.ActivityInfoSelectBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.signup.adapter.InfoSelectAdapter
import com.oclothes.mycloset.ui.login.EmailAuthActivity

class SignUpActivity : BaseActivity<ActivityInfoSelectBinding>(ActivityInfoSelectBinding::inflate), SignUpView {

    private val tabInfo = arrayListOf("회원가입","회원 정보", "스타일 정보")
    var email = ""
    var password = ""
    var nickname = ""
    var gender : Int = 0
    var age : Int = 0
    var height : Int = 0
    var weight : Int = 0
    var tagList = ArrayList<Int>()


    override fun initAfterBinding() {
        initViewPager()
    }

    private fun initViewPager() {
        val infoSelectAdapter = InfoSelectAdapter(this)
        binding.infoContentVp.isUserInputEnabled = false
        binding.infoContentVp.adapter = infoSelectAdapter
        TabLayoutMediator(binding.infoTb, binding.infoContentVp) { tab, position ->
            tab.text = tabInfo[position]
        }.attach()
        var touchableList: ArrayList<View>? = binding.infoTb?.touchables
        touchableList?.forEach {
            it.isEnabled = false
        }
    }

    fun getBinding() : ActivityInfoSelectBinding{
        return binding
    }

    fun signUp(){
        UserService.signUp(this, SignUpDto(email,password,nickname,gender,age,height,weight,tagList))
    }


    override fun onSignUpLoading() {
        binding.loadingPb.visibility = View.VISIBLE
    }

    override fun onSignUpSuccess() {
        binding.loadingPb.visibility = View.GONE
        startActivity(Intent(this, EmailAuthActivity::class.java))
    }

    override fun onSignUpFailure(code: Int, message: String) {
        binding.loadingPb.visibility = View.GONE
        showToast(message)
    }

}