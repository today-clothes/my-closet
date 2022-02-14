package com.oclothes.mycloset.ui.main.search

import com.oclothes.mycloset.data.entities.StyleInfo
import com.oclothes.mycloset.data.entities.remote.style.StyleInfoView
import com.oclothes.mycloset.data.entities.remote.style.StyleService
import com.oclothes.mycloset.databinding.FragmentSearchDetailBinding
import com.oclothes.mycloset.ui.BaseFragment

class SearchDetailFragment(val f : MainSearchFragment) : BaseFragment<FragmentSearchDetailBinding>(FragmentSearchDetailBinding::inflate) , StyleInfoView{
    override fun initAfterBinding() {
        binding.searchDetailMainBackBtnIv.setOnClickListener {
            f.getBinding().mainSearchFragmentVp.currentItem = 0
        }
    }

    override fun onInfoSuccess(styleInfo: StyleInfo) {
    }

    override fun onInfoFailure() {
    }

    fun fromSearch(id : Int){
        StyleService.getClothInfo(this, id)
    }
//이거 나중에 써야함.
//    binding.detailSecondInfoUserTv.text = "${styleinfo.updateAt.substring(2,3)}.${styleinfo.updateAt.substring(5,6)}.${styleinfo.updateAt.substring(8,9)}/${styleinfo.userName} ${styleinfo.height}cm ${styleinfo.weight}kg"

}