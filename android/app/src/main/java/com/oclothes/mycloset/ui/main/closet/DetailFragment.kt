package com.oclothes.mycloset.ui.main.closet

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
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
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.data.entities.StyleInfo
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.data.entities.User
import com.oclothes.mycloset.data.entities.remote.auth.AuthService
import com.oclothes.mycloset.data.entities.remote.closet.ClosetService
import com.oclothes.mycloset.data.entities.remote.style.StyleCreateView
import com.oclothes.mycloset.data.entities.remote.style.StyleInfoView
import com.oclothes.mycloset.data.entities.remote.style.StyleLockView
import com.oclothes.mycloset.data.entities.remote.style.StyleService
import com.oclothes.mycloset.data.entities.remote.tag.TagService
import com.oclothes.mycloset.databinding.FragmentDetailBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.info.TagView
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.ui.main.closet.adapter.DetailTagListRvAdapter
import com.oclothes.mycloset.ui.main.closet.adapter.TagSelectDialogRvAdapter
import com.oclothes.mycloset.ui.main.closet.view.ClosetView
import com.oclothes.mycloset.ui.main.closet.view.UserInfoView
import com.oclothes.mycloset.utils.FormDataUtils
import com.oclothes.mycloset.utils.FormDataUtils.asMultipart
import com.oclothes.mycloset.utils.getJwt
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URI

class DetailFragment(val f : MainFragment) : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate), UserInfoView, StyleInfoView, TagView,
    StyleCreateView, ClosetView, StyleLockView {
    var editMode = false
    var isEditable = false
    var mainImage : Bitmap? = null
    var currentClosetId = 0
    var currentClosetName = ""
    var currentCloth = 0

    lateinit var tagListAdapter: DetailTagListRvAdapter
    val tagListForAdapter = ArrayList<Tag>()

    lateinit var eventTags : ArrayList<Tag>
    lateinit var moodTags : ArrayList<Tag>
    lateinit var seasonTags : ArrayList<Tag>

    lateinit var mainImageUri : Uri
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

    fun fromCloset(id: Int){
        editMode = false
        currentCloth = id
        StyleService.getClothInfo(this, id)
        normalModeViewSet()
    }

    fun fromSearch(id: Int) {
        StyleService.getClothInfo(this, id)
        normalModeViewSet()
    }

    fun uploadCloth(){
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
        requireActivity().contentResolver.openInputStream(mainImageUri)?.use { inputStream ->
            val tempFile = createTempFile(requireContext(), "test", "jpg")
            copyStreamToFile(inputStream, tempFile)
            val body = FormDataUtils.getImageBody("file", tempFile)
            StyleService.createCloth(
                this,
                content = FormDataUtils.getBody("content", binding.detailSecondInfoDetailEditEt.text.toString()),
                eventIds = FormDataUtils.getBody("eventIds", eventIds),
                moodIds = FormDataUtils.getBody("moodIds", moodIds),
                seasonIds = FormDataUtils.getBody("seasonIds", seasonIds),
                styleTitle = FormDataUtils.getBody("styleTitle", binding.detailSecondTitleEditEt.text.toString()),
                closetId = FormDataUtils.getBody("closetId", currentClosetId.toString()),
                file = body,
                locked = FormDataUtils.getBody("locked", binding.detailSecondLockSwitchS.isChecked)
            )
        }
    }

    @Throws(IOException::class)
    fun createTempFile(context: Context, fileName: String?, extension: String?): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        return File(storageDir, "$fileName.$extension")
    }

    fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }

    private fun clearTagListForEdit() {
        tagListForAdapter.clear()
        detailTagRvAdapter.notifyDataSetChanged()
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
            if(editMode){
                onEditDiscard()
            }
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

        binding.detailSecondLockSwitchS.setOnClickListener {
            lockCloth()
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

    fun lockCloth(){
        if(!editMode)
            StyleService.lockCloth(this, currentCloth)
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
        editMode = true

    }

    fun onEditDiscard() {
        normalModeViewSet()
        detailTagRvAdapter.isEditOnDetail = false
        if(f.singleCloset.isAllCloset) {
            f.singleCloset.setAllCloset()
        }else
            f.singleCloset.setSingleCloset((requireActivity() as MainActivity).singleClosetCurrent)
        f.getBinding().mainFragmentVp.currentItem = 1
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
    }

    fun normalModeViewSet(){
        binding.detailSecondTitleEditEt.isEnabled = false
        binding.detailSecondInfoDetailEditEt.isEnabled = false
//        binding.detailSecondEditBtnTv.visibility = View.VISIBLE
        binding.detailSecondApplyTv.visibility = View.GONE
        binding.detailSecondCancelTv.visibility = View.GONE
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
        binding.detailSecondLockSwitchS.isChecked = styleinfo.locked

        val glideUrl = GlideUrl("http://10.0.2.2:8080/clothes/images/${styleinfo.imgUrl}", LazyHeaders.Builder()
            .addHeader("Authorization", getJwt()!!)
            .build())
        Glide.with(this).load(glideUrl).into(binding.detailMainImageIv)


        tagListForAdapter.clear()
        tagListForAdapter.addAll(styleinfo.eventTags)
        tagListForAdapter.addAll(styleinfo.moodTags)
        tagListForAdapter.addAll(styleinfo.seasonTags)
        detailTagRvAdapter.notifyDataSetChanged()

    }

    override fun onInfoFailure() {
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

    override fun onCreateSuccess(closetId: Int, clothesId: Int,  url: String) {
        currentClosetId = closetId
        showToast("옷장에 옷이 등록되었습니다.")
        ClosetService.getClosets(this)
    }

    override fun onCreateFailure(message: String) {
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

    override fun onGetClosetsSuccess(data: ArrayList<Closet>) {
        for (closet in data) {
            if(closet.id == currentClosetId){
                f.singleCloset.setSingleCloset(closet)
                f.singleCloset.currentCloset = closet
            }
        }
    }

    override fun onGetClosetsFailure(code: Int, message: String) {

    }

    override fun onLockSuccess() {
        if(binding.detailSecondLockSwitchS.isChecked){
            showToast("사진이 잠겼습니다.")
        }else {
            showToast("사진 잠금이 풀렸습니다.")
        }
        f.singleCloset.setSingleCloset((requireActivity() as MainActivity).singleClosetCurrent)
    }

    override fun onLockFailure() {

    }

}