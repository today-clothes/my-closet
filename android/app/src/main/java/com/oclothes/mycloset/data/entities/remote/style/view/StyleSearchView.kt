package com.oclothes.mycloset.data.entities.remote.style.view

import com.oclothes.mycloset.data.entities.remote.domain.Style

interface StyleSearchView {
    fun onSearchSuccess(styles: ArrayList<Style>, hasNext: Boolean)
    fun onSearchFailure(message : String)
}