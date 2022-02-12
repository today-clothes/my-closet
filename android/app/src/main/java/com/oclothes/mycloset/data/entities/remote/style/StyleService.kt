package com.oclothes.mycloset.data.entities.remote.style

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oclothes.mycloset.ApplicationClass
import com.oclothes.mycloset.data.entities.ErrorBody
import com.oclothes.mycloset.data.entities.Style
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object StyleService {
    val gson = Gson()
    val type = object : TypeToken<ErrorBody>() {}.type

    fun searchClothes(styleSearchView : StyleSearchView, intMap : Map<String, Int>?, stringMap : Map<String, String>?){
        val styleService = ApplicationClass.retrofit.create(StyleRetrofitInterface::class.java)
        styleService.searchClothes(intMap, stringMap).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val resp = response.body()!!
                when(response.code()){
                    in 200..299-> {
                        val styles = ArrayList<Style>()
                        for (style in resp.data.contents) {
                            style.apply {
                                styles.add(Style(closetId, clothesId, imgUrl, locked, "" ,eventTags, moodTags, seasonTags))
                            }
                        }
                        if(resp.data.hasNext){
                            styleSearchView.onSearchSuccess(styles, true)
                        }else{
                            styleSearchView.onSearchSuccess(styles, false)
                        }
                    }
                    else -> {
                        styleSearchView.onSearchFailure()
                    }
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                styleSearchView.onSearchFailure()
            }
        })
    }

    fun createCloth(styleCreateView : StyleCreateView, closetId: MultipartBody.Part, content: MultipartBody.Part, eventIds: MultipartBody.Part, file: MultipartBody.Part, moodIds: MultipartBody.Part, seasonIds: MultipartBody.Part, title: MultipartBody.Part){
        val styleService = ApplicationClass.retrofit.create(StyleRetrofitInterface::class.java)
        styleService.createCloth(closetId, content, eventIds, file, moodIds, seasonIds, title).enqueue(object : Callback<CreateResponse> {
            override fun onResponse(
                call: Call<CreateResponse>,
                response: Response<CreateResponse>
            ) {
                val resp = response.body()!!
                when(response.code()) {
                    in 200..299 -> {
                        styleCreateView.onSuccess(resp.data.clothesId, resp.data.imgUrl)
                    }
                    else -> {
                        styleCreateView.onFailure()
                    }
                }
            }
            override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                styleCreateView.onFailure()
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
                        }
//                        styleInfoView.onSuccess()
                    }
                    else -> {
                        styleInfoView.onFailure()
                    }
                }
            }
            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                styleInfoView.onFailure()
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
                val resp = response.body()!!
                when(response.code()) {
                    in 200..299 -> {
                        styleDeleteView.onSuccess()
                    }
                    else -> {
                        styleDeleteView.onFailure()
                    }
                }
            }
            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                styleDeleteView.onFailure()
            }
        })
    }

    fun lockCloth(styleLockView : StyleLockView, id : Int){
        val styleService = ApplicationClass.retrofit.create(StyleRetrofitInterface::class.java)
        styleService.lockCloth(id).enqueue(object : Callback<LockResponse>{
            override fun onResponse(call: Call<LockResponse>, response: Response<LockResponse>) {
                val resp = response.body()!!
                when(response.code()) {
                    in 200..299 -> {
                        styleLockView.onSuccess()
                    }
                    else -> {
                    }
                }
            }
            override fun onFailure(call: Call<LockResponse>, t: Throwable) {
                styleLockView.onFailure()
            }
        })
    }

}