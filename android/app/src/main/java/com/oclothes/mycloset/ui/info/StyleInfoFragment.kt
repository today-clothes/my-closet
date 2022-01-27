package com.oclothes.mycloset.ui.info

import androidx.recyclerview.widget.GridLayoutManager
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.databinding.FragmentStyleInfoBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.info.adapter.InfoStyleRvAdapter

class StyleInfoFragment : BaseFragment<FragmentStyleInfoBinding>(FragmentStyleInfoBinding::inflate){
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
        tags = ArrayList<Tag>()
        tags.add(Tag("봄", "계절"))
        tags.add(Tag("여름", "계절"))
        tags.add(Tag("가을", "계절"))
        tags.add(Tag("겨울", "계절"))
        tags.add(Tag("스트릿", "계절"))
        tags.add(Tag("미니멀", "계절"))
        tags.add(Tag("포멀", "계절"))
        tags.add(Tag("인포멀", "계절"))
        tags.add(Tag("컬러풀", "계절"))
        tags.add(Tag("모던", "계절"))
    }
}