package com.oclothes.mycloset.ui.info

import com.google.android.material.tabs.TabLayoutMediator
import com.oclothes.mycloset.databinding.ActivityInfoSelectBinding
import com.oclothes.mycloset.ui.BaseActivity
import com.oclothes.mycloset.ui.info.adapter.InfoSelectAdapter

class InfoSelectActivity : BaseActivity<ActivityInfoSelectBinding>(ActivityInfoSelectBinding::inflate) {

    private val tabInfo = arrayListOf("회원 정보", "스타일 정보")
    override fun initAfterBinding() {
        val infoSelectAdapter: InfoSelectAdapter = InfoSelectAdapter(this)
        binding.infoContentVp.adapter = infoSelectAdapter
        TabLayoutMediator(binding.infoTb, binding.infoContentVp) { tab, position ->
            tab.text = tabInfo[position]
        }.attach()

    }
}