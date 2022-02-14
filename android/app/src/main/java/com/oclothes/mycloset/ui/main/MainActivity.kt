package com.oclothes.mycloset.ui.main

import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.google.gson.Gson
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.databinding.ActivityMainBinding
import com.oclothes.mycloset.ui.main.closet.MainFragment
import com.oclothes.mycloset.ui.main.mypage.MyPageFragment
import com.oclothes.mycloset.ui.main.search.MainSearchFragment
import com.oclothes.mycloset.ui.main.search.SearchFragment
import com.oclothes.mycloset.utils.saveString
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var closet : MainFragment
    lateinit var search : MainSearchFragment
    lateinit var mypage : MyPageFragment
    private var backPressedTime: Long = 0
    lateinit var filterActionActivityLauncher: ActivityResultLauncher<Intent>
    var currentPage = 0
    val TIME_INTERVAL: Long = 2000
    var galleryFlag = false
    var galleryFailFlag = false
    lateinit var singleClosetCurrent : Closet

    var detailImage : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment()
        initNavigation()
        supportActionBar?.hide()

        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        }
        permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)



        filterActionActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val currentImageUri = it.data?.data
                try {
                    currentImageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                contentResolver, currentImageUri
                            )
                            this.detailImage = bitmap
                        } else {
                            closet.detail.mainImageUri = currentImageUri
                            val source = ImageDecoder.createSource(contentResolver, currentImageUri)
                            val imageBitmap = ImageDecoder.decodeBitmap(source)
                            galleryFlag = true
                            this.detailImage = imageBitmap
                            closet.detail.currentClosetId = singleClosetCurrent.id
                            closet.detail.currentClosetName = singleClosetCurrent.name
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (it.resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show()
                closet.detail.currentClosetId = singleClosetCurrent.id
                closet.detail.currentClosetName = singleClosetCurrent.name
                closet.singleCloset.currentCloset = singleClosetCurrent
                galleryFailFlag = true
                closet.getBinding().mainFragmentVp.currentItem = 1
            } else {

            }
        }

        binding.mainNavBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    showFragment(closet)
                    if(currentPage == 0){
                        closet.getBinding().mainFragmentVp.currentItem = 0
                    }
                    currentPage = 0

                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    showFragment(search)
                    currentPage = 1
                    return@setOnItemSelectedListener true
                }

                R.id.myFragment -> {
                    showFragment(mypage)
                    currentPage = 2
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        showFragment(closet)
        if(savedInstanceState != null){
            when(savedInstanceState.getInt("currentPage")) {
                0 -> {
                    showFragment(closet)
                    savedInstanceState.getInt("currentItem")?.let{
                        closet.getBinding().mainFragmentVp.currentItem = it
                    }
                }
                1 -> showFragment(search)
                2 -> showFragment(mypage)
                else -> showFragment(closet)
            }
        }
    }

    fun openGallery(){
        singleClosetCurrent = closet.singleCloset.currentCloset
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        filterActionActivityLauncher.launch(intent)

    }

    override fun onPause() {
        super.onPause()
        singleClosetCurrent?.let{
            saveString("currentCloset", Gson().toJson(singleClosetCurrent, Closet::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        com.oclothes.mycloset.utils.getString("currentCloset")?.let {
            singleClosetCurrent = Gson().fromJson(it, Closet::class.java)
        }
        if(galleryFlag) {
            closet.getBinding().mainFragmentVp.currentItem = 2
            closet.detail.setImage(this.detailImage!!)
            galleryFlag = false
            closet.detail.editMode = true
            closet.detail.isEditable = false
        }else if(galleryFailFlag){
            closet.getBinding().mainFragmentVp.currentItem = 1
            closet.detail.currentClosetId = singleClosetCurrent.id
            closet.singleCloset.currentCloset = singleClosetCurrent
            closet.singleCloset.isFailFromGallery = true
            galleryFailFlag = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentPage", currentPage)
        outState.putInt("currentItem", closet.getBinding().mainFragmentVp.currentItem)
    }

    private fun initFragment() {
        mypage = MyPageFragment()
        closet = MainFragment()
        search = MainSearchFragment()
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
                if(search.backPressed()){
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

            2->{
                if(mypage.backPressed()){
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
        }
    }
}
