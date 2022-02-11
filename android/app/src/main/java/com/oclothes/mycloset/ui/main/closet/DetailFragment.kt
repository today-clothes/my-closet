package com.oclothes.mycloset.ui.main.closet

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.databinding.FragmentDetailBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.MainActivity

class DetailFragment(val f : MainFragment) : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    val editMode = false
    lateinit var mainImageView: ImageView
    override fun initAfterBinding() {
        binding.detailMainFrmSpl.anchorPoint = 0.5f
        binding.detailMainFrmSpl.isClipPanel = false
        binding.detailMainBackBtnIv.setOnClickListener {
            f.getBinding().mainFragmentVp.currentItem = 1
        }
        mainImageView = binding.detailMainImageIv
        mainImageView.setOnClickListener {
            (requireContext() as MainActivity).openGallery()
        }
    }

    fun getBinding() = binding

    fun setContents(style: Style) {

    }

    fun setImage(image : Bitmap){
        Glide.with(this)
            .load(image)
            .into(binding.detailMainImageIv)
    }

    fun onEditModeBegin() {
        binding.detailSecondApplyTv.visibility = View.VISIBLE
        binding.detailSecondCancelTv.visibility = View.VISIBLE
    }

    fun onEditDiscard() {
        binding.detailSecondApplyTv.visibility = View.GONE
        binding.detailSecondCancelTv.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    fun onEditConfirm() {

    }
}