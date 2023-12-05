package com.hardik.vasukatask.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hardik.vasukatask.R;
import com.hardik.vasukatask.databinding.ItemRecFilterBinding;
import com.hardik.vasukatask.databinding.ItemRecParentBinding;
import com.hardik.vasukatask.model.FilterItem;
import com.hardik.vasukatask.model.Index;
import com.hardik.vasukatask.model.ResultModel;
import com.hardik.vasukatask.model.Smart;
import com.hardik.vasukatask.model.User;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {
    private static String TAG = FilterAdapter.class.getSimpleName();

    List<FilterItem> filterItemList;

    OnItemClickListener onItemClickListener;

    public FilterAdapter(List<FilterItem> filterItemList, OnItemClickListener onItemClickListener) {
        Log.i(TAG, "FilterAdapter: ");
        this.filterItemList = filterItemList;
        this.onItemClickListener = onItemClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<FilterItem> filterItemList) {
        this.filterItemList = filterItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecFilterBinding binding = ItemRecFilterBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FilterItem filterItem = filterItemList.get(position);

        holder.label.setText(filterItem.getName());

        if (filterItem.isSelected()) {
            holder.linearLayout.setBackground(holder.linearLayout.getContext().getDrawable(R.color.purple_200));
        } else {
            holder.linearLayout.setBackground(holder.linearLayout.getContext().getDrawable(R.color.white));
        }

        holder.itemView.setOnClickListener(view -> {
            Log.i(TAG, "onBindViewHolder: "+filterItem.getName());
            holder.linearLayout.setBackground(filterItem.isSelected() ? holder.linearLayout.getContext().getDrawable(R.color.purple_200) : holder.linearLayout.getContext().getDrawable(R.color.white));
            filterItem.setSelected(filterItem.isSelected() ? false : true);
            notifyItemChanged(position);
            boolean isBackgroundColor = ((ColorDrawable) holder.linearLayout.getBackground()).getColor() == Color.WHITE;
            if (isBackgroundColor) {
                onItemClickListener.onItemClick(position, filterItem.getValue(), isSelected -> {
                    filterItem.setSelected(isSelected);
                    notifyItemChanged(position);
                });
            } else {
                onItemClickListener.onItemClick(position, filterItem.getValue(), isSelected -> {
                    filterItem.setSelected(isSelected);
                    notifyItemChanged(position);
                });
            }
            if (holder.label.getText().equals("Clear")) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return filterItemList.size() == 0 ? 0 : filterItemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull ItemRecFilterBinding binding) {
            super(binding.getRoot());
            label = binding.itemRecFilterTvLabel;
            linearLayout = binding.itemRecFilterLl;
        }
    }
}
