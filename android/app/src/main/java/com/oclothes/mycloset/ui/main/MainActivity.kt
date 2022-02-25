package com.oclothes.mycloset.ui.main

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import com.google.gson.Gson
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.remote.domain.Closet
import com.oclothes.mycloset.data.entities.remote.domain.Status
import com.oclothes.mycloset.databinding.ActivityMainBinding
import com.oclothes.mycloset.ui.main.closet.ClosetMainFragment
import com.oclothes.mycloset.ui.main.mypage.MyPageFragment
import com.oclothes.mycloset.ui.main.search.SearchMainFragment
import com.oclothes.mycloset.utils.getString
import com.oclothes.mycloset.utils.saveString

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var closetMain : ClosetMainFragment
    lateinit var searchMain : SearchMainFragment
    lateinit var mypage : MyPageFragment
    private var backPressedTime: Long = 0
    lateinit var filterActionActivityLauncher: ActivityResultLauncher<Intent>
    private val TIME_INTERVAL: Long = 2000

    companion object {
        var pageStatus : Status? = null;
    }
    var currentCloset : Closet? = null
    var currentStyle : Int? = null
    var currentImage : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()
        initFragment()
        initNavigation()
        getPermission()
        initGalleryActionLauncher()
        setNavigation()
    }

    private fun setNavigation() {
        binding.mainNavBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    showFragment(closetMain)
                    if (pageStatus == Status.STATE_CLOSET_FRAGMENT) {
                        closetMain.setVp(ClosetMainFragment.CLOSET)
                    }
                    if(pageStatus == Status.STATE_SEARCH_FRAGMENT || pageStatus == Status.STATE_MY_PAGE_FRAGMENT) {
                        pageStatus = Status.STATE_CLOSET_FRAGMENT
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    showFragment(searchMain)
                    if(pageStatus == Status.STATE_SEARCH_FRAGMENT){
                        searchMain.search.setRecommendation()
                        searchMain.setVp(0)
                    }
                    pageStatus = Status.STATE_SEARCH_FRAGMENT
                    return@setOnItemSelectedListener true
                }

                R.id.myFragment -> {
                    showFragment(mypage)
                    pageStatus = Status.STATE_MY_PAGE_FRAGMENT
                    mypage.setEmail()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        showFragment(closetMain)
        pageStatus = Status.STATE_CLOSET_FRAGMENT
    }

    private fun initGalleryActionLauncher() {
        filterActionActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK && it.data != null) {
                    val currentImageUri = it.data?.data
                    try {
                        currentImageUri?.let {
                            if (Build.VERSION.SDK_INT < 28) {
                                val bitmap = MediaStore.Images.Media.getBitmap(
                                    contentResolver, currentImageUri
                                )
                                currentImage = bitmap
                            } else {
                                closetMain.detail.mainImageUri = currentImageUri
                                val source = ImageDecoder.createSource(contentResolver, currentImageUri)
                                val imageBitmap = ImageDecoder.decodeBitmap(source)
                                pageStatus = Status.STATE_GALLERY_SUCCESS
                                currentImage = imageBitmap
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else if (it.resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show()
                    pageStatus = Status.STATE_GALLERY_FAIL
                } else {

                }
            }
    }

    private fun initActivity() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    private fun getPermission() {
        val permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            }
        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun openGallery(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        filterActionActivityLauncher.launch(intent)
    }

    override fun onPause() {
        super.onPause()
        saveString("pageStatus", pageStatus.toString())

        currentStyle?.let {
            saveString("currentStyle", Gson().toJson(currentStyle, Int::class.java))
        }
        currentCloset?.let{
            saveString("currentCloset", Gson().toJson(currentCloset, Closet::class.java))
        }
        currentImage?.let{
            saveString("currentImage", Gson().toJson(currentImage, Bitmap::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        getString("currentPage")?.let {
            pageStatus = Status.valueOf(it!!)
        }
        getString("currentCloset")?.let {
            currentCloset = Gson().fromJson(it, Closet::class.java)
        }
        getString("currentStyle")?.let {
            currentStyle = Gson().fromJson(it, Int::class.java)
        }

        if(pageStatus != Status.STATE_GALLERY_SUCCESS) {
            getString("currentImage")?.let {
                currentImage = Gson().fromJson(it, Bitmap::class.java)
            }
        }

        when (pageStatus){
            Status.STATE_CLOSET_FRAGMENT ->{
                binding.mainNavBnv.menu.getItem(0).isChecked = true
                closetMain.setVp(ClosetMainFragment.CLOSET)
            }

            Status.STATE_STYLE_FRAGMENT->{
                closetMain.setVp(ClosetMainFragment.STYLE)
                binding.mainNavBnv.menu.getItem(0).isChecked = true
            }

            Status.STATE_DETAIL_FRAGMENT->{
                binding.mainNavBnv.menu.getItem(0).isChecked = true
                closetMain.setVp(ClosetMainFragment.DETAIL)
                closetMain.detail.setImage(currentImage!!)
                closetMain.detail.onEditModeBegin()
            }

            Status.STATE_GALLERY_SUCCESS->{
                closetMain.setVp(ClosetMainFragment.DETAIL)
                closetMain.detail.editMode = true
            }

            Status.STATE_GALLERY_FAIL ->{
                closetMain.setVp(ClosetMainFragment.STYLE)
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show()
            }

            Status.STATE_SEARCH_FRAGMENT->{
                binding.mainNavBnv.menu.getItem(1).isChecked = true
            }

            Status.STATE_SEARCH_DETAIL_FRAGMENT->{
                binding.mainNavBnv.menu.getItem(1).isChecked = true
            }

            Status.STATE_MY_PAGE_FRAGMENT->{
                binding.mainNavBnv.menu.getItem(2).isChecked = true
            }
        }
    }

    private fun initFragment() {
        mypage = MyPageFragment()
        closetMain = ClosetMainFragment()
        searchMain = SearchMainFragment()
    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().add(R.id.main_frm, closetMain).hide(closetMain).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_frm, searchMain).hide(searchMain).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_frm, mypage).hide(mypage).commit()
    }

    fun showFragment(f : Fragment){
        supportFragmentManager.beginTransaction().hide(closetMain).hide(searchMain).hide(mypage).commit()
        supportFragmentManager.beginTransaction().show(f).commit()
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val intervalTime = currentTime - backPressedTime

        when(pageStatus){
            Status.STATE_CLOSET_FRAGMENT, Status.STATE_STYLE_FRAGMENT, Status.STATE_GALLERY_SUCCESS, Status.STATE_GALLERY_FAIL, Status.STATE_DETAIL_FRAGMENT->{
                if(closetMain.backPressed()){
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

            Status.STATE_SEARCH_FRAGMENT, Status.STATE_SEARCH_DETAIL_FRAGMENT->{
                if(searchMain.backPressed()){
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

            Status.STATE_MY_PAGE_FRAGMENT->{
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

            else->{

            }
        }
    }
}
