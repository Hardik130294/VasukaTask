package com.hardik.challengearraylistfilter.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hardik.challengearraylistfilter.R
import com.hardik.challengearraylistfilter.data.remote.dto.Index
import com.hardik.challengearraylistfilter.databinding.ItemRecFilterBinding
import com.hardik.challengearraylistfilter.databinding.ItemRecIndexBinding
import com.hardik.challengearraylistfilter.presentation.model.FilterData


class FilterAdapter(var filterList: List<FilterData>): RecyclerView.Adapter<FilterAdapter.MainViewHolder>() {

    inner class MainViewHolder(val binding: ItemRecFilterBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemRecFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val filter:FilterData = filterList[position]
        var activatedFilter = ""
        for (af in filter.subFilters) {
            if (af.isSelected) {
                val newString = af.name
                activatedFilter += if (activatedFilter.isNotEmpty() && activatedFilter.last() != '/') {
                    "/$newString"
                } else {
                    newString
                }
            }
        }

        holder.itemView.alpha = 0f
        holder.itemView.animate().alpha(1f).setDuration(200).start()
        holder.itemView.apply {
            holder.binding.itemRecFilterLl.apply {
                background = if (filter.isSelected){
                    ContextCompat.getDrawable(context, R.drawable.item_background_on)
                }else{
                    ContextCompat.getDrawable(context, R.drawable.item_background)

                }
            }
            holder.binding.itemRecFilterTvLabel.apply {
                ellipsize = android.text.TextUtils.TruncateAt.MARQUEE
                marqueeRepeatLimit = -1
                setHorizontallyScrolling(true)
                isSingleLine = true
                isSelected = true
                text = filter.filterName
            }
            holder.binding.itemRecFilterTv.apply {
                isSingleLine = true
                /// Replace "0" with "no" and "1" with "yes"
                activatedFilter = activatedFilter.replace("0", "No").replace("1", "Yes")
                text = " : ${activatedFilter.ifEmpty { "All" }}"
            }

            setOnClickListener {
                onItemClickListener?.let {
                    it(filter,position)
                }
            }
        }
    }

    private var onItemClickListener: ((FilterData,Int) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: ((FilterData,Int) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFilterList(it: List<FilterData>) {
        filterList = it
        notifyDataSetChanged()
    }

}