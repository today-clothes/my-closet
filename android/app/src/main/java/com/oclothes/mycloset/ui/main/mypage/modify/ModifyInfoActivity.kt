package com.oclothes.mycloset.ui.main.mypage.modify

import android.util.Log
import com.oclothes.mycloset.data.entities.remote.domain.User
import com.oclothes.mycloset.data.entities.remote.user.dto.AccountDto
import com.oclothes.mycloset.data.entities.remote.user.dto.PersonalDto
import com.oclothes.mycloset.data.entities.remote.user.service.UserService
import com.oclothes.mycloset.data.entities.remote.user.view.AccountUpdateView
import com.oclothes.mycloset.data.entities.remote.user.view.PersonalUpdateView
import com.oclothes.mycloset.data.entities.remote.user.view.UserInfoView
import com.oclothes.mycloset.databinding.ActivityModifyInfoBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.utils.getUser
import com.oclothes.mycloset.utils.saveUser

class ModifyInfoActivity : BaseActivity<ActivityModifyInfoBinding>(ActivityModifyInfoBinding::inflate), PersonalUpdateView, AccountUpdateView,
    UserInfoView {
    override fun initAfterBinding() {
        initBtn()

        when (intent.getIntExtra("mode", 3)){
            1->{
                setAccountInfoModifyMode()
            }

            2->{
                setPersonalInfoModifyMode()
            }

            3->{
                finish()
            }
        }
    }

    private fun initBtn() {
        binding.modifyAccountBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun setAccountInfoModifyMode() {
        val user = getUser()!!
        binding.modifyFirstTv.text = "닉네임 (현재 '${user.nickname}')"
        binding.modifySecondTv.text = "나이 (현재 '${user.age.toString()}')"

        Log.d("MYPAGE_APPDEBUG", "어카운트작동")
        binding.modifyAccountConfirmBtnTv.setOnClickListener {
            var first = binding.modifyFirstEt.text.toString()
            var second = binding.modifySecondEt.text.toString()
            var age = user.age
            if(first.isEmpty()){
                first = user.nickname
            }
            if(second.isNotEmpty()){
                age = binding.modifySecondEt.text.toString().toInt()
            }
            UserService.updateAccount(this, AccountDto(nickname = first, age = age, gender = user.gender))
        }
    }

    private fun setPersonalInfoModifyMode() {
        val user = getUser()!!
        binding.modifyFirstTv.text = "키 (현재 '${user.height.toString()}')"
        binding.modifySecondTv.text = "몸무게 (현재 '${user.weight.toString()}')"
        Log.d("MYPAGE-APPDEBUG", "퍼스널 작동")

        binding.modifyAccountConfirmBtnTv.setOnClickListener {
            var first = binding.modifyFirstEt.text.toString()
            var second = binding.modifySecondEt.text.toString()
            var height = user.height
            var weight = user.weight
            if(first.isNotEmpty()){
                height = first.toInt()
            }
            if(second.isNotEmpty()){
                weight = second.toInt()
            }
            UserService.updatePersonal(this, PersonalDto(height, weight))
        }
    }

    override fun onAccountUpdateSuccess() {
        showToast("업데이트 완료")
        UserService.getUserInfo(this)
    }

    override fun onAccountUpdateFailure() {
        showToast("업데이트 실패")

    }

    override fun onPersonalUpdateSuccess() {
        showToast("업데이트 완료")
        UserService.getUserInfo(this)
    }

    override fun onPersonalUpdateFailure() {
        showToast("업데이트 실패")

    }

    override fun onGetUserInfoSuccess(user: User) {
        saveUser(user)
        finish()
    }

    override fun onGetUserInfoFailure(message: String) {
        showToast("업데이트 실패")
    }


}