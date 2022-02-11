package com.oclothes.mycloset.data.entities.remote.tag

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oclothes.mycloset.ApplicationClass
import com.oclothes.mycloset.data.entities.ErrorBody
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.ui.info.TagView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TagService {
    val gson = Gson()
    val type = object : TypeToken<ErrorBody>() {}.type

    fun getTags(tagView : TagView){
        val tagService = ApplicationClass.retrofit.create(TagRetrofitInterface::class.java)
        tagService.getTags().enqueue(object : Callback<TagResponse>{
            override fun onResponse(call: Call<TagResponse>, response: Response<TagResponse>) {
                when(response.code()){
                    200 -> {
                        val resp = response.body()!!
                        val eventTags = ArrayList<Tag>()
                        val moodTags = ArrayList<Tag>()
                        val seasonTags = ArrayList<Tag>()
                        for (content in resp.data.eventTags) {
                            val tag = Tag(content.id, content.name)
                            eventTags.add(tag)
                        }
                        for (content in resp.data.moodTags) {
                            val tag = Tag(content.id, content.name)
                            moodTags.add(tag)
                        }
                        for (content in resp.data.seasonTags) {
                            val tag = Tag(content.id, content.name)
                            seasonTags.add(tag)
                        }
                        tagView.onGetTagsSuccess(eventTags, moodTags, seasonTags)
                    }
                    else -> {
                        var errorBody: ErrorBody? =
                            gson.fromJson(response.errorBody()!!.charStream(), type)
                        if (errorBody != null) {
                            tagView.onGetTagsFailure(response.code(), errorBody.errorMessage)
                            return
                        }
                    }
                }
            }
            override fun onFailure(call: Call<TagResponse>, t: Throwable) {
                tagView.onGetTagsFailure(400 ,"알 수 없는 오류가 발생했습니다.")
            }
        })


    }


}