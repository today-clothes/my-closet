package com.oclothes.mycloset.data.entities.remote.style

import com.oclothes.mycloset.data.entities.Style

interface StyleInfoView {
    fun onSuccess(style : Style)
    fun onFailure()
}