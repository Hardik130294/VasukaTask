package com.hardik.vasukatask.ui.view_all;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hardik.vasukatask.R;
import com.hardik.vasukatask.adapter.FilterAdapter;
import com.hardik.vasukatask.adapter.ItemSelected;
import com.hardik.vasukatask.adapter.OnItemClickListener;
import com.hardik.vasukatask.adapter.ViewAllAdapter;
import com.hardik.vasukatask.databinding.FragmentViewAllBinding;
import com.hardik.vasukatask.model.FilterItem;
import com.hardik.vasukatask.model.Index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ViewAllFragment extends Fragment {
    private String TAG = ViewAllFragment.class.getSimpleName();
    private FragmentViewAllBinding binding;
    private RecyclerView recyclerViewFilter, recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FilterAdapter filterAdapter;
    private ViewAllAdapter adapter;
    private List<Index> index, indexListNew;
    private List<String> selectedList = new ArrayList<>();

    private SearchView searchView;
    private MenuItem menuItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getParcelableArrayList("index");
            indexListNew = new ArrayList<>();
        }
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);

        menuItem = menu.findItem(R.id.action_search);
        View actionView = menuItem.getActionView();

        if (actionView instanceof SearchView) {
            searchView = (SearchView) actionView;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                @Override
                public boolean onQueryTextSubmit(String query) {
//                    adapter.getFilter().filter(query);
                    menuItem.collapseActionView();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return true;
                }
            });

            menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // SearchView expanded
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    binding.fragmentViewAllRec.setVisibility(View.VISIBLE);
                    Log.e(TAG, "menuItem.onMenuItemActionCollapse().onClick: ");

                    menuItem.setVisible(true);
                    searchView.setVisibility(View.VISIBLE);
                    return true;
                }
            });
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_search) {
            Log.e(TAG, "action_search = onOptionsItemSelected() ");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_view_all, container, false);
        binding = FragmentViewAllBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewFilter = binding.fragmentViewAllRecFilter;
        recyclerView = binding.fragmentViewAllRec;
        recyclerViewFilter.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerViewFilter.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ViewAllAdapter();
        recyclerView.setAdapter(adapter);
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
        adapter.updateList(index);

        List<FilterItem> filterItemList = new ArrayList<>();
        List<String> styleTags = new ArrayList<>();
        List<String> skillTags = new ArrayList<>();
        List<String> seriesTags = new ArrayList<>();
        List<String> curriculumTags = new ArrayList<>();
        List<String> educatorTags = new ArrayList<>();
        for (int i = 0; i < index.size(); i++) {
            for (int j = 0; j < index.get(i).getStyleTags().size(); j++) {
                styleTags.add(index.get(i).getStyleTags().get(j));
            }
            for (int j = 0; j < index.get(i).getSkillTags().size(); j++) {
                skillTags.add(index.get(i).getSkillTags().get(j));
            }
            for (int j = 0; j < index.get(i).getSeriesTags().size(); j++) {
                seriesTags.add(index.get(i).getSeriesTags().get(j));
            }
            for (int j = 0; j < index.get(i).getCurriculumTags().size(); j++) {
                curriculumTags.add(index.get(i).getCurriculumTags().get(j));
            }
            educatorTags.add(index.get(i).getEducator());
        }
//        filterItemList.add(new FilterItem("Clear",false, uniqueStringList(new ArrayList<>())));
        filterItemList.add(new FilterItem("Owned",false,uniqueStringList(new ArrayList<>(Arrays.asList("Yes", "No")))));
        filterItemList.add(new FilterItem("Style",false, uniqueStringList(styleTags)));
        filterItemList.add(new FilterItem("Skill",false, uniqueStringList(skillTags)));
        filterItemList.add(new FilterItem("Series",false, uniqueStringList(seriesTags)));
        filterItemList.add(new FilterItem("Curriculum",false, uniqueStringList(curriculumTags)));
        filterItemList.add(new FilterItem("Educator",false, uniqueStringList(educatorTags)));

        filterAdapter = new FilterAdapter( filterItemList, new OnItemClickListener() {
            @Override
            public void onItemClick(int pos, List<String> list, ItemSelected itemSelected) {
                final Dialog dialog = new Dialog(requireContext());
                dialog.setContentView(R.layout.bottom_sheet_layout);

                if (list != null) {

                    Log.e(TAG, "onResume: ");
                    // Convert List to array
                    String[] dataArray = list.toArray(new String[0]);
                    // Create an ArrayAdapter
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, dataArray);
//                    MyAdapter adapter = new MyAdapter(requireContext(), list);
                    // Get a reference to the ListView in your layout
                    ListView listViewBottom = dialog.findViewById(R.id.bottomSheetLayout_listView_filter);
                    TextView tvClear = dialog.findViewById(R.id.bottomSheetLayout_tv_clear);
                    tvClear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedList.clear();
                            ViewAllFragment.this.adapter.updateList(index);
                            for (int i = 0; i < filterItemList.size(); i++) {
                                filterItemList.get(i).setSelected(false);
                            }
                            filterAdapter.updateList(filterItemList);
                            dialog.dismiss();
                        }
                    });

                    // Set the adapter to the ListView
                    listViewBottom.setAdapter(adapter);

                    // Optionally, set an item click listener
                    listViewBottom.setOnItemClickListener((parent, view, position, id) -> {
                        // Handle item click
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        // Do something with the selected item
                        Log.i(TAG, "onItemClick: " + selectedItem);
                        if (selectedList.contains(selectedItem)) {
                            selectedList.remove(selectedItem);
                        } else {
                            selectedList.add(selectedItem);
                        }
                        itemSelected.itemSelected(selectedList.size()==0 ? false:true);

                        for (int i = 0; i < selectedList.size(); i++) {
                            for (int j = 0; j < index.size(); j++) {
                                if (selectedList.contains("Yes") && index.get(j).getOwned() == 1) {
                                    addToIndexList(indexListNew, index.get(j));
                                } else if (selectedList.contains("No") && index.get(j).getOwned() == 0) {
                                    addToIndexList(indexListNew, index.get(j));
                                } else if (index.get(j).getStyleTags().contains(selectedList.get(i))) {
                                    addToIndexList(indexListNew, index.get(j));
                                } else if (index.get(j).getSkillTags().contains(selectedList.get(i))) {
                                    addToIndexList(indexListNew, index.get(j));
                                } else if (index.get(j).getSeriesTags().contains(selectedList.get(i))) {
                                    addToIndexList(indexListNew, index.get(j));
                                } else if (index.get(j).getCurriculumTags().contains(selectedList.get(i))) {
                                    addToIndexList(indexListNew, index.get(j));
                                } else if (index.get(j).getEducator().contains(selectedList.get(i))) {
                                    addToIndexList(indexListNew, index.get(j));
                                } else {
                                    indexListNew.remove(index.get(j));
                                }
                            }
                        }
                        ViewAllFragment.this.adapter.updateList(indexListNew);
                    });

                    dialog.show();
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                } else {
                    dialog.dismiss();
                }
            }
        });
        recyclerViewFilter.setAdapter(filterAdapter);


    }

    public synchronized List<String> uniqueStringList(List<String> stringList) {
        // Remove null and empty strings
        List<String> filteredList = new ArrayList<>();
        for (String str : stringList) {
            if (str != null && !str.isEmpty()) {
                filteredList.add(str);
            }
        }
        // Create a unique list from the filtered list
        Set<String> uniqueSet = new HashSet<>(filteredList);
        List<String> uniqueList = new ArrayList<>(uniqueSet);

        return uniqueList;
    }

    private synchronized void addToIndexList(List<Index> indexListNew, Index obj) {
        // Add the object to the list if it's not already present
        if (!indexListNew.contains(obj)) {
            indexListNew.add(obj);
        }
    }
}