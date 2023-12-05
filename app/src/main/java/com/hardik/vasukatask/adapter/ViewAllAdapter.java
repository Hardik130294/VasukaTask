package com.hardik.vasukatask.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hardik.vasukatask.databinding.ItemRecViewAllBinding;
import com.hardik.vasukatask.model.Index;
import com.hardik.repository.ImageLoadRepository;

import java.util.ArrayList;
import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.MyViewHolder> implements Filterable {
    private static String TAG = ViewAllAdapter.class.getSimpleName();
    List<Index> indexList;
    List<Index> indexListBackup;
    public ViewAllAdapter() {
        Log.i(TAG, "ViewAllAdapter: ");
    }

    @SuppressLint("NotifyDataSetChanged")
    public synchronized void updateList(List<Index> indexList) {
        Log.i(TAG, "updateList: list size:"+indexList.size());
        this.indexList = indexList;
        this.indexListBackup = new ArrayList<>(indexList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecViewAllBinding binding = ItemRecViewAllBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Index index = indexList.get(position);

        holder.label.setText(index.getTitle());
        holder.description.setText(index.getEducator());
        ImageLoadRepository imageLoadRepository = ImageLoadRepository.getInstance();
        imageLoadRepository.setImageUsingGlide(holder.poster.getContext(), "http://d2xkd1fof6iiv9.cloudfront.net/images/courses/" + index.getId() + "/169_820.jpg", holder.poster);
    }

    @Override
    public int getItemCount() {
        return indexList.size() == 0 ? 0 : indexList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView label, description;

        public MyViewHolder(@NonNull ItemRecViewAllBinding binding) {
            super(binding.getRoot());
            poster = binding.itemRecViewAllImgPoster;
            label = binding.itemRecViewAllTvLabel;
            description = binding.itemRecViewAllTvDec;
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Index> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(indexListBackup);
            } else {
                for (Index index : indexListBackup) {
                    if (index.getOwned().toString().toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                            index.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                            index.getEducator().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        for (String styleTage : index.getStyleTags()) {
                            if (styleTage.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                                if (!filteredList.contains(index)) {
                                    filteredList.add(index);
                                }
                            }
                        }
                        for (String seriesTage : index.getSkillTags()) {
                            if (seriesTage.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                                if (!filteredList.contains(index)) {
                                    filteredList.add(index);
                                }
                            }
                        }
                        for (String curriculumTage : index.getSkillTags()) {
                            if (curriculumTage.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                                if (!filteredList.contains(index)) {
                                    filteredList.add(index);
                                }
                            }
                        }
                        for (String skillTage : index.getSkillTags()) {
                            if (skillTage.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                                if (!filteredList.contains(index)) {
                                    filteredList.add(index);
                                }
                            }
                        }
                        if (!filteredList.contains(index)) {
                            filteredList.add(index);
                        }
                    }

                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            indexList.clear();
            List<Index> list = (List<Index>) filterResults.values;
            indexList.addAll(list);
            notifyDataSetChanged();
        }
    };
}
