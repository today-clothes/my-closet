package com.oclothes.mycloset.ui.main.closet

import android.view.View
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.databinding.FragmentDetailBinding
import com.oclothes.mycloset.ui.BaseFragment

class DetailFragment(val f : MainFragment) : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    val editMode = false

    override fun initAfterBinding() {
        binding.detailMainFrmSpl.anchorPoint = 0.5f
        binding.detailMainFrmSpl.isClipPanel = false
        binding.detailMainBackBtnIv.setOnClickListener{
            f.getBinding().mainFragmentVp.currentItem = 1
        }
    }

    fun setContents(style : Style){

    }

    fun onEditModeBegin(){
        binding.detailSecondApplyTv.visibility = View.VISIBLE
        binding.detailSecondCancelTv.visibility = View.VISIBLE
    }

    fun onEditDiscard(){
        binding.detailSecondApplyTv.visibility = View.GONE
        binding.detailSecondCancelTv.visibility = View.GONE
    }

    fun onEditConfirm(){

    }

//        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
//        ) { result : ActivityResult ->
//            binding.detailMainImageIv.setImageURI(result.data?.data)
//        }
//
//        binding.detailMainImageIv.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK);
//            intent.type = MediaStore.Images.Media.CONTENT_TYPE
//            intent.type = "image/*"
//            getContent.launch(intent)
//        }
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