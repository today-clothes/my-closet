package com.oclothes.mycloset.ui.main.closet

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.databinding.FragmentSingleClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.closet.adapter.SingleClosetStyleListRVAdapter
import com.oclothes.mycloset.ui.main.closet.adapter.SingleClosetTagListRvAdapter

class SingleClosetFragment : BaseFragment<FragmentSingleClosetBinding>(FragmentSingleClosetBinding::inflate) ,View.OnClickListener {

    lateinit var styleList: ArrayList<Style>
    lateinit var tags : ArrayList<Tag>


    override fun initAfterBinding() {
        initStyleList()
        initTags()
        val clothListAdapter = SingleClosetStyleListRVAdapter(styleList)
        val myLayoutManager = GridLayoutManager(activity, 2)
        binding.singleClosetClothesListRv.layoutManager = myLayoutManager
        binding.singleClosetClothesListRv.adapter = clothListAdapter


        val tagListAdapter = SingleClosetTagListRvAdapter(tags)
        binding.singleClosetFilterListRv.adapter = tagListAdapter
        binding.singleClosetFilterListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onClick(v: View?) {

    }
    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SingleClosetFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    private fun initStyleList(){
        styleList = ArrayList<Style>()
        for(i in 1..15) {
            styleList.add(Style())
        }
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