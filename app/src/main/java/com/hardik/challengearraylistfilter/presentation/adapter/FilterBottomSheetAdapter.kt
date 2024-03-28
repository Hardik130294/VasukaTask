package com.hardik.challengearraylistfilter.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hardik.challengearraylistfilter.R
import com.hardik.challengearraylistfilter.data.remote.dto.Index
import com.hardik.challengearraylistfilter.databinding.ItemRecFilterBinding
import com.hardik.challengearraylistfilter.databinding.ItemRecFilterChildBinding
import com.hardik.challengearraylistfilter.databinding.ItemRecIndexBinding
import com.hardik.challengearraylistfilter.presentation.model.FilterData
import com.hardik.challengearraylistfilter.presentation.model.SubFilter


class FilterBottomSheetAdapter(var filterList: List<SubFilter>) :
    RecyclerView.Adapter<FilterBottomSheetAdapter.MainViewHolder>() {

    inner class MainViewHolder(val binding: ItemRecFilterChildBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemRecFilterChildBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val filter: SubFilter = filterList[position]

        holder.itemView.alpha = 0f
        holder.itemView.animate().alpha(1f).setDuration(200).start()
        holder.itemView.apply {
            holder.binding.itemRecFilterTvLabel.apply {
                ellipsize = android.text.TextUtils.TruncateAt.MARQUEE
                marqueeRepeatLimit = -1
                setHorizontallyScrolling(true)
                isSingleLine = true
                isSelected = true
                /// Replace "0" with "no" and "1" with "yes"
                text = filter.name.replace("0", "no").replace("1", "yes")
            }
            holder.binding.itemRecFilterViewIcon.apply {
                background = if (filter.isSelected) {
                    ContextCompat.getDrawable(this.context, R.drawable.round_done_24)
                } else {
                    null
                }
            }

            setOnClickListener {
                onItemClickListener?.let {
                    it(filter, position)
                }
                holder.binding.itemRecFilterViewIcon.background = if (filter.isSelected) {
                    ContextCompat.getDrawable(this.context, R.drawable.round_done_24)
                } else {
                    null
                }
            }
        }
    }

    private var onItemClickListener: ((SubFilter, Int) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: ((SubFilter, Int) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFilterList(it: List<SubFilter>) {
        filterList = it
        notifyDataSetChanged()
    }

}