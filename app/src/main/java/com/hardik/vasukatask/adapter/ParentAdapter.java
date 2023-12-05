package com.hardik.vasukatask.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hardik.vasukatask.R;
import com.hardik.vasukatask.databinding.ItemRecParentBinding;
import com.hardik.vasukatask.model.Index;
import com.hardik.vasukatask.model.ResultModel;
import com.hardik.vasukatask.model.Smart;
import com.hardik.vasukatask.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.MyViewHolder> {
    private static String TAG = ParentAdapter.class.getSimpleName();
    private Activity activity;
    ResultModel resultModel;
    List<Smart> smartList;
    List<User> userList;
    List<Index> indexList;

    public ParentAdapter(Activity activity) {
        this.activity = activity;

    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ResultModel resultModel) {
        this.resultModel = resultModel;
        this.indexList = resultModel.getResult().getIndex();
        Log.i(TAG, "updateData: "+indexList.size());
        this.smartList = resultModel.getResult().getCollections().getSmart();
        this.userList = resultModel.getResult().getCollections().getUser();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecParentBinding binding = ItemRecParentBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Smart smart = smartList.get(position);
        holder.label.setText(smart.getLabel());

        holder.recyclerViewChild.setLayoutManager(new LinearLayoutManager(activity.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewChild.setHasFixedSize(true);
        ChildAdapter childAdapter = new ChildAdapter();
        holder.recyclerViewChild.setAdapter(childAdapter);
        childAdapter.updateCourses(smartList, userList, indexList, smart.getCourses());

        holder.viewAll.setText("View All");
        holder.viewAll.setOnClickListener(view -> {
            Log.i(TAG, "onClick: got all data");
            Bundle b = new Bundle();
            b.putParcelableArrayList("index", (ArrayList<? extends Parcelable>) indexList);
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_viewAllFragment,b);
        });
    }

    @Override
    public int getItemCount() {
        return smartList.size() == 0 ? 0 : smartList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView label, viewAll;
        RecyclerView recyclerViewChild;

        public MyViewHolder(@NonNull ItemRecParentBinding binding) {
            super(binding.getRoot());
            label = binding.itemRecParentTvLabel;
            viewAll = binding.itemRecParentTvViewAll;
            recyclerViewChild = binding.itemRecParentRecChild;
        }
    }
}
