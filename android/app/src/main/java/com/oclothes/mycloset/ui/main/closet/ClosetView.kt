package com.oclothes.mycloset.ui.main.closet

interface ClosetView {
    fun onGetClosetsSuccess(data: ArrayList<com.oclothes.mycloset.data.entities.Closet>)
    fun onGetClosetsFailure(code: Int, message: String)
}