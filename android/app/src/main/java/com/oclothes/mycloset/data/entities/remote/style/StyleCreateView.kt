package com.oclothes.mycloset.data.entities.remote.style

import com.oclothes.mycloset.data.entities.Style

interface StyleCreateView {
    fun onCreateSuccess(id : Int, url : String)
    fun onCreateFailure()
}