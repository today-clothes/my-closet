package com.oclothes.mycloset.ui.main.closet

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.oclothes.mycloset.ApplicationClass
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.data.entities.remote.closet.ClosetService
import com.oclothes.mycloset.databinding.FragmentClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.closet.adapter.ClosetListRVAdapter


class ClosetFragment (f : MainFragment): BaseFragment<FragmentClosetBinding>(FragmentClosetBinding::inflate), ClosetView {
    lateinit var f : Fragment
    lateinit var closetList : ArrayList<Closet>
    lateinit var nickName : String

    override fun initAfterBinding() {
        arguments?.let {
            //이곳에 번들에 넣을 것 생각하자. 또는 나중에 사용하자.
        }

        init()
        binding.closetAllClosetListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val closetRVAdapter = ClosetListRVAdapter(closetList)

        closetRVAdapter.setMyItemClickListener(object : ClosetListRVAdapter.MyItemClickListener{
            override fun onItemClick(closet: Closet) {
                startSingleClosetFragment(closet)
            }

            override fun onRemoveAlbum(position: Int) {

            }
        })
        binding.closetAllClosetListRv.adapter = closetRVAdapter
    }

    private fun init() {
        nickName = ApplicationClass.mSharedPreferences.getString("nickname", "사용자").toString()
        binding.closetInfoTv.text = "\'$nickName\'님의 옷장"
        binding.closetAllClosetNumberTv.text = "${closetList.size.toString()} 개"
        ClosetService.getClosets(this)
    }

    fun startSingleClosetFragment(closet: Closet) {
        (f as MainFragment).openCloset(closet)
    }

    override fun onGetClosetsSuccess(data: ArrayList<Closet>) {
        closetList = data
    }

    override fun onGetClosetsFailure(code: Int, message: String) {
    }
}