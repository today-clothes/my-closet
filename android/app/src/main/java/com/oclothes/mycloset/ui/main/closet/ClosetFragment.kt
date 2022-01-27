package com.oclothes.mycloset.ui.main.closet

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.oclothes.mycloset.R
import com.oclothes.mycloset.databinding.FragmentClosetBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.MainActivity


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClosetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ClosetFragment : BaseFragment<FragmentClosetBinding>(FragmentClosetBinding::inflate) {

    private var param1: String? = null
    private var param2: String? = null

    private fun startTagSelection() {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, TagSelectFragment().apply {
                arguments = Bundle().apply {
                    //넘길 데이터를 써주는 부분임.
                }
            })
            .commitAllowingStateLoss()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClosetFragment.
         */
        // TODO: Rename and change types and number of parameters
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
    }


}