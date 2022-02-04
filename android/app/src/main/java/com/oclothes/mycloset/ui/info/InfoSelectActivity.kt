package com.oclothes.mycloset.ui.info

import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.oclothes.mycloset.databinding.ActivityInfoSelectBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.info.adapter.InfoSelectAdapter

class InfoSelectActivity : BaseActivity<ActivityInfoSelectBinding>(ActivityInfoSelectBinding::inflate) {

    private val tabInfo = arrayListOf("회원 정보", "스타일 정보")
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

    public fun getBinding() : ActivityInfoSelectBinding{
        return binding
    }
}