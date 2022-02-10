package com.oclothes.mycloset.data.entities.remote.closet

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oclothes.mycloset.ApplicationClass
import com.oclothes.mycloset.ApplicationClass.Companion.TAG
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.data.entities.ErrorBody
import com.oclothes.mycloset.ui.main.closet.ClosetCreateView
import com.oclothes.mycloset.ui.main.closet.ClosetView
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
}