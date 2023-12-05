package com.hardik.vasukatask.ui.home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hardik.vasukatask.adapter.ParentAdapter;
import com.hardik.vasukatask.databinding.FragmentHomeBinding;
import com.hardik.vasukatask.model.Index;
import com.hardik.vasukatask.model.ResultModel;
import com.hardik.repository.ReadJsonDataRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {
    private String TAG = HomeFragment.class.getSimpleName();

    public static ReadJsonDataRepository repository = ReadJsonDataRepository.getInstance();
    public static ResultModel resultModel = repository.getData();

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ParentAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        recyclerView = binding.fragmentHomeRecNested;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ParentAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.updateData(resultModel);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

}