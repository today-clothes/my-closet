package com.oclothes.mycloset.data.entities.remote.style.service

import android.util.ArrayMap
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oclothes.mycloset.ApplicationClass
import com.oclothes.mycloset.data.entities.remote.domain.ErrorBody
import com.oclothes.mycloset.data.entities.remote.domain.Style
import com.oclothes.mycloset.data.entities.remote.domain.StyleInfo
import com.oclothes.mycloset.data.entities.remote.style.controller.StyleRetrofitInterface
import com.oclothes.mycloset.data.entities.remote.style.dto.*
import com.oclothes.mycloset.data.entities.remote.style.view.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

object StyleService {
    val gson = Gson()
    val type = object : TypeToken<ErrorBody>() {}.type

    fun searchClothes(styleSearchView : StyleSearchView, intMap : Map<String, Int>, stringMap : Map<String, String>, eventIds : ArrayList<Int>,moodIds : ArrayList<Int>, seasonIds : ArrayList<Int>, page : Int){
        val styleService = ApplicationClass.retrofit.create(StyleRetrofitInterface::class.java)
        styleService.searchClothes(intMap, stringMap, eventIds, moodIds, seasonIds, page).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                when(response.code()){
                    in 200..299-> {
                        val resp = response.body()!!
                        val styles = ArrayList<Style>()
                        for (style in resp.data.contents) {
                            style.apply {
                                styles.add(Style(closetId, clothesId, imgUrl, locked, "", styleTitle ,eventTags, moodTags, seasonTags, updatedAt))
                            }
                        }
                        styleSearchView.onSearchSuccess(styles, resp.data.hasNext)

                    }
                    else -> {
                        var errorBody: ErrorBody? =
                            gson.fromJson(response.errorBody()!!.charStream(),
                                type
                            )
                        if (errorBody != null) {
                            styleSearchView.onSearchFailure(errorBody.errorMessage)
                        }else {
                            styleSearchView.onSearchFailure("알 수 없는 오류 in response")
                        }
                    }
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                styleSearchView.onSearchFailure("알 수 없는 오류 in failure")
            }
        })
    }

    fun createCloth(styleCreateView : StyleCreateView, closetId: MultipartBody.Part, locked : MultipartBody.Part, content: MultipartBody.Part, eventIds: MultipartBody.Part, file: MultipartBody.Part, moodIds: MultipartBody.Part, seasonIds: MultipartBody.Part, styleTitle: MultipartBody.Part){
        val styleService = ApplicationClass.retrofit.create(StyleRetrofitInterface::class.java)
        styleService.createCloth(closetId, content, eventIds, file, moodIds, seasonIds, styleTitle, locked).enqueue(object : Callback<CreateResponse> {
            override fun onResponse(
                call: Call<CreateResponse>,
                response: Response<CreateResponse>
            ) {
                when(response.code()) {
                    in 200..299 -> {
                        val resp = response.body()!!
                        styleCreateView.onCreateSuccess(resp.data.closetId, resp.data.clothesId ,resp.data.imgUrl)
                    }
                    else -> {
                        var errorBody: ErrorBody? =
                            gson.fromJson(response.errorBody()!!.charStream(),
                                type
                            )
                        if (errorBody != null) {
                            styleCreateView.onCreateFailure(errorBody.errorMessage)
                        }else {
                            styleCreateView.onCreateFailure("알 수 없는 오류 in response")
                        }
                    }
                }
            }
            override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                styleCreateView.onCreateFailure("알 수 없는 오류 in failure")
            }
        })
    }

    fun getClothInfo(styleInfoView : StyleInfoView, id : Int){
        val styleService = ApplicationClass.retrofit.create(StyleRetrofitInterface::class.java)
        styleService.getClothInfo(id).enqueue(object : Callback<InfoResponse>{
            override fun onResponse(call: Call<InfoResponse>, response: Response<InfoResponse>) {
                val resp = response.body()!!
                when(response.code()) {
                    in 200..299 -> {
                        resp.data.apply {
                            val styleInfo = StyleInfo(content, height, styleTitle, updatedAt, userName, weight, eventTags, moodTags, seasonTags, imgUrl, locked)
                            styleInfoView.onInfoSuccess(styleInfo)
                        }
                    }
                    else -> {
                        styleInfoView.onInfoFailure()
                    }
                }
            }
            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                styleInfoView.onInfoFailure()
            }
        })
    }

    fun deleteCloth(styleDeleteView : StyleDeleteView, id : Int){
        val styleService = ApplicationClass.retrofit.create(StyleRetrofitInterface::class.java)
        styleService.deleteCloth(id).enqueue(object : Callback<DeleteResponse>{
            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                when(response.code()) {
                    in 200..299 -> {
                        styleDeleteView.onDeleteSuccess()
                    }
                    else -> {
                        styleDeleteView.onDeleteFailure()
                    }
                }
            }
            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                styleDeleteView.onDeleteFailure()
            }
        })
    }

    fun lockCloth(styleLockView : StyleLockView, id : Int){
        val styleService = ApplicationClass.retrofit.create(StyleRetrofitInterface::class.java)
        styleService.lockCloth(id).enqueue(object : Callback<LockResponse>{
            override fun onResponse(call: Call<LockResponse>, response: Response<LockResponse>) {
                when(response.code()) {
                    in 200..299 -> {
                        styleLockView.onLockSuccess()
                    }
                    else -> {
                    }
                }
            }
            override fun onFailure(call: Call<LockResponse>, t: Throwable) {
                styleLockView.onLockFailure()
            }
        })
    }

    fun recommendClothes(recommendClothView : RecommendClothView){
        val styleService = ApplicationClass.retrofit.create(StyleRetrofitInterface::class.java)
        styleService.recommendClothes().enqueue(object : Callback<RecommendResponse>{
            override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {
                when(response.code()) {
                    in 200..299 -> {
                        val resp = response.body()!!
                        val styles = ArrayList<Style>()
                        for (style in resp.data) {
                            style.apply {
                                styles.add(Style(closetId, clothesId, imgUrl, locked, "", styleTitle ,eventTags, moodTags, seasonTags, updatedAt))
                            }
                        }
                        recommendClothView.onRecommendSuccess(styles)
                    }
                    else -> {
                        var errorBody: ErrorBody? =
                            gson.fromJson(
                                response.errorBody()!!.charStream(),
                                type
                            )
                        if (errorBody != null) {
                            recommendClothView.onRecommendFailure(errorBody.errorMessage)
                        } else {
                            recommendClothView.onRecommendFailure("알 수 없는 오류 in response")
                        }
                    }
                }
            }
            override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                Log.d("FAILURE", if(t is IOException) "네트워크 문제" else "시리얼라이즈 문제")
                recommendClothView.onRecommendFailure("알 수 없는 오류 in failure")
            }
        })
    }

}