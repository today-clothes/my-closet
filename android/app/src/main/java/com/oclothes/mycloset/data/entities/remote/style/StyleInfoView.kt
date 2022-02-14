package com.oclothes.mycloset.data.entities.remote.style

import com.oclothes.mycloset.data.entities.StyleInfo

interface StyleInfoView {
    fun onInfoSuccess(styleInfo : StyleInfo)
    fun onInfoFailure()
}