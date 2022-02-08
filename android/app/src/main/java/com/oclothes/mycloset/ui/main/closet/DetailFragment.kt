package com.oclothes.mycloset.ui.main.closet

import com.oclothes.mycloset.databinding.FragmentDetailBinding
import com.oclothes.mycloset.ui.BaseFragment

class DetailFragment(val f : MainFragment) : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    override fun initAfterBinding() {
        binding.detailMainFrmSpl.anchorPoint = 0.5f
        binding.detailMainFrmSpl.isClipPanel = false
        binding.detailMainBackBtnIv.setOnClickListener{
            f.getBinding().mainFragmentVp.currentItem = 1
        }
    }


}