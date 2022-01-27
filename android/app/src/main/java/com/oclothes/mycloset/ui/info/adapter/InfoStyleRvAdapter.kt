package com.oclothes.mycloset.ui.info.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.databinding.ItemStyleInfoTagBinding

class InfoStyleRvAdapter(private val tagList : ArrayList<Tag>) : RecyclerView.Adapter<InfoStyleRvAdapter.ViewHolder>(){

    private val selectedTag : HashMap<String, Tag> = HashMap<String, Tag>()

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): InfoStyleRvAdapter.ViewHolder {
        val binding: ItemStyleInfoTagBinding = ItemStyleInfoTagBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoStyleRvAdapter.ViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    fun addItems(albums: ArrayList<Tag>) {
        tagList.clear()
        tagList.addAll(albums)
        notifyDataSetChanged()
    }

    fun addItem(album: Tag) {
        tagList.add(album)
        notifyDataSetChanged()
    }

    fun removeItems() {
        tagList.clear()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        tagList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount() = tagList.size

    fun getSelectedTags() : HashMap<String, Tag> {
        return selectedTag
    }


    inner class ViewHolder(val binding: ItemStyleInfoTagBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(tag : Tag){
            binding.styleInfoItemTagNameTv.text = tag.name

            binding.styleInfoTagBtnCl.setOnClickListener {
                if(selectedTag.contains(tag.name)){
                    selectedTag.remove(tag.name)
                    binding.styleInfoTagBtnCl.setBackgroundResource(R.drawable.style_info_tag_background_inactive)
                    binding.styleInfoItemTagNameTv.setTextColor(Color.BLACK)

                }else{
                    selectedTag[tag.name] = tag
                    binding.styleInfoTagBtnCl.setBackgroundResource(R.drawable.style_info_tag_background_active)
                    binding.styleInfoItemTagNameTv.setTextColor(Color.WHITE)
                }
            }
        }
    }

}