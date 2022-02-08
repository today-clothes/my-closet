package com.oclothes.mycloset.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import com.oclothes.mycloset.R
import com.oclothes.mycloset.databinding.ActivityMainBinding
import com.oclothes.mycloset.ui.main.closet.MainFragment
import com.oclothes.mycloset.ui.main.mypage.MyPageFragment
import com.oclothes.mycloset.ui.main.search.SearchFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var closet : MainFragment
    lateinit var search : SearchFragment
    lateinit var mypage : MyPageFragment
    private var backPressedTime: Long = 0
    var currentPage = 0
    val TIME_INTERVAL: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment()
        initNavigation()
        supportActionBar?.hide()

        binding.mainNavBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    showFragment(closet)
                    if(currentPage == 0){
                        closet.getBinding().mainFragmentVp.currentItem = 0
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    showFragment(search)
                    return@setOnItemSelectedListener true
                }

                R.id.myFragment -> {
                    showFragment(mypage)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        showFragment(closet)
    }

    private fun initFragment() {
        mypage = MyPageFragment()
        closet = MainFragment()
        search = SearchFragment()
    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().add(R.id.main_frm, closet).hide(closet).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_frm, search).hide(search).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_frm, mypage).hide(mypage).commit()

    }

    private fun showFragment(f : Fragment){
        when(f){
            is MainFragment-> {
                currentPage = 0
            }
            is SearchFragment->{
                currentPage = 1
            }

            is MyPageFragment->{
                currentPage = 2
            }
        }
        supportFragmentManager.beginTransaction().hide(closet).hide(search).hide(mypage).commit()
        supportFragmentManager.beginTransaction().show(f).commit()
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val intervalTime = currentTime - backPressedTime


        when(currentPage){
            0->{
                if(closet.backPressed()){
                    val currentTime = System.currentTimeMillis()
                    val intervalTime = currentTime - backPressedTime
                    if (intervalTime in 0..TIME_INTERVAL) {
                        finish()
                    }
                    else{
                        backPressedTime = currentTime
                    }
                }
            }

            1->{
                search.backPressed()
            }

            2->{
                mypage.backPressed()
            }
        }
    }
}
