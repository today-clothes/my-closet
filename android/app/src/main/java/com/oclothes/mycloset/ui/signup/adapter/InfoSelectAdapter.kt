package com.oclothes.mycloset.ui.signup.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oclothes.mycloset.ui.signup.PersonalInfoFragment
import com.oclothes.mycloset.ui.signup.SignUpEmailFragment
import com.oclothes.mycloset.ui.signup.StyleInfoFragment

class InfoSelectAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() : Int = 3;

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SignUpEmailFragment()
            1 -> PersonalInfoFragment()
            else -> StyleInfoFragment()
        }
    }

}