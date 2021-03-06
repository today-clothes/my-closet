package com.oclothes.mycloset.data.entities.remote.user.view

import com.oclothes.mycloset.data.entities.remote.domain.User

interface UserInfoView {
    fun onGetUserInfoSuccess(user: User)
    fun onGetUserInfoFailure(message : String)
}