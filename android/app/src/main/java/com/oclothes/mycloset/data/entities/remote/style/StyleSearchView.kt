package com.oclothes.mycloset.data.entities.remote.style

import com.oclothes.mycloset.data.entities.Style

interface StyleSearchView {
    fun onSearchSuccess(styles: ArrayList<Style>, b: Boolean)
    fun onSearchFailure()
}