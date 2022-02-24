package com.oclothes.mycloset.ui.signup

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.oclothes.mycloset.data.entities.remote.domain.Tag
import com.oclothes.mycloset.data.entities.remote.tag.service.TagService
import com.oclothes.mycloset.databinding.FragmentStyleInfoBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.signup.adapter.InfoStyleRvAdapter

class StyleInfoFragment : BaseFragment<FragmentStyleInfoBinding>(FragmentStyleInfoBinding::inflate), TagView{
    var tags = ArrayList<Tag>()
    lateinit var rvAdapter: InfoStyleRvAdapter
    lateinit var myActivity : SignUpActivity

    override fun initAfterBinding() {
        init()
        initRvAdapter()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.styleInfoNextBtnTv.setOnClickListener {
            for (selectedTag in rvAdapter.getSelectedTags()) {
                myActivity.tagList.add(selectedTag.id)
                Log.d("TAGTEST", selectedTag.id.toString())
            }
            myActivity.signUp()
        }
    }

    private fun initRvAdapter() {
        rvAdapter = InfoStyleRvAdapter(tags, this)
        binding.styleInfoTagListRv.adapter = rvAdapter
        val gridLayoutManager = GridLayoutManager(activity, 2)
        binding.styleInfoTagListRv.layoutManager = gridLayoutManager
    }

    private fun init(){
        binding.styleInfoNextBtnTv.isEnabled = false
        TagService.getTags(this)
        myActivity = requireActivity() as SignUpActivity
    }

    override fun onGetTagsSuccess(
        eventTags: java.util.ArrayList<Tag>,
        moodTags: java.util.ArrayList<Tag>,
        seasonTags: java.util.ArrayList<Tag>
    ) {
        tags.addAll(moodTags)
        rvAdapter.notifyDataSetChanged()
    }

    override fun onGetTagsFailure(code: Int, message: String) {
        showToast(message)
    }

    fun getBinding() : FragmentStyleInfoBinding = binding
}