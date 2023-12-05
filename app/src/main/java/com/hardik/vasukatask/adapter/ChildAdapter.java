package com.hardik.vasukatask.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hardik.vasukatask.databinding.ItemRecChildBinding;
import com.hardik.vasukatask.model.Index;
import com.hardik.vasukatask.model.Smart;
import com.hardik.vasukatask.model.User;
import com.hardik.repository.ImageLoadRepository;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder> {
    private static String TAG = ChildAdapter.class.getSimpleName();
    private List<Integer> courses;
    List<Smart> smartList;
    List<User> userList;
    List<Index> indexList;

    public ChildAdapter() {
        Log.i(TAG, "ChildAdapter: ");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateCourses(List<Smart> smartList, List<User> userList, List<Index> indexList, List<Integer> courses) {
        this.smartList = smartList;
        this.userList = userList;
        this.indexList = indexList;
        this.courses = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecChildBinding binding = ItemRecChildBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Integer course_id = courses.get(position);
        for (int i = 0; i < indexList.size(); i++) {
            if (indexList.get(i).getId().equals(course_id)) {
                holder.label.setText(indexList.get(i).getTitle());
                holder.description.setText(indexList.get(i).getEducator());
            }
        }
        ImageLoadRepository imageLoadRepository = ImageLoadRepository.getInstance();
        imageLoadRepository.setImageUsingGlide(holder.poster.getContext(), "http://d2xkd1fof6iiv9.cloudfront.net/images/courses/" + course_id + "/169_820.jpg", holder.poster);
    }

    @Override
    public int getItemCount() {
        return courses.size() == 0 ? 0 : courses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView label, description;

        public MyViewHolder(@NonNull ItemRecChildBinding binding) {
            super(binding.getRoot());
            poster = binding.itemRecChildImgPoster;
            label = binding.itemRecChildTvLabel;
            description = binding.itemRecChildTvDec;
        }
    }
}
