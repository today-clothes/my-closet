package com.oclothes.mycloset.ui.main.closet

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.oclothes.mycloset.ApplicationClass
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.data.entities.User
import com.oclothes.mycloset.data.entities.remote.auth.AuthService
import com.oclothes.mycloset.data.entities.remote.closet.ClosetService
import com.oclothes.mycloset.data.entities.remote.closet.CreateClosetDto
import com.oclothes.mycloset.data.entities.remote.closet.UpdateClosetDto
import com.oclothes.mycloset.databinding.FragmentClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.closet.adapter.ClosetListRVAdapter
import com.oclothes.mycloset.ui.main.closet.view.*

class ClosetFragment (private val f : MainFragment): BaseFragment<FragmentClosetBinding>(FragmentClosetBinding::inflate),
    ClosetView, ClosetCreateView, ClosetDeleteView, ClosetUpdateView, UserInfoView {
    lateinit var closetList : ArrayList<Closet>
    lateinit var nickName : String
    lateinit var closetRvAdapter : ClosetListRVAdapter
    val closetFragment = this
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
                startSingleClosetFragment(closet)
            }

            override fun onItemLongClick(closet: Closet) {
                showDialog(closet)
            }

            override fun onRemoveAlbum(position: Int) {

            }
        })
        binding.closetAllClosetListRv.adapter = closetRvAdapter
    }

    private fun setAddBtnTrigger() {
        binding.closetPlusBtnIv.setOnClickListener {
            val myLayout: ConstraintLayout =
                View.inflate(context, R.layout.dialog_create_closet, null) as ConstraintLayout
            AlertDialog.Builder(context).setView(myLayout).setTitle("옷장 생성")
                .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        val editText = myLayout.findViewById<EditText>(R.id.dialog_create_closet_et)
                        createCloset(editText.text.toString())
                    }
                }).show()
        }
    }

    private fun showDialog(closet: Closet) {
        val items = resources.getStringArray(R.array.closet_ask)
        AlertDialog.Builder(context).setTitle("'${closet.name}' 옷장 수정")
            .setItems(items, object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
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
                }

            }).show()
    }

    private fun createCloset(name : String){
        ClosetService.createCloset(this, CreateClosetDto(name))
    }

    private fun init() {
        closetList = ArrayList<Closet>()
        nickName = ApplicationClass.mSharedPreferences.getString("nickname", "사용자").toString()
        binding.closetInfoTv.text = "\'$nickName\'님의 옷장"
        binding.closetAllClosetNumberTv.text = "${closetList.size.toString()} 개"
        ClosetService.getClosets(this)
        AuthService.getUserInfo(this)
    }

    fun startSingleClosetFragment(closet: Closet) {
        f.openCloset(closet)
    }

    override fun onGetClosetsSuccess(data: ArrayList<Closet>) {
        closetList.clear()
        closetList.addAll(data)
        notifyClosetChanged()
        closetRvAdapter.notifyDataSetChanged()
    }

    private fun notifyClosetChanged() {
        closetRvAdapter.notifyDataSetChanged()
        binding.closetAllClosetNumberTv.text = closetList.size.toString() + " 개"
    }

    override fun onGetClosetsFailure(code: Int, message: String) {
        showToast("옷장 불러오기 실패")
    }

    override fun onCreateClosetsSuccess() {
        ClosetService.getClosets(this)
    }

    override fun onCreateClosetsFailure() {
        showToast("옷장 추가하기 실패")
    }

    override fun onClosetDeleteSuccess() {
        ClosetService.getClosets(closetFragment)
    }

    override fun onClosetDeleteFailure() {
        showToast("옷장 삭제 실패")

    }

    override fun onClosetUpdateSuccess() {
        ClosetService.getClosets(closetFragment)
    }

    override fun onClosetUpdateFailure() {
        showToast("옷장 이름 변경 실패")
    }

    override fun onGetUserInfoSuccess(user: User) {
        binding.closetInfoTv.text = "\'${user.nickname}\'님의 옷장"

    }

    override fun onGetUserInfoFailure(message: String) {
        showToast(message)
    }
}