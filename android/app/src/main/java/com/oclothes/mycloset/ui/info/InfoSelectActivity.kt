package com.oclothes.mycloset.ui.info

import android.provider.ContactsContract
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.oclothes.mycloset.data.entities.remote.auth.AuthService
import com.oclothes.mycloset.data.entities.remote.auth.SignUpDto
import com.oclothes.mycloset.databinding.ActivityInfoSelectBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.info.adapter.InfoSelectAdapter
import com.oclothes.mycloset.ui.login.LoginActivity

class InfoSelectActivity : BaseActivity<ActivityInfoSelectBinding>(ActivityInfoSelectBinding::inflate), SignUpView {

    private val tabInfo = arrayListOf("회원 정보", "스타일 정보")
    lateinit var singUpDto: SignUpDto
    lateinit var email : String
    lateinit var password: String
    lateinit var nickname : String
    var gender : Int = 0
    var age : Int = 0
    var height : Int = 0
    var weight : Int = 0
    lateinit var tagList : ArrayList<Int>


    override fun initAfterBinding() {
        val infoSelectAdapter = InfoSelectAdapter(this)
        binding.infoContentVp.isUserInputEnabled = false
        binding.infoContentVp.adapter = infoSelectAdapter
        TabLayoutMediator(binding.infoTb, binding.infoContentVp) { tab, position ->
            tab.text = tabInfo[position]
        }.attach()

        var touchableList : ArrayList<View>? = ArrayList()

        touchableList = binding.infoTb?.touchables
        touchableList?.forEach{
            it.isEnabled = false
        }




    }

    fun getBinding() : ActivityInfoSelectBinding{
        return binding
    }

    public fun signUp(){
        AuthService.signUp(this, singUpDto)
    }


    override fun onSignUpLoading() {
        binding.loadingPb.visibility = View.VISIBLE
    }

    override fun onSignUpSuccess() {
        binding.loadingPb.visibility = View.GONE
        startActivityWithClear(LoginActivity::class.java)
    }

    override fun onSignUpFailure(code: Int, message: String) {
        binding.loadingPb.visibility = View.GONE
        showToast(code.toString() + "그리고" + message)
    }

}