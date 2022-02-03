package com.oclothes.mycloset.ui.main.closet

import androidx.viewpager2.widget.ViewPager2
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.databinding.FragmentMainBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.closet.adapter.MainFragmentVPAdapter

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    lateinit var singleCloset : SingleClosetFragment
    lateinit var vpAdapter: MainFragmentVPAdapter
    override fun initAfterBinding() {
        singleCloset = SingleClosetFragment()

        vpAdapter = MainFragmentVPAdapter(this)
        vpAdapter.addFragment(ClosetFragment.newInstance(this))
        vpAdapter.addFragment(singleCloset)

        binding.mainFragmentVp.adapter = vpAdapter
        binding.mainFragmentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.mainFragmentVp.currentItem = 1
        binding.mainFragmentVp.currentItem = 0


    }

    fun openCloset(closet : Closet){
        binding.mainFragmentVp.currentItem = 1
        singleCloset.setSingleCloset(closet)
    }
}