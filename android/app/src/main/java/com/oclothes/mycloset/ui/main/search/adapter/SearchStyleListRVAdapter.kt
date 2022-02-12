package com.oclothes.mycloset.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.databinding.ItemSearchedItemBinding
import com.oclothes.mycloset.ui.main.search.SearchFragment
import java.util.concurrent.CopyOnWriteArrayList

class SearchStyleListRVAdapter (private val fragment : SearchFragment, private val styleList : CopyOnWriteArrayList<Style>) : RecyclerView.Adapter<SearchStyleListRVAdapter.ViewHolder>(){
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

        }
    }

    override fun getItemCount(): Int {
        return styleList.size
    }

    inner class ViewHolder(val binding: ItemSearchedItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(style: Style){
            Glide.with(fragment.getBinding().searchMainRv)
                .load(style.imgUrl)
                .into(binding.searchItemClothImageIv)
        }
    }
}