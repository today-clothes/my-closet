package com.oclothes.mycloset.ui.main.search

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.data.entities.remote.domain.Status
import com.oclothes.mycloset.data.entities.remote.domain.Style
import com.oclothes.mycloset.data.entities.remote.style.view.StyleSearchView
import com.oclothes.mycloset.data.entities.remote.style.service.StyleService
import com.oclothes.mycloset.data.entities.remote.style.view.RecommendClothView
import com.oclothes.mycloset.databinding.FragmentSearchBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.ui.main.search.adapter.SearchStyleListRVAdapter
import com.oclothes.mycloset.utils.getUser


class SearchFragment(val f : SearchMainFragment) : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    StyleSearchView, RecommendClothView {
    lateinit var searchAdapter : SearchStyleListRVAdapter
    private val styleList: ArrayList<Style> = ArrayList<Style>()
    var lastParam1 : HashMap<String, Int>? = null
    var lastParam2 : HashMap<String, String>? = null
    var lastParam3 : ArrayList<Int>? = null
    var lastParam4 : ArrayList<Int>? = null
    var lastParam5 : ArrayList<Int>? = null
    var hasNext = false
    var currentPage = 1
    var status = 0
    val me by lazy{this}
    private lateinit var myLayoutManager: GridLayoutManager

    override fun initAfterBinding() {
        setRvAdapter()
        setOnClickListeners()
        setRecommendation()
    }

    fun setRecommendation() {
        binding.searchResultTv.text = "'${getUser()?.nickname}'님을 위한 추천"
        binding.searchMainEt.setText("")
        searchAdapter.styleList.clear()
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
            if(binding.searchMainEt.text.toString().isNotEmpty()) {
                hideKeyboard()
                val map = HashMap<String, String>()
                binding.searchResultTv.text = "'${binding.searchMainEt.text.toString()}' 검색 결과"
                map["keyword"] = binding.searchMainEt.text.toString()
                lastParam1 = HashMap<String, Int>()
                lastParam2 = map
                lastParam3 = ArrayList<Int>()
                lastParam4 = ArrayList<Int>()
                lastParam5 = ArrayList<Int>()
                this.styleList.clear()
                currentPage = 1
                StyleService.searchClothes(
                    this,
                    HashMap<String, Int>(),
                    map,
                    ArrayList<Int>(),
                    ArrayList<Int>(),
                    ArrayList<Int>(),
                    1
                )
            }else{
                showToast("검색어를 입력하세요")
            }
        }
    }

    private fun setRvAdapter() {
        searchAdapter = SearchStyleListRVAdapter(this, styleList)
        searchAdapter.setMyItemClickListener(object : SearchStyleListRVAdapter.MyItemClickListener{
            override fun onItemClick(style: Style, position: Int) {
                f.detail.fromSearch(style.clothesId)
                MainActivity.pageStatus = Status.STATE_SEARCH_DETAIL_FRAGMENT
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

        binding.searchMainRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.canScrollVertically(-1)) {
                    if(hasNext) {
                        currentPage += 1
                        StyleService.searchClothes(me, lastParam1!!, lastParam2!!, lastParam3!!, lastParam4!!, lastParam5!!, currentPage)
                    }
                }
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
        this.hasNext = b
        this.status = 1
        searchAdapter.styleList.addAll(styles)
        searchAdapter.notifyDataSetChanged()
    }

    override fun onSearchFailure(message: String) {
        showToast("검색 실패 : $message")
    }

    override fun onRecommendSuccess(styles: ArrayList<Style>) {
        status = 2
        searchAdapter.styleList.addAll(styles)
        searchAdapter.notifyDataSetChanged()
    }

    override fun onRecommendFailure(message: String) {
        showToast("추천 실패 : $message")
    }


}