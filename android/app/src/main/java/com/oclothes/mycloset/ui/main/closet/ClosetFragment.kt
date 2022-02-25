package com.oclothes.mycloset.ui.main.closet

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.oclothes.mycloset.ApplicationClass
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.remote.domain.Closet
import com.oclothes.mycloset.data.entities.remote.domain.Status
import com.oclothes.mycloset.data.entities.remote.domain.User
import com.oclothes.mycloset.data.entities.remote.user.service.UserService
import com.oclothes.mycloset.data.entities.remote.closet.service.ClosetService
import com.oclothes.mycloset.data.entities.remote.closet.dto.CreateClosetDto
import com.oclothes.mycloset.data.entities.remote.closet.dto.UpdateClosetDto
import com.oclothes.mycloset.data.entities.remote.closet.view.ClosetCreateView
import com.oclothes.mycloset.data.entities.remote.closet.view.ClosetDeleteView
import com.oclothes.mycloset.data.entities.remote.closet.view.ClosetUpdateView
import com.oclothes.mycloset.data.entities.remote.closet.view.ClosetView
import com.oclothes.mycloset.data.entities.remote.user.view.UserInfoView
import com.oclothes.mycloset.databinding.FragmentClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.ui.main.closet.adapter.ClosetListRVAdapter
import com.oclothes.mycloset.utils.getJwt
import com.oclothes.mycloset.utils.saveUser

class ClosetFragment (private val f : ClosetMainFragment): BaseFragment<FragmentClosetBinding>(FragmentClosetBinding::inflate),
    ClosetView, ClosetCreateView, ClosetDeleteView, ClosetUpdateView, UserInfoView {
    val closetList = ArrayList<Closet>()
    lateinit var nickName : String
    val a by lazy {(requireActivity() as MainActivity)}
    lateinit var closetRvAdapter : ClosetListRVAdapter
    val me by lazy{this}
    val closetFragment = this
    var hasNext = false
    var currentPage = 1
    override fun initAfterBinding() {
        getBundle()
        init()
        setClosetRvAdapter()
        setAddBtnTrigger()
    }

    private fun getBundle() {
        arguments?.let {
            //이곳에 번들에 넣을 것 생각하자. 또는 나중에 사용하자.
        }
    }

    private fun setClosetRvAdapter() {
        binding.closetAllClosetListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        closetRvAdapter = ClosetListRVAdapter(closetList, this)
        closetRvAdapter.setMyItemClickListener(object : ClosetListRVAdapter.MyItemClickListener {
            override fun onItemClick(closet: Closet) {
                a.currentCloset = closet
                openCloset(closet)
            }

            override fun onItemLongClick(closet: Closet) {
                showEditDialog(closet)
            }

            override fun onRemoveAlbum(position: Int) {

            }
        })
        binding.closetAllClosetListRv.adapter = closetRvAdapter

        binding.closetNs.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY ==(v.getChildAt(0).measuredHeight) - v.measuredHeight){
                if(hasNext) {
                    currentPage += 1
                    ClosetService.getClosets(me, currentPage)
                }
            }
        })
    }

    private fun setAddBtnTrigger() {
        binding.closetPlusBtnIv.setOnClickListener {
            val myLayout: ConstraintLayout =
                View.inflate(context, R.layout.dialog_create_closet, null) as ConstraintLayout
            AlertDialog.Builder(context).setView(myLayout).setTitle("옷장 생성")
                .setPositiveButton("확인") { _, _ ->
                    val editText = myLayout.findViewById<EditText>(R.id.dialog_create_closet_et)
                    createCloset(editText.text.toString())
                }.show()
        }
    }

    private fun showEditDialog(closet: Closet) {
        val items = resources.getStringArray(R.array.closet_ask)
        AlertDialog.Builder(context).setTitle("'${closet.name}' 옷장 수정")
            .setItems(items) { _, p1 ->
                when (p1) {
                    0 -> {
                        val myLayout: ConstraintLayout = View.inflate(
                            context,
                            R.layout.dialog_create_closet,
                            null
                        ) as ConstraintLayout
                        AlertDialog.Builder(context).setView(myLayout).setTitle("옷장 이름 변경")
                            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    val editText =
                                        myLayout.findViewById<EditText>(R.id.dialog_create_closet_et)
                                    ClosetService.updateCloset(
                                        closetFragment,
                                        UpdateClosetDto(closet.id, editText.text.toString())
                                    )
                                }
                            }).show()
                    }
                    1 -> {
                        ClosetService.deleteCloset(closetFragment, closet.id)
                    }
                }
            }.show()
    }

    private fun createCloset(name : String){
        ClosetService.createCloset(this, CreateClosetDto(name))
    }

    fun init() {
        nickName = ApplicationClass.mSharedPreferences.getString("nickname", "사용자").toString()
        closetList.clear()
        ClosetService.getClosets(this, 1)
        currentPage = 1
        UserService.getUserInfo(this)
    }

    private fun initAllClothes() {

        val glideUrl = GlideUrl("http://10.0.2.2:8080/clothes/images/${closetList[0].thumbnail}", LazyHeaders.Builder()
            .addHeader("Authorization", getJwt()!!)
            .build())
        Glide.with(this).load(glideUrl).into(binding.closetAllClosetImageIv)

        binding.closetAllClosetCv.setOnClickListener {
            f.setVp(ClosetMainFragment.STYLE)
            MainActivity.pageStatus = Status.STATE_STYLE_FRAGMENT
            a.currentCloset = Closet(0, "모든 옷", null)
            f.style.setCloset(Closet(0,"모든 옷", null))
        }
    }

    fun openCloset(closet: Closet) {
        f.openCloset(closet)
    }

    override fun onGetClosetsSuccess(data: ArrayList<Closet>, hasNext : Boolean) {
        closetList.addAll(data)
        closetRvAdapter.notifyDataSetChanged()
        this.hasNext = hasNext
        initAllClothes()
    }


    override fun onGetClosetsFailure(code: Int, message: String) {
        showToast("옷장 불러오기 실패")
    }

    override fun onCreateClosetsSuccess() {
        closetList.clear()
        ClosetService.getClosets(this, 1)
        currentPage = 1
    }

    override fun onCreateClosetsFailure() {
        showToast("옷장 추가하기 실패")
    }

    override fun onClosetDeleteSuccess() {
        closetList.clear()
        ClosetService.getClosets(closetFragment, 1)
        currentPage = 1
        showToast("옷장을 삭제했습니다.")
    }

    override fun onClosetDeleteFailure() {
        showToast("옷장 안에 옷이 있어 삭제할 수 없습니다.")
    }

    override fun onClosetUpdateSuccess() {
        closetList.clear()
        ClosetService.getClosets(closetFragment, 1)
        currentPage = 1
    }

    override fun onClosetUpdateFailure() {
        showToast("옷장 이름 변경 실패")
    }

    override fun onGetUserInfoSuccess(user: User) {
        binding.closetInfoTv.text = "\'${user.nickname}\'님의 옷장"
        saveUser(user)
    }

    override fun onGetUserInfoFailure(message: String) {
        showToast(message)
    }
}