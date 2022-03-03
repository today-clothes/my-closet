package com.oclothes.mycloset.ui.main.closet.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.oclothes.mycloset.ApplicationClass.Companion.BASE_URL
import com.oclothes.mycloset.data.entities.remote.domain.Closet
import com.oclothes.mycloset.databinding.ItemClosetBinding
import com.oclothes.mycloset.ui.main.closet.ClosetFragment
import com.oclothes.mycloset.utils.getJwt

class ClosetListRVAdapter(private val closetList : ArrayList<Closet>, val f : ClosetFragment) : RecyclerView.Adapter<ClosetListRVAdapter.ViewHolder>(){


    // 클릭 인터페이스를 정의
    interface MyItemClickListener{
        fun onItemClick(closet: Closet)
        fun onItemLongClick(closet : Closet)
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
        holder.itemView.setOnLongClickListener {
            mItemClickListener.onItemLongClick(closetList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return closetList.size
    }

    inner class ViewHolder(val binding: ItemClosetBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(closet: Closet){
            if(closet.thumbnail == null){
                binding.closetItemNameTv.setTextColor(Color.BLACK)
                binding.closetItemNameTv.text = closet.name
                binding.closetItemWhenEmptyTv.visibility = View.VISIBLE
            }else{
                binding.closetItemWhenEmptyTv.visibility = View.GONE
                binding.closetItemNameTv.text = closet.name
            }
            val glideUrl = GlideUrl("${BASE_URL}clothes/images/${closet.thumbnail}", LazyHeaders.Builder()
                .addHeader("Authorization", getJwt()!!)
                .build())
            Glide.with(f).load(glideUrl).into(binding.closetItemImageIv)
        }
    }
}