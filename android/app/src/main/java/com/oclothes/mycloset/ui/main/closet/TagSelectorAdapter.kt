package com.oclothes.mycloset.ui.main.closet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oclothes.mycloset.R
import com.oclothes.mycloset.data.entities.Tag


class TagSelectorAdapter(private val context: Context, private val dataList : ArrayList<Tag>) : RecyclerView.Adapter<TagSelectorAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TagSelectorAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.closet_tag_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagSelectorAdapter.ItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ItemViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private val tagText = itemView.findViewById<TextView>(R.id.tag_item_name)

        fun bind(tag : Tag, context: Context){
            tagText.text = ""
        }

    }

}