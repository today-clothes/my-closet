package com.oclothes.mycloset.data.entities.remote.closet.view

import com.oclothes.mycloset.data.entities.remote.domain.Closet

interface ClosetView {
    fun onGetClosetsSuccess(data: ArrayList<Closet>, hasNext : Boolean)
    fun onGetClosetsFailure(code: Int, message: String)
}