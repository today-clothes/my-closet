package com.oclothes.mycloset.ui.main.search.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.remote.domain.Tag
import com.oclothes.mycloset.databinding.ItemSingleClosetTagBinding

class SearchDetailTagListRvAdapter (val fragment : Fragment, private val tagList : ArrayList<Tag>) : RecyclerView.Adapter<SearchDetailTagListRvAdapter.ViewHolder>() {

    private val selectedTag : ArrayList<Tag> = ArrayList<Tag>()
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchDetailTagListRvAdapter.ViewHolder {
        val binding: ItemSingleClosetTagBinding = ItemSingleClosetTagBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchDetailTagListRvAdapter.ViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount() = tagList.size

    inner class ViewHolder(val binding: ItemSingleClosetTagBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            binding.singleClosetTagNameTv.text = tag.name
            binding.singleClosetTagBtnCl.setBackgroundResource(R.drawable.style_info_tag_background_active)
            binding.singleClosetTagNameTv.setTextColor(Color.WHITE)
        }
    }
}

