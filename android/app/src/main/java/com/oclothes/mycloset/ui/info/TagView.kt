package com.oclothes.mycloset.ui.info

import com.oclothes.mycloset.data.entities.Tag
import java.util.ArrayList

interface TagView {
    fun onGetTagsSuccess(
        eventTags: ArrayList<Tag>,
        moodTags: ArrayList<Tag>,
        seasonTags: ArrayList<Tag>
    )
    fun onGetClosetsFailure(code: Int, message: String)
}