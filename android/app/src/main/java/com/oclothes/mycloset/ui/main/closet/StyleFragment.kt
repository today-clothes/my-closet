package com.oclothes.mycloset.ui.main.closet

import android.app.AlertDialog
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.remote.closet.service.ClosetService
import com.oclothes.mycloset.data.entities.remote.domain.Closet
import com.oclothes.mycloset.data.entities.remote.domain.Status
import com.oclothes.mycloset.data.entities.remote.domain.Style
import com.oclothes.mycloset.data.entities.remote.domain.Tag
import com.oclothes.mycloset.data.entities.remote.style.view.StyleCreateView
import com.oclothes.mycloset.data.entities.remote.style.view.StyleDeleteView
import com.oclothes.mycloset.data.entities.remote.style.view.StyleSearchView
import com.oclothes.mycloset.data.entities.remote.style.service.StyleService
import com.oclothes.mycloset.data.entities.remote.tag.service.TagService
import com.oclothes.mycloset.databinding.FragmentSingleClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.signup.TagView
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.ui.main.closet.adapter.SingleClosetStyleListRVAdapter
import com.oclothes.mycloset.ui.main.closet.adapter.SingleClosetTagListRvAdapter
import com.oclothes.mycloset.ui.main.closet.adapter.TagSelectDialogRvAdapter

class StyleFragment(private val f : ClosetMainFragment) : BaseFragment<FragmentSingleClosetBinding>(FragmentSingleClosetBinding::inflate)
    ,View.OnClickListener , TagView, StyleCreateView, StyleDeleteView, StyleSearchView {

    private val styleList: ArrayList<Style> = ArrayList<Style>()
    lateinit var eventTags : ArrayList<Tag>
    lateinit var moodTags : ArrayList<Tag>
    lateinit var seasonTags : ArrayList<Tag>
    lateinit var allTags : ArrayList<Tag>
    private val a by lazy {(requireActivity() as MainActivity)}
    private lateinit var tagListAdapter : SingleClosetTagListRvAdapter
    private lateinit var clothListAdapter: SingleClosetStyleListRVAdapter
    private lateinit var myLayoutManager: GridLayoutManager
    private val selectedTag = ArrayList<Tag>()
    var hasNext = false
    var currentPage = 1
    val me by lazy{this}

    var lastParam1 : HashMap<String, Int>? = null
    var lastParam2 : HashMap<String, String>? = null
    var lastParam3 : ArrayList<Int>? = null
    var lastParam4 : ArrayList<Int>? = null
    var lastParam5 : ArrayList<Int>? = null


    override fun initAfterBinding() {
        if(MainActivity.pageStatus == Status.STATE_STYLE_FRAGMENT){
            setCloset(a.currentCloset!!)
        }
        initTags()
        initOnClickListeners()
        setSingleClosetRvAdapter()

        if(MainActivity.pageStatus == Status.STATE_GALLERY_FAIL){
            f.style.setCloset(a.currentCloset!!)
            MainActivity.pageStatus = Status.STATE_STYLE_FRAGMENT
        }
    }

    private fun setSingleClosetRvAdapter() {
        clothListAdapter = SingleClosetStyleListRVAdapter(f, styleList)
        clothListAdapter.setMyItemClickListener(object :
            SingleClosetStyleListRVAdapter.MyItemClickListener {
            override fun onItemClick(style: Style, position: Int) {

            }
            override fun onRemoveStyle(position: Int) {

            }

            override fun onItemLongClick(style: Style) {
                if(clothListAdapter.getEditMode()){
                    editMode()
                }else{
                    normalMode()
                }
            }
        })
        myLayoutManager = GridLayoutManager(activity, 2)
        binding.singleClosetClothesListRv.layoutManager = myLayoutManager
        binding.singleClosetClothesListRv.adapter = clothListAdapter
        tagListAdapter = SingleClosetTagListRvAdapter(this, allTags)
        binding.singleClosetFilterListRv.adapter = tagListAdapter
        binding.singleClosetFilterListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.singleClosetFilterCountTv.text = "0"


        binding.singleClosetNs.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY ==(v.getChildAt(0).measuredHeight) - v.measuredHeight){
                if(hasNext) {
                    currentPage += 1
                    StyleService.searchClothes(me, lastParam1!!, lastParam2!!, lastParam3!!, lastParam4!!, lastParam5!!, currentPage)
                }
            }
        })
    }

    private fun initOnClickListeners() {
        binding.singleClosetBackBtnIv.setOnClickListener(this)
        binding.singleClosetScissorsIv.setOnClickListener(this)
        binding.singleClosetPlusIv.setOnClickListener(this)
        binding.singleClosetFilterImageIv.setOnClickListener(this)
        binding.singleClosetDeleteIv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.singleClosetBackBtnIv ->{
                f.backPressed()
                clothListAdapter.finishEditMode()
                binding.singleClosetClothesListRv.layoutManager?.scrollToPosition(0)
            }

            binding.singleClosetScissorsIv ->{
                editMode()
            }

            binding.singleClosetPlusIv ->{
                if(a.currentCloset!!.id == 0){
                    showToast("전체 옷에서는 옷을 추가할 수 없습니다.")
                }else{
                    (requireContext() as MainActivity).openGallery()
                }
            }

            binding.singleClosetFilterImageIv ->{
                showTagSelect()
            }

            binding.singleClosetDeleteIv ->{
                setDeleteButtonTrigger()
            }
        }
    }

    private fun editMode() {
        binding.singleClosetScissorsIv.visibility = View.GONE
        binding.singleClosetDeleteIv.visibility = View.VISIBLE
        binding.singleClosetTitleTv.setTextColor(Color.parseColor("#654CBB"))
        clothListAdapter.setEditMode(true)
    }

    private fun normalMode() {
        binding.singleClosetScissorsIv.visibility = View.VISIBLE
        binding.singleClosetDeleteIv.visibility = View.GONE
        binding.singleClosetTitleTv.setTextColor(Color.BLACK)
        clothListAdapter.setEditMode(false)
    }

    fun updateStyleList() {
        val intMap = HashMap<String, Int>()
        if(a.currentCloset!!.id != 0)
            intMap["closetId"] = a.currentCloset!!.id
        intMap["size"] = 20
        val tagMap = HashMap<String, ArrayList<Int>>()
        val moodList = ArrayList<Int>()
        val seasonList = ArrayList<Int>()
        val eventList = ArrayList<Int>()
        for(tag in selectedTag){
            when {
                moodTags.contains(tag) -> moodList.add(tag.id)
                seasonTags.contains(tag) -> seasonList.add(tag.id)
                eventTags.contains(tag) -> eventList.add(tag.id)
            }
        }
        lastParam1 = intMap
        lastParam2 = HashMap<String, String>()
        lastParam3 = eventList
        lastParam4 = moodList
        lastParam5 = seasonList
        this.styleList.clear()
        currentPage = 1
        StyleService.searchClothes(this, intMap, HashMap<String, String>(), eventList, moodList , seasonList, 1)
    }

    fun updateList(selectedTag: ArrayList<Tag>) {
        this.selectedTag.clear()
        this.selectedTag.addAll(selectedTag)
    }

    fun getBinding(): FragmentSingleClosetBinding {
        return binding
    }

    private fun setDeleteButtonTrigger() {
        if(clothListAdapter.getSelectedCount() == 0){
            normalMode()
        }else {
            AlertDialog.Builder(context).setTitle("옷을 삭제 하시겠습니까?")
                .setPositiveButton("예") { p0, p1 ->
                    clothListAdapter.deleteSelectedItem()
                    normalMode()
                }.setNegativeButton("아니요") { p0, p1 ->

                }.show()
        }
    }

    fun setCloset(closet : Closet){
        normalMode()
        binding.singleClosetTitleTv.text = closet.name
        val intMap = HashMap<String, Int>()
        if(a.currentCloset!!.id != 0)
            intMap["closetId"] = closet.id
        intMap["size"] = 20

        lastParam1 = intMap
        lastParam2 = HashMap<String, String>()
        lastParam3 = ArrayList<Int>()
        lastParam4 = ArrayList<Int>()
        lastParam5 = ArrayList<Int>()
        this.styleList.clear()
        currentPage = 1
        StyleService.searchClothes(this, intMap, HashMap<String, String>(), ArrayList<Int>(), ArrayList<Int>(), ArrayList<Int>(), 1)
        emptySelectedTags()
    }

    private fun emptySelectedTags() {
        tagListAdapter.setSelectedTag(ArrayList<Tag>())
        tagListAdapter.notifyDataSetChanged()
        binding.singleClosetFilterCountTv.text = "0"
    }

    private fun initTags(){
        allTags = ArrayList<Tag>()
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
        allTags.addAll(eventTags)
        allTags.addAll(moodTags)
        allTags.addAll(seasonTags)
        tagListAdapter.notifyDataSetChanged()
    }

    override fun onGetTagsFailure(code: Int, message: String) {
    }

    override fun onCreateSuccess(closetId: Int, clothesId : Int,  url: String) {

    }

    override fun onCreateFailure(message: String) {

    }

    override fun onDeleteSuccess() {

    }

    override fun onDeleteFailure() {

    }

    override fun onSearchSuccess(styles: ArrayList<Style>, hasNext: Boolean) {
        this.hasNext = hasNext
        this.styleList.addAll(styles)
        clothListAdapter.notifyDataSetChanged()
    }

    override fun onSearchFailure(message : String) {
        showToast(message)
    }


    private fun showTagSelect() {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialog = dialogBuilder.setView(View(context)).create()
        val layoutParams = WindowManager.LayoutParams()

        val eventAdapter = TagSelectDialogRvAdapter(this.eventTags, selectedTag, dialog)
        val moodAdapter = TagSelectDialogRvAdapter(this.moodTags, selectedTag, dialog)
        val seasonAdapter = TagSelectDialogRvAdapter(this.seasonTags, selectedTag, dialog)

        val gridLayoutManager1 = GridLayoutManager(context, 2)
        val gridLayoutManager2 = GridLayoutManager(context, 2)
        val gridLayoutManager3 = GridLayoutManager(context, 2)

        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT

        dialog.show()
        dialog.window!!.attributes = layoutParams
        dialog.setContentView(R.layout.dialog_tag_select)
        dialog.findViewById<RecyclerView>(R.id.tag_select_event_tag_list_rv).adapter = eventAdapter
        dialog.findViewById<RecyclerView>(R.id.tag_select_mood_tag_list_rv).adapter = moodAdapter
        dialog.findViewById<RecyclerView>(R.id.tag_select_season_tag_list_rv).adapter = seasonAdapter
        dialog.findViewById<RecyclerView>(R.id.tag_select_event_tag_list_rv).layoutManager = gridLayoutManager1
        dialog.findViewById<RecyclerView>(R.id.tag_select_mood_tag_list_rv).layoutManager = gridLayoutManager2
        dialog.findViewById<RecyclerView>(R.id.tag_select_season_tag_list_rv).layoutManager = gridLayoutManager3

        if(selectedTag.size >= 1) {
            dialog.findViewById<TextView>(R.id.tag_select_apply_btn1).visibility = View.GONE
            dialog.findViewById<TextView>(R.id.tag_select_apply_btn2).visibility = View.VISIBLE
        }else{
            dialog.findViewById<TextView>(R.id.tag_select_apply_btn1).visibility = View.VISIBLE
            dialog.findViewById<TextView>(R.id.tag_select_apply_btn2).visibility = View.GONE
        }
        dialog.findViewById<TextView>(R.id.tag_select_apply_btn2).setOnClickListener {
            Log.d("TAGTESTMAN", selectedTag.toString())
            tagListAdapter.setSelectedTag(selectedTag)
            tagListAdapter.notifyDataSetChanged()
            binding.singleClosetFilterCountTv.text = selectedTag.size.toString()
            updateStyleList()
            dialog.dismiss()
        }
        dialog.findViewById<ImageView>(R.id.tag_select_back_btn).setOnClickListener {
            selectedTag.clear()
            selectedTag.addAll(tagListAdapter.getSelectedTag())
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.tag_select_reset_btn).setOnClickListener {
            eventAdapter.reset()
            moodAdapter.reset()
            seasonAdapter.reset()
        }
    }
}