package com.oclothes.mycloset.ui.main.closet

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.data.entities.remote.style.StyleCreateView
import com.oclothes.mycloset.data.entities.remote.style.StyleDeleteView
import com.oclothes.mycloset.data.entities.remote.style.StyleSearchView
import com.oclothes.mycloset.data.entities.remote.style.StyleService
import com.oclothes.mycloset.data.entities.remote.tag.TagService
import com.oclothes.mycloset.databinding.FragmentSingleClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.info.TagView
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.ui.main.closet.adapter.SingleClosetStyleListRVAdapter
import com.oclothes.mycloset.ui.main.closet.adapter.SingleClosetTagListRvAdapter
import com.oclothes.mycloset.ui.main.closet.adapter.TagSelectDialogRvAdapter

class SingleClosetFragment(val f : MainFragment) : BaseFragment<FragmentSingleClosetBinding>(FragmentSingleClosetBinding::inflate)
    ,View.OnClickListener , TagView, StyleCreateView, StyleDeleteView, StyleSearchView{

    val styleList: ArrayList<Style> by lazy{
        ArrayList<Style>()
    }
    lateinit var eventTags : ArrayList<Tag>
    lateinit var moodTags : ArrayList<Tag>
    lateinit var seasonTags : ArrayList<Tag>
    lateinit var allTags : ArrayList<Tag>
    lateinit var currentCloset : Closet
    var isFailFromGallery = false
    var isAllCloset = false
    private lateinit var tagListAdapter : SingleClosetTagListRvAdapter
    private lateinit var clothListAdapter: SingleClosetStyleListRVAdapter
    private lateinit var myLayoutManager: GridLayoutManager
    private val selectedTag = ArrayList<Tag>()


    override fun initAfterBinding() {
        initTags()
        initOnClickListeners()
        setSingleClosetRvAdapter()
        if(isFailFromGallery){
            setSingleCloset((requireActivity() as MainActivity).singleClosetCurrent)
        }
    }

    private fun setSingleClosetRvAdapter() {
        clothListAdapter = SingleClosetStyleListRVAdapter(this, styleList)
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
    }

    fun openDetail(style: Style) {
        f.getBinding().mainFragmentVp.currentItem = 2
        f.detail.fromCloset(style.clothesId)
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
                f.getBinding().mainFragmentVp.currentItem = 0
                clothListAdapter.finishEditMode()
                binding.singleClosetClothesListRv.layoutManager?.scrollToPosition(0)
            }

            binding.singleClosetScissorsIv ->{
                editMode()
            }

            binding.singleClosetPlusIv ->{
                if(isAllCloset){
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
        /**
         * 여기 아주 중요한 부분.,.....,.,.,,, 여기서 태그 검색해서 넘겨주고, 받아서 처리하는 부분은 success에서 처리하는걸로!!!
         *
         *
         */
    }

    fun updateList(selectedTag: ArrayList<Tag>) {
        this.selectedTag.clear()
        this.selectedTag.addAll(selectedTag)
    }

    fun getBinding(): FragmentSingleClosetBinding {
        return binding
    }

    private fun setDeleteButtonTrigger() {

        AlertDialog.Builder(context).setTitle("옷을 삭제 하시겠습니까?")
            .setPositiveButton("예", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    clothListAdapter.deleteSelectedItem()
                    normalMode()
                }
            }).setNegativeButton("아니요",object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {

                }
            }).show()
    }

    fun setSingleCloset(closet : Closet){
        isAllCloset = false
        currentCloset = closet
        normalMode()
        binding.singleClosetTitleTv.text = closet.name
        val intMap = HashMap<String, Int>()
        intMap["closetId"] = closet.id
        intMap["size"] = 20
        StyleService.searchClothes(this, intMap, HashMap<String, String>())
    }

    fun setAllCloset(){
        normalMode()
        isAllCloset = true
        binding.singleClosetTitleTv.text = "모든 옷"
        val intMap = HashMap<String, Int>()
        intMap["page"] = 0
        StyleService.searchClothes(this, intMap, HashMap<String, String>())
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

    override fun onSuccess() {

    }

    override fun onFailure() {

    }

    override fun onSearchSuccess(styles: ArrayList<Style>, b: Boolean) {
        this.styleList.clear()
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