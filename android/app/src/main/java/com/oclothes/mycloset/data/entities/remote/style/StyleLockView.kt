package com.oclothes.mycloset.data.entities.remote.style

import com.oclothes.mycloset.data.entities.Style

interface StyleLockView {
    fun onSuccess()
    fun onFailure()
}