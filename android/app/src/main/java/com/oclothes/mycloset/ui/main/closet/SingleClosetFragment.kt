package com.oclothes.mycloset.ui.main.closet

import android.os.Bundle
import android.view.View
import com.oclothes.mycloset.databinding.FragmentSingleClosetBinding
import com.oclothes.mycloset.ui.BaseFragment

class SingleClosetFragment : BaseFragment<FragmentSingleClosetBinding>(FragmentSingleClosetBinding::inflate) ,View.OnClickListener {

    override fun initAfterBinding() {

    }

    override fun onClick(p0: View?) {

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
        fun newInstance() =
            SingleClosetFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}