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

//    {
//        "email" : "gjwodud312129@gmail.com",
//        "password" : "gj1109gj",
//        "gender": 0,
//        "moodTags": [3,5],
//        "nickname": "jadnddg",
//        "weight": 60,
//        "age": 30
//    }
//
//
}