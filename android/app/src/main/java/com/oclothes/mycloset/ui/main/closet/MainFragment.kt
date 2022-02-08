package com.oclothes.mycloset.ui.main.closet

import android.view.MotionEvent
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.databinding.FragmentMainBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.closet.adapter.MainFragmentVPAdapter

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    lateinit var singleCloset : SingleClosetFragment
    lateinit var vpAdapter: MainFragmentVPAdapter
    lateinit var detail : DetailFragment
    override fun initAfterBinding() {
        detail = DetailFragment(this)
        singleCloset = SingleClosetFragment(this)
        vpAdapter = MainFragmentVPAdapter(this)
        vpAdapter.addFragment(ClosetFragment.newInstance(this))
        vpAdapter.addFragment(singleCloset)
        vpAdapter.addFragment(detail)

        binding.mainFragmentVp.adapter = vpAdapter
        binding.mainFragmentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.mainFragmentVp.isUserInputEnabled = false
        binding.mainFragmentVp.currentItem = 2
        binding.mainFragmentVp.currentItem = 1
        binding.mainFragmentVp.currentItem = 0


    }

    fun getBinding() : FragmentMainBinding{
        return binding
    }

    fun openCloset(closet : Closet){
        binding.mainFragmentVp.currentItem = 1
        singleCloset.setSingleCloset(closet)
    }

    fun backPressed() : Boolean {
        when(binding.mainFragmentVp.currentItem){
            2->{
                binding.mainFragmentVp.currentItem = 1
                return false
            }
            1->{
                binding.mainFragmentVp.currentItem = 0
                return false
            }
            0->{
                return true
            }
            else->{
                return true
            }
        }
    }
}