package com.oclothes.mycloset.ui.main.closet

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.databinding.FragmentSingleClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.closet.adapter.SingleClosetStyleListRVAdapter
import com.oclothes.mycloset.ui.main.closet.adapter.SingleClosetTagListRvAdapter

class SingleClosetFragment(val f : MainFragment) : BaseFragment<FragmentSingleClosetBinding>(FragmentSingleClosetBinding::inflate) ,View.OnClickListener {

    lateinit var styleList: ArrayList<Style>
    lateinit var tags : ArrayList<Tag>
    private lateinit var tagListAdapter : SingleClosetTagListRvAdapter
    private lateinit var clothListAdapter: SingleClosetStyleListRVAdapter
    private lateinit var myLayoutManager: GridLayoutManager

    override fun initAfterBinding() {
        initStyleList()
        initTags()
        binding.singleClosetBackBtnIv.setOnClickListener(this)

        clothListAdapter = SingleClosetStyleListRVAdapter(this, styleList)
        clothListAdapter.setMyItemClickListener(object : SingleClosetStyleListRVAdapter.MyItemClickListener{
            override fun onItemClick(style: Style, position : Int) {
                // 이건 상세보기 페이지로 넘어가야하는데 아직은 미구현
            }

            override fun onRemoveStyle(position: Int) {
                //이것도 아직은 미구현. 롱클릭 이벤트 먼저 구현예정
            }

            override fun onItemLongClick(style: Style){

            }
        })
        myLayoutManager = GridLayoutManager(activity, 2)
        binding.singleClosetClothesListRv.layoutManager = myLayoutManager
        binding.singleClosetClothesListRv.adapter = clothListAdapter


        tagListAdapter = SingleClosetTagListRvAdapter(this, tags)
        binding.singleClosetFilterListRv.adapter = tagListAdapter
        binding.singleClosetFilterListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.singleClosetFilterCountTv.text = "0"
        binding.singleClosetScissorsIv.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v){
            binding.singleClosetBackBtnIv ->{
                f.getBinding().mainFragmentVp.currentItem = 0
                clothListAdapter.finishEditMode()
                binding.singleClosetClothesListRv.layoutManager?.scrollToPosition(0)
            }

            binding.singleClosetScissorsIv ->{
                if(clothListAdapter.initEditMode(-1)){
                    initEditMode()
                }
            }
        }
    }

    private fun initEditMode() {

    }

    fun updateList(selectedTag: HashMap<String, Tag>) {
        clothListAdapter.updateByTag(selectedTag)
    }

    fun getBinding(): FragmentSingleClosetBinding {
        return binding
    }

    private fun initStyleList(){
        styleList = ArrayList<Style>()
        for(i in 1..15) {
            styleList.add(Style())
        }
    }

    fun setSingleCloset(closet : Closet){
        binding.singleClosetTitleTv.text = closet.name
        binding.singleClosetClothesCountTv.text = "내 옷장보기 " + styleList.size.toString()
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