package com.oclothes.mycloset.ui.main.closet

import androidx.viewpager2.widget.ViewPager2
import com.oclothes.mycloset.data.entities.remote.domain.Closet
import com.oclothes.mycloset.data.entities.remote.domain.Status
import com.oclothes.mycloset.databinding.FragmentClosetMainBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.ui.main.closet.adapter.MainFragmentVPAdapter

class ClosetMainFragment : BaseFragment<FragmentClosetMainBinding>(FragmentClosetMainBinding::inflate) {
    lateinit var style : StyleFragment
    lateinit var detail : DetailFragment
    lateinit var closet : ClosetFragment
    lateinit var vpAdapter: MainFragmentVPAdapter
    val a by lazy {(requireActivity() as MainActivity)}

    companion object{
        const val CLOSET = 0
        const val STYLE = 1
        const val DETAIL = 2
    }

    override fun initAfterBinding() {
        detail = DetailFragment(this)
        style = StyleFragment(this)
        closet = ClosetFragment(this)
        setVpAdapter()


    }

    private fun setVpAdapter() {
        vpAdapter = MainFragmentVPAdapter(this)
        vpAdapter.addFragment(closet)
        vpAdapter.addFragment(style)
        vpAdapter.addFragment(detail)
        binding.mainFragmentVp.adapter = vpAdapter
        binding.mainFragmentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.mainFragmentVp.isUserInputEnabled = false
        binding.mainFragmentVp.currentItem = STYLE
        binding.mainFragmentVp.currentItem = DETAIL
        binding.mainFragmentVp.currentItem = CLOSET
    }

    fun setVp(page : Int){
        binding.mainFragmentVp.currentItem = page
    }

    fun openCloset(closet : Closet){
        setVp(STYLE)
        style.setCloset(closet)
        MainActivity.pageStatus = Status.STATE_STYLE_FRAGMENT
    }

    fun openStyle(styleId : Int){
        setVp(DETAIL)
        detail.fromCloset(styleId)
        a.currentStyle = styleId
        MainActivity.pageStatus = Status.STATE_DETAIL_FRAGMENT
    }

    fun backPressed() : Boolean {
        when(binding.mainFragmentVp.currentItem){
            2->{
                setVp(STYLE)
                if(MainActivity.pageStatus == Status.STATE_GALLERY_SUCCESS){
                    openCloset((requireActivity() as MainActivity).currentCloset!!)
                }
                MainActivity.pageStatus = Status.STATE_STYLE_FRAGMENT

                return false
            }
            1->{
                setVp(CLOSET)
                MainActivity.pageStatus = Status.STATE_CLOSET_FRAGMENT
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