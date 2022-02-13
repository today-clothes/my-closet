package com.oclothes.mycloset.ui.main.closet

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.StyleInfo
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.data.entities.User
import com.oclothes.mycloset.data.entities.remote.auth.AuthService
import com.oclothes.mycloset.data.entities.remote.style.StyleCreateView
import com.oclothes.mycloset.data.entities.remote.style.StyleInfoView
import com.oclothes.mycloset.data.entities.remote.style.StyleService
import com.oclothes.mycloset.data.entities.remote.tag.TagService
import com.oclothes.mycloset.databinding.FragmentDetailBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.info.TagView
import com.oclothes.mycloset.ui.main.closet.adapter.DetailTagListRvAdapter
import com.oclothes.mycloset.ui.main.closet.adapter.TagSelectDialogRvAdapter
import com.oclothes.mycloset.ui.main.closet.view.UserInfoView
import com.oclothes.mycloset.utils.FormDataUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.URI

class DetailFragment(val f : MainFragment) : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate), UserInfoView, StyleInfoView, TagView,
    StyleCreateView {
    var editMode = false
    var isEditable = false
    var mainImage : Bitmap? = null
    var currentClosetId : Int = 3

    lateinit var tagListAdapter: DetailTagListRvAdapter
    val tagListForAdapter = ArrayList<Tag>()

    lateinit var eventTags : ArrayList<Tag>
    lateinit var moodTags : ArrayList<Tag>
    lateinit var seasonTags : ArrayList<Tag>

    lateinit var mainImageUrl : String
    val detailTagRvAdapter : DetailTagListRvAdapter = DetailTagListRvAdapter(this, tagListForAdapter)
    lateinit var mainImageView: ImageView
    private val selectedTag = ArrayList<Tag>()

    override fun initAfterBinding() {
        setPanelView()
        mainImageView = binding.detailMainImageIv
        setClickListeners()
        fromGallery()

        tagListAdapter = DetailTagListRvAdapter(this, tagListForAdapter)
        binding.singleClosetFilterListRv.adapter = tagListAdapter
        binding.singleClosetFilterListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.detailSecondFilterCountTv.text = "0"
    }

    private fun fromGallery() {
        if (!isEditable && editMode) {
            isEditable = true
            binding.detailMainImageIv.setImageBitmap(mainImage)
            onEditModeBegin()
            binding.detailSecondTitleEditEt.setText("")
            binding.detailSecondInfoDetailEditEt.setText("")
            setPersonalInfo()
            clearTagListForEdit()
            TagService.getTags(this)
        }
    }

    fun uploadCloth(){
//        val map = HashMap<String, RequestBody>()
//
//        map["closetId"] = currentClosetId.toString().toRequestBody("text/plain".toMediaType())
//        map["content"] = binding.detailSecondInfoDetailEditEt.text.toString().toRequestBody("text/plain".toMediaType())
//        map["styleTitle"] = binding.detailSecondTitleEditEt.text.toString().toRequestBody("text/plain".toMediaType())
//        val tempArr = ArrayList<Int>()
//        for (eventTag in eventTags) {
//            if(selectedTag.contains(eventTag)){
//                tempArr.add(eventTag.id)
//            }
//        }
//        map["eventIds"] = tempArr.joinToString(separator = ",").toRequestBody("text/plain".toMediaType())
//
//        if(tempArr.size == 0)
//            map["eventIds"] = "".toRequestBody("text/plain".toMediaType())
//
//        tempArr.clear()
//
//        for (moodTag in moodTags) {
//            if(selectedTag.contains(moodTag)){
//                tempArr.add(moodTag.id)
//            }
//        }
//        map["moodIds"] = tempArr.joinToString(separator = ",").toRequestBody("text/plain".toMediaType())
//        if(tempArr.size == 0)
//            map["moodIds"] = "".toRequestBody("text/plain".toMediaType())
//        tempArr.clear()
//
//        for (seasonTag in seasonTags) {
//            if(selectedTag.contains(seasonTag)){
//                tempArr.add(seasonTag.id)
//            }
//        }
//        map["seasonIds"] = tempArr.joinToString(separator = ",").toRequestBody("text/plain".toMediaType())
//        if(tempArr.size == 0)
//            map["seasonIds"] = "".toRequestBody("text/plain".toMediaType())
//
//        val f = File(mainImageUrl)
//        val fb = f.asRequestBody("image/*".toMediaType())
//        val file = MultipartBody.Part.createFormData("file", f.name, fb)


        val tempArr = ArrayList<Int>()

        for (eventTag in eventTags) {
            if(selectedTag.contains(eventTag)){
                tempArr.add(eventTag.id)
            }
        }
        var eventIds = tempArr.joinToString(separator = ",")
        if(tempArr.size == 0)
            eventIds = ""
        tempArr.clear()

        for (moodTag in moodTags) {
            if(selectedTag.contains(moodTag)){
                tempArr.add(moodTag.id)
            }
        }
        var moodIds  = tempArr.joinToString(separator = ",")
        if(tempArr.size == 0)
            moodIds = ""
        tempArr.clear()

        for (seasonTag in seasonTags) {
            if(selectedTag.contains(seasonTag)){
                tempArr.add(seasonTag.id)
            }
        }
        var seasonIds = tempArr.joinToString(separator = ",")
        if(tempArr.size == 0)
            seasonIds = ""

//        StyleService.createCloth(
//            this,
//            content = FormDataUtils.getBody("content", binding.detailSecondInfoDetailEditEt.text.toString()),
//            eventIds = FormDataUtils.getBody("eventIds", eventIds),
//            moodIds = FormDataUtils.getBody("moodIds", moodIds),
//            seasonIds = FormDataUtils.getBody("seasonIds", seasonIds),
//            styleTitle = FormDataUtils.getBody("styleTitle", binding.detailSecondTitleEditEt.text.toString()),
//            closetId = FormDataUtils.getBody("closetId", currentClosetId.toString()),
//            file = FormDataUtils.getImageBody("file", File(mainImageUrl))
//        )


        val data: MultipartBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("content", binding.detailSecondInfoDetailEditEt.text.toString())
            .addFormDataPart("styleTitle", binding.detailSecondTitleEditEt.text.toString())
            .addFormDataPart("closetId", currentClosetId.toString())
            .addFormDataPart("eventIds", eventIds)
            .addFormDataPart("moodIds",moodIds)
            .addFormDataPart("seasonIds", seasonIds)
            .addFormDataPart("file", File(mainImageUrl).name ,File(mainImageUrl).asRequestBody("image/*".toMediaType()))
            .build()



        StyleService.createCloth(this, data)
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
            tagListForAdapter.clear()
            tagListForAdapter.addAll(selectedTag)

            tagListAdapter.notifyDataSetChanged()
            binding.detailSecondFilterCountTv.text = selectedTag.size.toString()
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

    private fun clearTagListForEdit() {
        tagListForAdapter.clear()
        detailTagRvAdapter.notifyDataSetChanged()
    }

    fun fromCloset(id: Int){
        StyleService.getClothInfo(this, id)
        normalModeViewSet()

    }

    private fun setPersonalInfo() {
        AuthService.getUserInfo(this)
    }


    private fun setClickListeners() {
        binding.detailSecondFilterImageIv.setOnClickListener {
            showTagSelect()
        }
        binding.detailMainBackBtnIv.setOnClickListener {
            f.getBinding().mainFragmentVp.currentItem = 1
        }

        mainImageView.setOnClickListener {
            hideKeyboard()
        }

        binding.detailSecondApplyTv.setOnClickListener {
            onEditConfirm()
        }

        binding.detailSecondCancelTv.setOnClickListener {
            onEditDiscard()
        }

        binding.detailSecondEditBtnTv.setOnClickListener {
            onEditModeBegin()
        }

        binding.detailSecondCl.setOnClickListener {
            hideKeyboard()
        }

        binding.detailSecondLockSwitchS.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    binding.detailSecondSwitchLockStateIv.setImageResource(R.drawable.lock_black_locked)
                }else{
                    binding.detailSecondSwitchLockStateIv.setImageResource(R.drawable.lock_black_unlocked)
                }
            }
        })
    }

    private fun setPanelView() {
        binding.detailMainFrmSpl.anchorPoint = 0.5f
        binding.detailMainFrmSpl.isClipPanel = false
    }

    fun getBinding() = binding

    fun setImage(image: Bitmap) {
        if (isEditable) {
            binding.detailMainImageIv.setImageBitmap(mainImage)
        }else{
            mainImage = image
        }
    }

    fun onEditModeBegin() {
        editModeViewSet()
        detailTagRvAdapter.isEditOnDetail = true

    }

    fun onEditDiscard() {
        normalModeViewSet()
        detailTagRvAdapter.isEditOnDetail = false

    }

    fun onEditConfirm() {
        uploadCloth()
        normalModeViewSet()
        detailTagRvAdapter.isEditOnDetail = false
    }

    fun editModeViewSet(){
        binding.detailSecondTitleEditEt.isEnabled = true
        binding.detailSecondInfoDetailEditEt.isEnabled = true
        binding.detailSecondEditBtnTv.visibility = View.GONE
        binding.detailSecondApplyTv.visibility = View.VISIBLE
        binding.detailSecondCancelTv.visibility = View.VISIBLE
        binding.detailSecondEditBtnTv.visibility = View.GONE
        binding.detailSecondInfoUserTv.text = ""
    }

    fun normalModeViewSet(){
        binding.detailSecondTitleEditEt.isEnabled = false
        binding.detailSecondInfoDetailEditEt.isEnabled = false
        binding.detailSecondEditBtnTv.visibility = View.VISIBLE
        binding.detailSecondApplyTv.visibility = View.GONE
        binding.detailSecondCancelTv.visibility = View.GONE
        binding.detailSecondEditBtnTv.visibility = View.VISIBLE
    }

    private fun hideKeyboard (){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    override fun onGetUserInfoSuccess(user: User) {

    }

    override fun onGetUserInfoFailure(message: String) {

    }

    override fun onInfoSuccess(styleinfo: StyleInfo) {
        binding.detailSecondTitleEditEt.setText(styleinfo.styleTitle)
        binding.detailSecondInfoDetailEditEt.setText(styleinfo.content)
        binding.detailSecondInfoUserTv.text = "${styleinfo.updateAt.substring(2,3)}.${styleinfo.updateAt.substring(5,6)}.${styleinfo.updateAt.substring(8,9)}/${styleinfo.userName} ${styleinfo.height}cm ${styleinfo.weight}kg"
        tagListForAdapter.clear()
        tagListForAdapter.addAll(styleinfo.eventTags)
        tagListForAdapter.addAll(styleinfo.moodTags)
        tagListForAdapter.addAll(styleinfo.seasonTags)
        detailTagRvAdapter.notifyDataSetChanged()
    }

    override fun onFailure() {

    }

    fun updateList(selectedTag: ArrayList<Tag>) {
        this.selectedTag.clear()
        this.selectedTag.addAll(selectedTag)
    }

    override fun onGetTagsSuccess(
        eventTags: java.util.ArrayList<Tag>,
        moodTags: java.util.ArrayList<Tag>,
        seasonTags: java.util.ArrayList<Tag>
    ) {
        this.eventTags = eventTags
        this.moodTags = moodTags
        this.seasonTags = seasonTags
        detailTagRvAdapter.notifyDataSetChanged()
    }

    override fun onGetTagsFailure(code: Int, message: String) {

    }

    override fun onCreateSuccess(id: Int, url: String) {
        showToast("설마 돼?")
    }

    override fun onCreateFailure(message: String) {
        showToast(message)

    }
}