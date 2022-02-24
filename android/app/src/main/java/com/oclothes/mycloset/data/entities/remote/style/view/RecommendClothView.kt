package com.oclothes.mycloset.data.entities.remote.style.view

import com.oclothes.mycloset.data.entities.remote.domain.Style

interface RecommendClothView {
    fun onRecommendSuccess(styles: ArrayList<Style>)
    fun onRecommendFailure(message : String)
}