package com.oclothes.mycloset.data.entities.remote.style.view

import com.oclothes.mycloset.data.entities.remote.domain.StyleInfo

interface StyleInfoView {
    fun onInfoSuccess(styleInfo : StyleInfo)
    fun onInfoFailure()
}