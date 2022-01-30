package com.oclothes.mycloset.ui.main.closet.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.databinding.ItemSingleClosetTagBinding

class SingleClosetTagListRvAdapter (private val tagList : ArrayList<Tag>) : RecyclerView.Adapter<SingleClosetTagListRvAdapter.ViewHolder>() {

    private val selectedTag : HashMap<String, Tag> = HashMap<String, Tag>()
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SingleClosetTagListRvAdapter.ViewHolder {
        val binding: ItemSingleClosetTagBinding = ItemSingleClosetTagBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: SingleClosetTagListRvAdapter.ViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount() = tagList.size

    inner class ViewHolder(val binding: ItemSingleClosetTagBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(tag : Tag){
            binding.singleClosetTagNameTv.text = tag.name

            binding.singleClosetTagBtnCl.setOnClickListener {
                if(selectedTag.contains(tag.name)){
                    selectedTag.remove(tag.name)
                    binding.singleClosetTagBtnCl.setBackgroundResource(R.drawable.basic_theme_button_inactive_stroke)
                    binding.singleClosetTagNameTv.setTextColor(Color.BLACK)

                }else{
                    selectedTag[tag.name] = tag
                    binding.singleClosetTagBtnCl.setBackgroundResource(R.drawable.style_info_tag_background_active)
                    binding.singleClosetTagNameTv.setTextColor(Color.WHITE)
                }
            }
        }
    }

}

