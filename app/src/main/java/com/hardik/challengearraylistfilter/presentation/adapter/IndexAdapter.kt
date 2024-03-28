package com.hardik.challengearraylistfilter.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hardik.challengearraylistfilter.data.remote.dto.Index
import com.hardik.challengearraylistfilter.databinding.ItemRecIndexBinding


class IndexAdapter(var indexList: ArrayList<Index>): RecyclerView.Adapter<IndexAdapter.MainViewHolder>() {

    inner class MainViewHolder(val binding: ItemRecIndexBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemRecIndexBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return indexList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val index:Index = indexList[position]

//        Log.e("TAG", "onBindViewHolder: ", )
        holder.itemView.alpha = 0f
        holder.itemView.animate().alpha(1f).setDuration(200).start()
        holder.itemView.apply {
            holder.binding.itemRecViewAllTvLabel.apply {
                ellipsize = android.text.TextUtils.TruncateAt.MARQUEE
                marqueeRepeatLimit = -1
                setHorizontallyScrolling(true)
                isSingleLine = true
                isSelected = true
                text = index.title
            }
            holder.binding.itemRecViewAllTvDec.apply {
                maxLines = 1000
                text = index.educator
            }
            Glide
                .with(context)
                .load("http://d2xkd1fof6iiv9.cloudfront.net/images/courses/${index.id}/169_820.jpg",)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.binding.itemRecViewAllImgPoster)

            setOnClickListener {
                onItemClickListener?.let {
                    it(
                        Index(
                            id = index.id?: 1,
                            title = index.title?: "",
                            educator = index.educator?: "",
                        )
                    )
                }
            }
        }
    }

    private var onItemClickListener: ((Index) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: ((Index) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateIndexList(it: ArrayList<Index>) {
        indexList = it
        notifyDataSetChanged()
    }

}