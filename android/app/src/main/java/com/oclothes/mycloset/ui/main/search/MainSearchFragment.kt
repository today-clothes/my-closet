package com.oclothes.mycloset.ui.main.search

import androidx.viewpager2.widget.ViewPager2
import com.oclothes.mycloset.databinding.FragmentMainSearchBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.search.adapter.MainSearchFragmentVpAdapter

class MainSearchFragment : BaseFragment<FragmentMainSearchBinding>(FragmentMainSearchBinding::inflate) {
    lateinit var search : SearchFragment
    lateinit var detail : SearchDetailFragment
    lateinit var vpAdapter : MainSearchFragmentVpAdapter

    override fun initAfterBinding() {
        search = SearchFragment(this)
        detail = SearchDetailFragment(this)
        vpAdapter = MainSearchFragmentVpAdapter(this)
        vpAdapter.addFragment(search)
        vpAdapter.addFragment(detail)

        binding.mainSearchFragmentVp.adapter = vpAdapter
        binding.mainSearchFragmentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.mainSearchFragmentVp.isUserInputEnabled = false
        binding.mainSearchFragmentVp.currentItem = 1
        binding.mainSearchFragmentVp.currentItem = 0
    }

    fun getBinding() : FragmentMainSearchBinding {
        return binding
    }

    fun backPressed() : Boolean{
        return true
    }
}