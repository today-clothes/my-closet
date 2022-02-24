package com.oclothes.mycloset.data.entities.remote.style.view

interface StyleCreateView {
    fun onCreateSuccess(closetId : Int, clothesId : Int, url : String)
    fun onCreateFailure(message : String)
}