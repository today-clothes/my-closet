package com.oclothes.mycloset.ui.main.closet

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.data.entities.remote.tag.TagService
import com.oclothes.mycloset.databinding.FragmentSingleClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.info.TagView
import com.oclothes.mycloset.ui.main.closet.adapter.SingleClosetStyleListRVAdapter
import com.oclothes.mycloset.ui.main.closet.adapter.SingleClosetTagListRvAdapter
import java.util.concurrent.CopyOnWriteArrayList

class SingleClosetFragment(val f : MainFragment) : BaseFragment<FragmentSingleClosetBinding>(FragmentSingleClosetBinding::inflate) ,View.OnClickListener , TagView{

    lateinit var styleList: CopyOnWriteArrayList<Style>
    lateinit var eventTags : ArrayList<Tag>
    lateinit var moodTags : ArrayList<Tag>
    lateinit var seasonTags : ArrayList<Tag>
    lateinit var allTags : ArrayList<Tag>
    private lateinit var tagListAdapter : SingleClosetTagListRvAdapter
    private lateinit var clothListAdapter: SingleClosetStyleListRVAdapter
    private lateinit var myLayoutManager: GridLayoutManager

    override fun initAfterBinding() {
        initStyleList()
        initTags()
        binding.singleClosetBackBtnIv.setOnClickListener(this)
        binding.singleClosetScissorsIv.setOnClickListener(this)
        binding.singleClosetPlusIv.setOnClickListener(this)

        clothListAdapter = SingleClosetStyleListRVAdapter(this, styleList)
        clothListAdapter.setMyItemClickListener(object : SingleClosetStyleListRVAdapter.MyItemClickListener{
            override fun onItemClick(style: Style, position : Int) {
                f.getBinding().mainFragmentVp.currentItem = 2
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


        tagListAdapter = SingleClosetTagListRvAdapter(this, allTags)
        binding.singleClosetFilterListRv.adapter = tagListAdapter
        binding.singleClosetFilterListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.singleClosetFilterCountTv.text = "0"
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

            binding.singleClosetPlusIv ->{
                if(clothListAdapter.getEditMode()){
                    clothListAdapter.deleteSelectedItem()
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
        styleList = CopyOnWriteArrayList<Style>()
        for(i in 1..10) {
            styleList.add(Style("1일번"))
            styleList.add(Style("2이번"))

        }
    }

    fun setSingleCloset(closet : Closet){
        binding.singleClosetTitleTv.text = closet.name
        binding.singleClosetClothesCountTv.text = "내 옷장보기 " + styleList.size.toString()
    }

    private fun initTags(){
        TagService.getTags(this)
    }

    override fun onGetTagsSuccess(
        eventTags: java.util.ArrayList<Tag>,
        moodTags: java.util.ArrayList<Tag>,
        seasonTags: java.util.ArrayList<Tag>
    ) {
        this.eventTags = eventTags
        this.moodTags = moodTags
        this.seasonTags = seasonTags
        allTags = ArrayList<Tag>()
        allTags.addAll(eventTags)
        allTags.addAll(moodTags)
        allTags.addAll(seasonTags)
    }

    override fun onGetTagsFailure(code: Int, message: String) {
    }
}