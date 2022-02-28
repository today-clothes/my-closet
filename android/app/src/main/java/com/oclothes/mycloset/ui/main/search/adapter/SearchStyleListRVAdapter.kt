package com.oclothes.mycloset.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.oclothes.mycloset.data.entities.remote.domain.Style
import com.oclothes.mycloset.databinding.ItemSearchedItemBinding
import com.oclothes.mycloset.ui.main.search.SearchFragment
import com.oclothes.mycloset.utils.getJwt

class SearchStyleListRVAdapter (private val fragment : SearchFragment, val styleList : ArrayList<Style>) : RecyclerView.Adapter<SearchStyleListRVAdapter.ViewHolder>(){
    interface MyItemClickListener{
        fun onItemClick(style: Style, position : Int)
    }

    private lateinit var mItemClickListener: SearchStyleListRVAdapter.MyItemClickListener

    // 클릭 리스너 등록 메서드 (메인 액티비티에서 inner Class로 호출)
    fun setMyItemClickListener(itemClickListener: SearchStyleListRVAdapter.MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchStyleListRVAdapter.ViewHolder {
        val binding: ItemSearchedItemBinding = ItemSearchedItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchStyleListRVAdapter.ViewHolder, position: Int) {
        holder.bind(styleList[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(styleList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return styleList.size
    }

    inner class ViewHolder(val binding: ItemSearchedItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(style: Style){
            val glideUrl = GlideUrl("http://10.0.2.2:8080/clothes/images/${style.imgUrl}", LazyHeaders.Builder()
                .addHeader("Authorization", getJwt()!!)
                .build())
            Glide.with(fragment).load(glideUrl).into(binding.searchItemClothImageIv)

        }
    }
}