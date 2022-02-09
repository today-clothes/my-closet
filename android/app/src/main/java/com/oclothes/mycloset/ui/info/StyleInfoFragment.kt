package com.oclothes.mycloset.ui.info

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.data.entities.remote.tag.TagService
import com.oclothes.mycloset.databinding.FragmentStyleInfoBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.info.adapter.InfoStyleRvAdapter

class StyleInfoFragment : BaseFragment<FragmentStyleInfoBinding>(FragmentStyleInfoBinding::inflate), TagView{
    private lateinit var tags : ArrayList<Tag>
    lateinit var rvAdapter: InfoStyleRvAdapter
    lateinit var myActivity : InfoSelectActivity

    override fun initAfterBinding() {
        init()
        initRvAdapter()
        binding.styleInfoNextBtnTv.setOnClickListener {
            for (selectedTag in rvAdapter.getSelectedTags()) {
                myActivity.tagList.add(selectedTag.id)
                Log.d("TAGTEST", selectedTag.id.toString())
            }
            myActivity.signUp()
        }
    }

    private fun initRvAdapter() {

        binding.styleInfoTagListRv.adapter = rvAdapter

        val gridLayoutManager = GridLayoutManager(activity, 2)
        binding.styleInfoTagListRv.layoutManager = gridLayoutManager
    }

    private fun init(){
        tags = ArrayList<Tag>()
        rvAdapter = InfoStyleRvAdapter(tags, this)
        TagService.getTags(this)
        binding.styleInfoNextBtnTv.isEnabled = false
        myActivity = requireActivity() as InfoSelectActivity

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
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getBinding() : FragmentStyleInfoBinding = binding
}