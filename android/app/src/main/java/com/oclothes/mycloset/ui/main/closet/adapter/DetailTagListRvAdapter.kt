package com.oclothes.mycloset.ui.main.closet.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.databinding.ItemSingleClosetTagBinding
import com.oclothes.mycloset.ui.main.closet.DetailFragment

class DetailTagListRvAdapter (val f : DetailFragment, private val tagList : ArrayList<Tag>) : RecyclerView.Adapter<DetailTagListRvAdapter.ViewHolder>() {

    private var touchable = true
    private val selectedTag : ArrayList<Tag> = ArrayList<Tag>()
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): DetailTagListRvAdapter.ViewHolder {
        val binding: ItemSingleClosetTagBinding = ItemSingleClosetTagBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailTagListRvAdapter.ViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount() = tagList.size

    fun setSelectedTag(selectedTag : ArrayList<Tag>){
        this.selectedTag.clear()
        this.selectedTag.addAll(selectedTag)
        f.getBinding().detailSecondFilterCountTv.text = selectedTag.size.toString()
    }

    fun getSelectedTag() : ArrayList<Tag> = selectedTag

    fun setTouchable(state: Boolean) {
        touchable = state
    }


    inner class ViewHolder(val binding: ItemSingleClosetTagBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tag : Tag){
            binding.singleClosetTagNameTv.text = tag.name
            if(selectedTag.contains(tag)){
                binding.singleClosetTagBtnCl.setBackgroundResource(R.drawable.style_info_tag_background_active)
                binding.singleClosetTagNameTv.setTextColor(Color.WHITE)
            }else{
                binding.singleClosetTagBtnCl.setBackgroundResource(R.drawable.basic_theme_button_inactive_stroke)
                binding.singleClosetTagNameTv.setTextColor(Color.BLACK)
            }


            binding.singleClosetTagBtnCl.setOnClickListener {
                if(touchable) {
                    if (selectedTag.contains(tag)) {
                        selectedTag.remove(tag)
                        tagList.remove(tag)
                        binding.singleClosetTagBtnCl.setBackgroundResource(R.drawable.basic_theme_button_inactive_stroke)
                        binding.singleClosetTagNameTv.setTextColor(Color.BLACK)
                        notifyDataSetChanged()

                    } else {
                        selectedTag.add(tag)
                        tagList.add(tag)
                        binding.singleClosetTagBtnCl.setBackgroundResource(R.drawable.style_info_tag_background_active)
                        binding.singleClosetTagNameTv.setTextColor(Color.WHITE)
                        notifyDataSetChanged()

                    }
                    f.getBinding().detailSecondFilterCountTv.text = selectedTag.size.toString()
                    f.updateList(selectedTag)
                }
            }
        }
    }

}

