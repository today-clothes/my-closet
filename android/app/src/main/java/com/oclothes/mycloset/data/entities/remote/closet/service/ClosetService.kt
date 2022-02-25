package com.oclothes.mycloset.data.entities.remote.closet.service

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oclothes.mycloset.ApplicationClass
import com.oclothes.mycloset.ApplicationClass.Companion.TAG
import com.oclothes.mycloset.data.entities.remote.closet.controller.ClosetRetrofitInterface
import com.oclothes.mycloset.data.entities.remote.domain.Closet
import com.oclothes.mycloset.data.entities.remote.domain.ErrorBody
import com.oclothes.mycloset.data.entities.remote.closet.dto.*
import com.oclothes.mycloset.data.entities.remote.closet.view.ClosetCreateView
import com.oclothes.mycloset.data.entities.remote.closet.view.ClosetDeleteView
import com.oclothes.mycloset.data.entities.remote.closet.view.ClosetUpdateView
import com.oclothes.mycloset.data.entities.remote.closet.view.ClosetView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ClosetService {
    val gson = Gson()
    val type = object : TypeToken<ErrorBody>() {}.type


    fun getClosets(closetView: ClosetView){
        val closetService = ApplicationClass.retrofit.create(ClosetRetrofitInterface::class.java)

        closetService.getClosets().enqueue(object : Callback<ClosetResponse> {
            override fun onResponse(call: Call<ClosetResponse>, response: Response<ClosetResponse>) {
                val resp = response.body()!!
                when(response.code()){
                    200 -> {
                        val closets = ArrayList<Closet>()
                        for (content in resp.data.contents) {
                            val closet = Closet(content.id, content.name)
                            closets.add(closet)
                        }
                        closetView.onGetClosetsSuccess(closets)
                    }
                    else -> closetView.onGetClosetsFailure(response.code(), resp.message)
                }
            }
            override fun onFailure(call: Call<ClosetResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                closetView.onGetClosetsFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun createCloset(closetCreateView : ClosetCreateView, createClosetDto: CreateClosetDto){
        val closetCreateService = ApplicationClass.retrofit.create(ClosetRetrofitInterface::class.java)

        closetCreateService.createCloset(createClosetDto).enqueue(object: Callback<CreateResponse> {
            override fun onResponse(
                call: Call<CreateResponse>,
                response: Response<CreateResponse>,
            ) {
                when(response.code()){
                    201 -> {
                        closetCreateView.onCreateClosetsSuccess()
                    }
                    else -> closetCreateView.onCreateClosetsFailure()
                }
            }

            override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                closetCreateView.onCreateClosetsFailure()
            }
        })
    }

    fun deleteCloset(closetDeleteView : ClosetDeleteView, id : Int){
        val closetDeleteService = ApplicationClass.retrofit.create(ClosetRetrofitInterface::class.java)

        closetDeleteService.deleteCloset(id).enqueue(object : Callback<DeleteResponse>{
            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                when(response.code()){
                    200 -> {
                        closetDeleteView.onClosetDeleteSuccess()
                    }
                    else -> closetDeleteView.onClosetDeleteFailure()
                }
            }
            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                closetDeleteView.onClosetDeleteFailure()
            }
        })
    }

    fun updateCloset(closetUpdateView : ClosetUpdateView, closetUpdateDto : UpdateClosetDto){
        val closetUpdateService = ApplicationClass.retrofit.create(ClosetRetrofitInterface::class.java)

        closetUpdateService.updateCloset(closetUpdateDto.id, closetUpdateDto).enqueue(object : Callback<UpdateResponse>{
            override fun onResponse(
                call: Call<UpdateResponse>,
                response: Response<UpdateResponse>
            ) {
                when(response.code()){
                    200 -> {
                        closetUpdateView.onClosetUpdateSuccess()
                    }
                    else -> closetUpdateView.onClosetUpdateFailure()
                }

            }
            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                closetUpdateView.onClosetUpdateFailure()
            }
        })
    }
}