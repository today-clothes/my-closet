package com.oclothes.mycloset.ui.main.closet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oclothes.mycloset.data.entities.Closet
import com.oclothes.mycloset.databinding.ItemClosetBinding

class ClosetListRVAdapter(private val closetList : ArrayList<Closet>) : RecyclerView.Adapter<ClosetListRVAdapter.ViewHolder>(){


    // 클릭 인터페이스를 정의
    interface MyItemClickListener{
        fun onItemClick(closet: Closet)
        fun onRemoveAlbum(position: Int)
    }

    // 클릭 리스너 선언
    private lateinit var mItemClickListener: MyItemClickListener

    // 클릭 리스너 등록 메서드 (메인 액티비티에서 inner Class로 호출)
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ClosetListRVAdapter.ViewHolder {
        val binding: ItemClosetBinding = ItemClosetBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClosetListRVAdapter.ViewHolder, position: Int) {
        holder.bind(closetList[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(closetList[position])
        }
    }

    override fun getItemCount(): Int {
        return closetList.size
    }

    inner class ViewHolder(val binding: ItemClosetBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(closet: Closet){
            binding.closetItemNameTv.text = closet.name
            Glide.with(binding.closetItemCv)
                .load(closet.imageSource)
                .into(binding.closetItemImageIv)
        }
    }
}