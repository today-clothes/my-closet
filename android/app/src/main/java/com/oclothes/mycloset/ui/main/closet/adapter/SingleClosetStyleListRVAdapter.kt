package com.oclothes.mycloset.ui.main.closet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.oclothes.mycloset.data.entities.remote.domain.Style
import com.oclothes.mycloset.data.entities.remote.domain.Tag
import com.oclothes.mycloset.data.entities.remote.style.view.StyleDeleteView
import com.oclothes.mycloset.data.entities.remote.style.service.StyleService
import com.oclothes.mycloset.databinding.ItemSingleClosetClothBinding
import com.oclothes.mycloset.ui.main.MainActivity
import com.oclothes.mycloset.ui.main.closet.ClosetMainFragment
import com.oclothes.mycloset.utils.getJwt
import java.util.*
import kotlin.collections.ArrayList

class SingleClosetStyleListRVAdapter (private val f : ClosetMainFragment, private val styleList : ArrayList<Style>) : RecyclerView.Adapter<SingleClosetStyleListRVAdapter.ViewHolder>(),
    StyleDeleteView {
    private var editMode = false
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
        viewList.add(binding)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SingleClosetStyleListRVAdapter.ViewHolder, position: Int) {
        holder.bind(styleList[position])
        setBackground(position, holder)

        holder.itemView.setOnLongClickListener {
            initEditMode(position)
            setBackground(position, holder)
            mItemClickListener.onItemLongClick(styleList[position])
            true
        }
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(styleList[position], position)
            if(editMode){
                styleList[position].isSelected = !styleList[position].isSelected
            }
            if(!editMode){
                f.openStyle(styleList[position].clothesId)
            }
            setBackground(position, holder)
            notifyItemChanged(position)
        }
    }

    fun getSelectedCount() : Int{
        var count = 0
        for(style in styleList){
            if(style.isSelected)
                count++
        }
        return count
    }

    fun getEditMode() = editMode

    fun setEditMode(b : Boolean){
        editMode = b
    }
    private fun setBackground(
        position: Int,
        holder: ViewHolder
    ) {
        if (editMode) {
            if (styleList[position].isSelected) {
                holder.binding.singleClosetClothEditBackgroundTv.visibility = View.VISIBLE
                holder.binding.singleClosetClothSelectedBtnIv.visibility = View.VISIBLE
            } else {
                holder.binding.singleClosetClothEditBackgroundTv.visibility = View.GONE
                holder.binding.singleClosetClothSelectedBtnIv.visibility = View.GONE
            }
        }
    }

    private fun initEditMode(position: Int) : Boolean{
        if (!editMode) {
            editMode = true
            if(position != -1) {
                styleList[position].isSelected = true
                notifyItemChanged(position)
            }
        } else {
            finishEditMode()
        }
        return editMode
    }

    fun deleteSelectedItem(){
        for (style in styleList) {
            if(style.isSelected){
                StyleService.deleteCloth(this, style.clothesId)
            }
        }
        finishEditMode()
    }

    fun finishEditMode() {
        editMode = false
        for (style in styleList) {
            style.isSelected = false
        }
        for (itemSingleClosetClothBinding in viewList) {
            itemSingleClosetClothBinding.singleClosetClothEditBackgroundTv.visibility = View.GONE
            itemSingleClosetClothBinding.singleClosetClothSelectedBtnIv.visibility = View.GONE
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return styleList.size
    }

    inner class ViewHolder(val binding: ItemSingleClosetClothBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(style: Style){
            binding.singleClosetClothNameTv.text = style.styleTitle
            binding.singleClosetClothClosetTextBodyTv.text = style.updatedAt.substring(0, 10)
            if(style.locked){
                binding.singleClosetLockIconIv.visibility = View.VISIBLE
            }else{
                binding.singleClosetLockIconIv.visibility = View.GONE
            }

            val glideUrl = GlideUrl("http://10.0.2.2:8080/clothes/images/${style.imgUrl}", LazyHeaders.Builder()
                .addHeader("Authorization", getJwt()!!)
                .build())
            Glide.with(f).load(glideUrl).into(binding.singleClosetClothImageIv)
        }
    }

    override fun onDeleteSuccess() {
        f.openCloset((f.requireActivity() as MainActivity).currentCloset!!)
        notifyDataSetChanged()
    }

    override fun onDeleteFailure() {

    }
}