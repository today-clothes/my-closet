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

    fun showFragment(f : Fragment){
        supportFragmentManager.beginTransaction().hide(closet).hide(search).hide(mypage).commit()
        supportFragmentManager.beginTransaction().show(f).commit()
    }
}
