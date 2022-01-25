package com.oclothes.mycloset.ui.info.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oclothes.mycloset.ui.info.PersonalInfoFragment
import com.oclothes.mycloset.ui.info.StyleInfoFragment

class InfoSelectAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() : Int = 2;

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PersonalInfoFragment()
            else -> StyleInfoFragment()
        }
    }

}