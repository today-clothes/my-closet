package com.oclothes.mycloset.ui.signup

import com.oclothes.mycloset.data.entities.remote.domain.Tag
import java.util.ArrayList

interface TagView {
    fun onGetTagsSuccess(
        eventTags: ArrayList<Tag>,
        moodTags: ArrayList<Tag>,
        seasonTags: ArrayList<Tag>
    )
    fun onGetTagsFailure(code: Int, message: String)
}