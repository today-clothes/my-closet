package com.oclothes.mycloset.ui.signup.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.remote.domain.Tag
import com.oclothes.mycloset.databinding.ItemStyleInfoTagBinding
import com.oclothes.mycloset.ui.signup.StyleInfoFragment

class InfoStyleRvAdapter(private val tagList : ArrayList<Tag>, val f : StyleInfoFragment) : RecyclerView.Adapter<InfoStyleRvAdapter.ViewHolder>(){

    private val selectedTag = ArrayList<Tag>()

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

    override fun getItemCount() = tagList.size

    fun getSelectedTags() : ArrayList<Tag> {
        return selectedTag
    }


    inner class ViewHolder(val binding: ItemStyleInfoTagBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(tag : Tag){
            binding.styleInfoItemTagNameTv.text = tag.name

            binding.styleInfoTagBtnCl.setOnClickListener {
                if(selectedTag.contains(tag)){
                    selectedTag.remove(tag)
                    binding.styleInfoTagBtnCl.setBackgroundResource(R.drawable.style_info_tag_background_inactive)
                    binding.styleInfoItemTagNameTv.setTextColor(Color.BLACK)

                }else{
                    selectedTag.add(tag)
                    binding.styleInfoTagBtnCl.setBackgroundResource(R.drawable.style_info_tag_background_active)
                    binding.styleInfoItemTagNameTv.setTextColor(Color.WHITE)
                }
                if(selectedTag.size > 1) {
                    f.getBinding().styleInfoNextBtnTv.setBackgroundResource(R.drawable.basic_theme_button_active)
                    f.getBinding().styleInfoNextBtnTv.isEnabled = true
                }else{
                    f.getBinding().styleInfoNextBtnTv.setBackgroundResource(R.drawable.basic_theme_button_inactive)
                    f.getBinding().styleInfoNextBtnTv.isEnabled = false
                }
            }
        }
    }

}