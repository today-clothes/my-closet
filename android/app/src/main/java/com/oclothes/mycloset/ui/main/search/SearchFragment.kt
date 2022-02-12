package com.oclothes.mycloset.ui.main.search

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.databinding.FragmentSearchBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.ui.main.search.adapter.SearchStyleListRVAdapter
import java.util.concurrent.CopyOnWriteArrayList


class SearchFragment(val a : MainActivity) : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    lateinit var searchAdapter : SearchStyleListRVAdapter
    val styleList: CopyOnWriteArrayList<Style> by lazy{
        CopyOnWriteArrayList<Style>()
    }
    private lateinit var myLayoutManager: GridLayoutManager

    override fun initAfterBinding() {
        searchAdapter = SearchStyleListRVAdapter(this, styleList)
        searchAdapter.setMyItemClickListener(object : SearchStyleListRVAdapter.MyItemClickListener{
            override fun onItemClick(style: Style, position: Int) {

            }
        })

        binding.searchMainRv.setOnClickListener{
            hideKeyboard()
        }

        binding.searchBackground.setOnClickListener{
            hideKeyboard()
        }


        myLayoutManager = GridLayoutManager(activity, 2)
        binding.searchMainRv.adapter = searchAdapter
        binding.searchMainRv.layoutManager = myLayoutManager
        binding.searchMainEt.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if((keyCode == KeyEvent.KEYCODE_ENTER)){
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
}