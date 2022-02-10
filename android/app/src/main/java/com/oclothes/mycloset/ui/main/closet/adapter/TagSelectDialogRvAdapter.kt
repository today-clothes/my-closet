package com.oclothes.mycloset.ui.main.closet.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.databinding.ItemTagSelectBinding

class TagSelectDialogRvAdapter(private val tagList : ArrayList<Tag>, private val selectedTag : ArrayList<Tag>, val d : Dialog) : RecyclerView.Adapter<TagSelectDialogRvAdapter.ViewHolder>(){


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemTagSelectBinding = ItemTagSelectBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    fun reset(){
        selectedTag.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTagSelectBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(tag : Tag){
            binding.tagSelectItemTagNameTv.text = tag.name

            if(selectedTag.contains(tag)){
                binding.tagSelectItemTagSelectBtnTv.setBackgroundResource(R.drawable.tag_select_item_circle_background_selected)

            }else{
                binding.tagSelectItemTagSelectBtnTv.setBackgroundResource(R.drawable.tag_select_item_circle_background_unselected)
            }


            binding.tagSelectBackgroundCl.setOnClickListener {
                if(selectedTag.contains(tag)){
                    selectedTag.remove(tag)
                    binding.tagSelectItemTagSelectBtnTv.setBackgroundResource(R.drawable.tag_select_item_circle_background_unselected)

                }else{
                    selectedTag.add(tag)
                    binding.tagSelectItemTagSelectBtnTv.setBackgroundResource(R.drawable.tag_select_item_circle_background_selected)
                }

                if(selectedTag.size >= 1) {
                    d.findViewById<TextView>(R.id.tag_select_apply_btn1).visibility = View.GONE
                    d.findViewById<TextView>(R.id.tag_select_apply_btn2).visibility = View.VISIBLE

                }else{
                    d.findViewById<TextView>(R.id.tag_select_apply_btn1).visibility = View.VISIBLE
                    d.findViewById<TextView>(R.id.tag_select_apply_btn2).visibility = View.GONE
                }
            }
        }
    }
}