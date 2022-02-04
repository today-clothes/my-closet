package com.oclothes.mycloset.ui.main.closet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.databinding.ItemSingleClosetClothBinding
import java.util.*
import kotlin.collections.ArrayList

class SingleClosetStyleListRVAdapter (private val styleList : ArrayList<Style>) : RecyclerView.Adapter<SingleClosetStyleListRVAdapter.ViewHolder>(){
    private var editMode = false
    private val editList = ArrayList<Int>()
    private val viewList = ArrayList<ItemSingleClosetClothBinding>()

    interface MyItemClickListener{
        fun onItemClick(style: Style, position : Int)
        fun onRemoveStyle(position: Int)
        fun onItemLongClick(style : Style)
    }

    private lateinit var mItemClickListener: SingleClosetStyleListRVAdapter.MyItemClickListener

    // 클릭 리스너 등록 메서드 (메인 액티비티에서 inner Class로 호출)
    fun setMyItemClickListener(itemClickListener: SingleClosetStyleListRVAdapter.MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SingleClosetStyleListRVAdapter.ViewHolder {
        val binding: ItemSingleClosetClothBinding = ItemSingleClosetClothBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SingleClosetStyleListRVAdapter.ViewHolder, position: Int) {
        holder.bind(styleList[position])

        if (editMode) {
            if(styleList[position].isSelected){
                holder.binding.singleClosetClothEditBackgroundTv.visibility = View.VISIBLE
            }else{
                holder.binding.singleClosetClothEditBackgroundTv.visibility = View.GONE
            }
        }

        holder.itemView.setOnLongClickListener {
            mItemClickListener.onItemLongClick(styleList[position])
            if(!editMode){
                editMode = true
                styleList[position].isSelected = true
                notifyItemChanged(position)
            }else{
                finishEditMode()
            }
            true
        }
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(styleList[position], position)
            if(editMode){
                styleList[position].isSelected = !styleList[position].isSelected
            }
            notifyItemChanged(position)
        }
    }

    private fun deleteSelectedItem(){
        for (style in styleList) {
            if(style.isSelected){
                //아무래도 여기서 삭제 API를 호출해 줘야 할 것 같다...
                styleList.remove(style)
            }
            editMode = false
        }
    }

    fun finishEditMode() {
        editMode = false
        for (style in styleList) {
            style.isSelected = false
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return styleList.size
    }

    fun updateByTag(selectedTag: HashMap<String, Tag>) {
        //서버에서 리스트 받아와서 업데이트 하는걸로!!
    }

    inner class ViewHolder(val binding: ItemSingleClosetClothBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(style: Style){
            binding.singleClosetClothNameTv.text = style.name
            Glide.with(binding.singleClosetClothImageIv)
                .load(style.imageSource)
                .into(binding.singleClosetClothImageIv)
        }
    }
}