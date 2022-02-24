package com.oclothes.mycloset.ui.main.search

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import com.oclothes.mycloset.data.entities.remote.domain.Style
import com.oclothes.mycloset.data.entities.remote.style.view.StyleSearchView
import com.oclothes.mycloset.data.entities.remote.style.service.StyleService
import com.oclothes.mycloset.data.entities.remote.style.view.RecommendClothView
import com.oclothes.mycloset.databinding.FragmentSearchBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.search.adapter.SearchStyleListRVAdapter
import com.oclothes.mycloset.utils.getUser


class SearchFragment(val f : SearchMainFragment) : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    StyleSearchView, RecommendClothView {
    lateinit var searchAdapter : SearchStyleListRVAdapter
    private val styleList: ArrayList<Style> = ArrayList<Style>()

    private lateinit var myLayoutManager: GridLayoutManager

    override fun initAfterBinding() {
        setRvAdapter()
        setOnClickListeners()
        setRecommendation()
    }

    private fun setRecommendation() {
        binding.searchResultTv.text = "'${getUser()?.nickname}'님을 위한 추천"
        StyleService.recommendClothes(this)
    }

    private fun setOnClickListeners() {
        binding.searchMainRv.setOnClickListener {
            hideKeyboard()
        }

        binding.searchBackground.setOnClickListener {
            hideKeyboard()
        }

        binding.searchBtnTv.setOnClickListener {
            val map = HashMap<String, String>()
            map["keyword"] = binding.searchMainEt.text.toString()
            StyleService.searchClothes(this, HashMap<String, Int>(), map)
        }
    }

    private fun setRvAdapter() {
        searchAdapter = SearchStyleListRVAdapter(this, styleList)
        searchAdapter.setMyItemClickListener(object : SearchStyleListRVAdapter.MyItemClickListener{
            override fun onItemClick(style: Style, position: Int) {
                f.detail.fromSearch(style.clothesId)
                f.getBinding().mainSearchFragmentVp.currentItem = 1
            }
        })
        myLayoutManager = GridLayoutManager(activity, 2)
        binding.searchMainRv.adapter = searchAdapter
        binding.searchMainRv.layoutManager = myLayoutManager
        binding.searchMainEt.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideKeyboard()
                    return true
                }
                return false
            }
        })
    }

    fun backPressed() : Boolean {
        return true
    }


    private fun hideKeyboard (){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    fun getBinding(): FragmentSearchBinding {
        return binding
    }

    override fun onSearchSuccess(styles: ArrayList<Style>, b: Boolean) {
        searchAdapter.styleList.clear()
        searchAdapter.styleList.addAll(styles)
        searchAdapter.notifyDataSetChanged()
    }

    override fun onSearchFailure(message: String) {
        showToast("검색 실패 : $message")
    }

    override fun onRecommendSuccess(styles: ArrayList<Style>) {
        searchAdapter.styleList.clear()
        searchAdapter.styleList.addAll(styles)
        searchAdapter.notifyDataSetChanged()
    }

    override fun onRecommendFailure(message: String) {
        showToast("추천 실패 : $message")
    }


}