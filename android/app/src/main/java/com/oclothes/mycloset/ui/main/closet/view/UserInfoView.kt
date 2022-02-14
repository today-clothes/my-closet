package com.oclothes.mycloset.ui.main.closet.view

import com.oclothes.mycloset.data.entities.User

interface UserInfoView {
    fun onGetUserInfoSuccess(user: User)
    fun onGetUserInfoFailure(message : String)
}