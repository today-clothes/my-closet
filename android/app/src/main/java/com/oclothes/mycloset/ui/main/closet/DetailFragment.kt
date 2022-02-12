package com.oclothes.mycloset.ui.main.closet

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.databinding.FragmentDetailBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.MainActivity

class DetailFragment(val f : MainFragment) : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    val editMode = false
    var isEditable = false
    var mainImage : Bitmap? = null
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

        binding.detailSecondApplyTv.setOnClickListener {
            onEditDiscard()
        }

        binding.detailSecondCancelTv.setOnClickListener {
            onEditDiscard()
        }

        binding.detailSecondEditBtnTv.setOnClickListener {
            onEditModeBegin()
        }

        if(!isEditable) {
            isEditable = true
            binding.detailMainImageIv.setImageBitmap(mainImage)
        }
    }

    fun getBinding() = binding

    fun setContents(style: Style) {

    }

    fun setImage(image: Bitmap) {
        if (isEditable) {
            binding.detailMainImageIv.setImageBitmap(mainImage)
        }else{
            mainImage = image
        }
    }

    fun onEditModeBegin() {
        binding.detailSecondApplyTv.visibility = View.VISIBLE
        binding.detailSecondCancelTv.visibility = View.VISIBLE
        binding.detailSecondEditBtnTv.visibility = View.GONE
    }

    fun onEditDiscard() {
        binding.detailSecondApplyTv.visibility = View.GONE
        binding.detailSecondCancelTv.visibility = View.GONE
        binding.detailSecondEditBtnTv.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    fun onEditConfirm() {

    }
}