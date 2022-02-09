package com.oclothes.mycloset.ui.info

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.data.entities.remote.tag.TagService
import com.oclothes.mycloset.databinding.FragmentStyleInfoBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.info.adapter.InfoStyleRvAdapter

class StyleInfoFragment : BaseFragment<FragmentStyleInfoBinding>(FragmentStyleInfoBinding::inflate), TagView{
    lateinit var tags : ArrayList<Tag>
    lateinit var rvAdapter: InfoStyleRvAdapter

    override fun initAfterBinding() {
        initTags()
        initRvAdapter()
    }

    private fun initRvAdapter() {
        rvAdapter = InfoStyleRvAdapter(tags)

        binding.styleInfoNextBtnTv.setOnClickListener {
            rvAdapter.getSelectedTags()
        }
        binding.styleInfoTagListRv.adapter = rvAdapter

        val gridLayoutManager = GridLayoutManager(activity, 2)
        binding.styleInfoTagListRv.layoutManager = gridLayoutManager
    }

    private fun initTags(){
        TagService.getTags(this)
    }

    override fun onGetTagsSuccess(
        eventTags: java.util.ArrayList<Tag>,
        moodTags: java.util.ArrayList<Tag>,
        seasonTags: java.util.ArrayList<Tag>
    ) {
        tags = moodTags
    }

    override fun onGetClosetsFailure(code: Int, message: String) {

    }
}