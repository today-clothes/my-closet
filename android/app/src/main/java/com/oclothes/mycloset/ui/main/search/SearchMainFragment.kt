package com.oclothes.mycloset.ui.main.search

import androidx.viewpager2.widget.ViewPager2
import com.oclothes.mycloset.databinding.FragmentSearchMainBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.search.adapter.MainSearchFragmentVpAdapter

class SearchMainFragment : BaseFragment<FragmentSearchMainBinding>(FragmentSearchMainBinding::inflate) {
    lateinit var search : SearchFragment
    lateinit var detail : SearchDetailFragment
    lateinit var vpAdapter : MainSearchFragmentVpAdapter

    companion object{
        const val SEARCH = 0
        const val DETAIL = 1
    }

    override fun initAfterBinding() {
        search = SearchFragment(this)
        detail = SearchDetailFragment(this)
        setVpAdapter()
    }

    private fun setVpAdapter() {
        vpAdapter = MainSearchFragmentVpAdapter(this)
        vpAdapter.addFragment(search)
        vpAdapter.addFragment(detail)
        binding.mainSearchFragmentVp.adapter = vpAdapter
        binding.mainSearchFragmentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.mainSearchFragmentVp.isUserInputEnabled = false
        binding.mainSearchFragmentVp.currentItem = DETAIL
        binding.mainSearchFragmentVp.currentItem = SEARCH
    }

    fun getBinding() : FragmentSearchMainBinding {
        return binding
    }

    fun setVp(page : Int){
        binding.mainSearchFragmentVp.currentItem = page
    }

    fun backPressed() : Boolean{
        return true
    }
}