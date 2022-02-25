package com.oclothes.mycloset.data.entities.remote.closet.view

import com.oclothes.mycloset.data.entities.remote.domain.Closet

interface ClosetView {
    fun onGetClosetsSuccess(data: ArrayList<Closet>)
    fun onGetClosetsFailure(code: Int, message: String)
}