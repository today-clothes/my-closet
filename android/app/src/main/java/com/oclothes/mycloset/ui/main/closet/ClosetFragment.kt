package com.oclothes.mycloset.ui.main.closet

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.oclothes.mycloset.ApplicationClass
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.databinding.FragmentClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.closet.adapter.ClosetListRVAdapter


class ClosetFragment (): BaseFragment<FragmentClosetBinding>(FragmentClosetBinding::inflate) {

    lateinit var f : Fragment
    lateinit var closetList : ArrayList<Closet>
    lateinit var nickName : String
    companion object {
        @JvmStatic
        fun newInstance(f : Fragment) =
            ClosetFragment().apply {
                this.f = f
            }
    }

    override fun initAfterBinding() {
        arguments?.let {
            //이곳에 번들에 넣을 것 생각하자. 또는 나중에 사용하자.
        }
        init()
        initClosetList()
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
        nickName = ApplicationClass.mSharedPreferences.getString("nickname", null).toString()
        binding.closetInfoTv.text = "\'$nickName\'님의 옷장"
    }


    private fun initClosetList(){
        closetList = ArrayList<Closet>()
        for(i in 1..7) {
            closetList.add(Closet("제주도옷"))
        }
    }

    fun startSingleClosetFragment(closet: Closet) {
        (f as MainFragment).openCloset(closet)
    }
}