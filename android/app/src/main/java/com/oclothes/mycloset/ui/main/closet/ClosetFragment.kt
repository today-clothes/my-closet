package com.oclothes.mycloset.ui.main.closet

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.oclothes.mycloset.ApplicationClass
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.databinding.FragmentClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.ui.main.closet.adapter.ClosetListRVAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ClosetFragment : BaseFragment<FragmentClosetBinding>(FragmentClosetBinding::inflate) {

    private var param1: String? = null
    private var param2: String? = null

    lateinit var closetList : ArrayList<Closet>
    lateinit var nickName : String

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClosetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun initAfterBinding() {
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        initUser()
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

    private fun initUser() {
        nickName = ApplicationClass.mSharedPreferences.getString("nickname", null).toString()
        binding.closetInfoTv.text = "\'" + nickName + "\'님의 옷장"
    }


    private fun initClosetList(){
        closetList = ArrayList<Closet>()
        for(i in 1..7) {
            closetList.add(Closet())
        }
    }

    fun startSingleClosetFragment(closet: Closet) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .add(R.id.main_frm, SingleClosetFragment.newInstance())
            .addToBackStack(null)
            .hide(this)
            .commit()
    }

}