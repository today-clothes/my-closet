package com.oclothes.mycloset.ui.main.closet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.databinding.ItemSingleClosetClothBinding

class SingleClosetStyleListRVAdapter (private val styleList : ArrayList<Style>) : RecyclerView.Adapter<SingleClosetStyleListRVAdapter.ViewHolder>(){

    interface MyItemClickListener{
        fun onItemClick(style: Style)
        fun onRemoveStyle(position: Int)
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
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(styleList[position])
        }
    }

    override fun getItemCount(): Int {
        return styleList.size
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