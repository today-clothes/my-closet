package com.oclothes.mycloset.data.entities.remote.style

import com.oclothes.mycloset.data.entities.Style

interface StyleDeleteView {
    fun onDeleteSuccess()
    fun onDeleteFailure()
}