package com.oclothes.mycloset.ui.main.closet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oclothes.mycloset.data.entities.Style
import com.oclothes.mycloset.data.entities.Tag
import com.oclothes.mycloset.data.entities.remote.style.StyleDeleteView
import com.oclothes.mycloset.data.entities.remote.style.StyleService
import com.oclothes.mycloset.databinding.ItemSingleClosetClothBinding
import com.oclothes.mycloset.ui.main.closet.SingleClosetFragment
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

class SingleClosetStyleListRVAdapter (private val fragment : SingleClosetFragment, private val styleList : ArrayList<Style>) : RecyclerView.Adapter<SingleClosetStyleListRVAdapter.ViewHolder>(),
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
            mItemClickListener.onItemLongClick(styleList[position])
            initEditMode(position)
            setBackground(position, holder)
            true
        }
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(styleList[position], position)
            if(editMode){
                styleList[position].isSelected = !styleList[position].isSelected
            }
            setBackground(position, holder)
            notifyItemChanged(position)
        }
    }

    fun getEditMode() = editMode

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

    fun initEditMode(position: Int) : Boolean{
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

    fun updateByTag(selectedTag: HashMap<String, Tag>) {
        //서버에서 리스트 받아와서 업데이트 하는걸로!!
    }

    inner class ViewHolder(val binding: ItemSingleClosetClothBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(style: Style){
            binding.singleClosetClothNameTv.text = style.closetId.toString() //임시임 원래는 name으로 해야하는데 api에서 미구현

            if(style.locked){
                binding.singleClosetLockIconIv.visibility = View.VISIBLE
            }else{
                binding.singleClosetLockIconIv.visibility = View.GONE
            }


            Glide.with(binding.singleClosetClothImageIv)
                .load(style.imgUrl)
                .into(binding.singleClosetClothImageIv)
        }
    }

    override fun onSuccess() {
        notifyDataSetChanged()
    }

    override fun onFailure() {

    }
}