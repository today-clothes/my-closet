package com.oclothes.mycloset.ui.main.search

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.oclothes.mycloset.data.entities.remote.domain.StyleInfo
import com.oclothes.mycloset.data.entities.remote.domain.Tag
import com.oclothes.mycloset.data.entities.remote.style.view.StyleInfoView
import com.oclothes.mycloset.data.entities.remote.style.service.StyleService
import com.oclothes.mycloset.databinding.FragmentSearchDetailBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.search.adapter.SearchDetailTagListRvAdapter
import com.oclothes.mycloset.utils.getJwt

class SearchDetailFragment(val f : SearchMainFragment) : BaseFragment<FragmentSearchDetailBinding>(FragmentSearchDetailBinding::inflate) ,
    StyleInfoView {

    val tagListForAdapter = ArrayList<Tag>()
    val detailTagRvAdapter = SearchDetailTagListRvAdapter(this, tagListForAdapter)


    override fun initAfterBinding() {
        binding.searchDetailSecondTitleEditEt.isEnabled = false
        binding.searchDetailSecondInfoSearchDetailEditEt.isEnabled = false
        binding.singleClosetFilterListRv.adapter = detailTagRvAdapter
        binding.singleClosetFilterListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.searchDetailMainBackBtnIv.setOnClickListener {
            f.getBinding().mainSearchFragmentVp.currentItem = 0
        }


    }

    override fun onInfoSuccess(styleInfo: StyleInfo) {
        binding.searchDetailSecondTitleEditEt.setText(styleInfo.styleTitle)
        binding.searchDetailSecondInfoSearchDetailEditEt.setText(styleInfo.content)
        binding.searchDetailSecondInfoUserTv.text = "${styleInfo.updatedAt.substring(0,10)}/${styleInfo.userName} ${styleInfo.height}cm ${styleInfo.weight}kg"

        val glideUrl = GlideUrl("http://10.0.2.2:8080/clothes/images/${styleInfo.imgUrl}", LazyHeaders.Builder()
            .addHeader("Authorization", getJwt()!!)
            .build())
        Glide.with(this).load(glideUrl).into(binding.searchDetailMainImageIv)
        tagListForAdapter.clear()
        tagListForAdapter.addAll(styleInfo.eventTags)
        tagListForAdapter.addAll(styleInfo.moodTags)
        tagListForAdapter.addAll(styleInfo.seasonTags)
        binding.searchDetailSecondFilterCountTv.text = tagListForAdapter.size.toString()
        detailTagRvAdapter.notifyDataSetChanged()

    }

    override fun onInfoFailure() {
    }

    fun fromSearch(id : Int){
        StyleService.getClothInfo(this, id)
    }

}