package com.hardik.challengearraylistfilter.presentation.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hardik.challengearraylistfilter.R
import com.hardik.challengearraylistfilter.databinding.FragmentFirstBinding
import com.hardik.challengearraylistfilter.presentation.adapter.FilterAdapter
import com.hardik.challengearraylistfilter.presentation.adapter.FilterBottomSheetAdapter
import com.hardik.challengearraylistfilter.presentation.adapter.IndexAdapter
import com.hardik.challengearraylistfilter.presentation.model.FilterData
import com.hardik.challengearraylistfilter.presentation.model.SubFilter
import com.hardik.challengearraylistfilter.presentation.view_model.DataViewModel
import com.hardik.challengearraylistfilter.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: DataViewModel
    private lateinit var indexAdapter: IndexAdapter
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var filterBottomSheetAdapter: FilterBottomSheetAdapter
    private lateinit var filterList: MutableList<FilterData>
    private var filterItemNameList = mutableListOf<String>()
    private lateinit var newFilterList: MutableList<FilterData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterBottomSheetAdapter = FilterBottomSheetAdapter(ArrayList())
        filterAdapter = FilterAdapter(ArrayList())
        indexAdapter = IndexAdapter(ArrayList())
        setRecyclerview()


        viewModel = (activity as MainActivity).viewModel
        // Observe the LiveData in your ViewModel
        viewModel.itemResponseDto.observe(viewLifecycleOwner, Observer { resource ->
            // Update UI based on the state of the resource
            when (resource) {
                is Resource.Loading -> {
                    // Show loading indicator or perform any loading-related tasks
                    val handler = Handler(Looper.getMainLooper())
                    handler.post(Runnable {
                        // update the ui from here
                        Log.w("TAG", "onViewCreated: Loading...")
                    })
                }

                is Resource.Success -> {
                    val itemResponseDto = resource.data
                    // Update UI with the itemResponseDto
//                    Log.e("TAG", "onViewCreated: ${itemResponseDto}", )
                    itemResponseDto?.result?.index?.let {
                        viewModel.setIndexList(it)
                    }
                }

                is Resource.Error -> {
                    val errorMessage = resource.message
                    // Show error message or handle error scenario
                    Log.e("TAG", "onViewCreated: $errorMessage")
                }
            }
        })

        viewModel.filterList.observe(viewLifecycleOwner, Observer {
            filterList = it.toMutableList()
            filterAdapter.updateFilterList(it)
        })
        viewModel.filterOriginal.observe(viewLifecycleOwner, Observer {
            newFilterList = it.toMutableList()
        })

        viewModel.indexList.observe(viewLifecycleOwner, Observer {
            Log.e("TAG", "onViewCreated: indexList: ${it.size}")
            indexAdapter.updateIndexList(ArrayList(it))
        })

        filterAdapter.setOnItemClickListener { filter, pos ->
            if (filter.filterName == "CleanAll" && filter.isSelected) {
               if (filterItemNameList.isNotEmpty()) {
                    filterItemNameList.clear()

//                    ///filterList.remove(filterList.find { it.filterName == "CleanAll" } )
//                    viewModel.setFilterData(newFilterList)
//                    /// send empty filter list
//                    viewModel.setFilterItemName(filterItemNameList)
//                    filterAdapter.updateFilterList(newFilterList)
//                    newFilterList.iterator().forEach {
//                        filterBottomSheetAdapter.updateFilterList(it.subFilters)
//                    }
                   viewModel.getData()
                }

            } else {
                openBottomSheet(pos, filter.filterName, filter.subFilters)
            }
        }

        indexAdapter.setOnItemClickListener {
//            findNavController().navigate(R.id.action_albumFragment_to_photoFragment, Bundle().apply { putInt(PARAM_ALBUM_ID,it.id) })
            Log.w("TAG", "onViewCreated: $it")
        }
    }

    private fun openBottomSheet(filterPos: Int, filterName: String, filterValue: List<SubFilter>) {
        val dialog = Dialog(requireContext());
        dialog.setContentView(R.layout.bottom_sheet_layout)

        val title: TextView = dialog.findViewById<TextView>(R.id.bottomSheetLayout_tv_title)
        title.text = filterName


        val recyclerView: RecyclerView =
            dialog.findViewById<RecyclerView>(R.id.bottomSheetLayout_listView_filter)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = filterBottomSheetAdapter
        recyclerView.setHasFixedSize(true)
        filterBottomSheetAdapter.updateFilterList(filterValue)

        filterBottomSheetAdapter.setOnItemClickListener { subFilter, subFilterPos ->
            /// set active filter position to current position(filter,subFilter)
            filterList[filterPos].apply {
                /// filter list add
                subFilters[subFilterPos].isSelected = !subFilter.isSelected
                if (subFilters[subFilterPos].isSelected) {
                    filterItemNameList.add(subFilters[subFilterPos].name)
                } else {
                    filterItemNameList.remove(subFilters[subFilterPos].name)
                }

                /// send filter list
                viewModel.setFilterItemName(filterItemNameList)
                viewModel.filterItemNameList.observe(viewLifecycleOwner, Observer {
                    Log.e("TAG", "openBottomSheet: filterItemNameList $it")
                })
//                viewModel.filterListWithCleaner.observe(viewLifecycleOwner, Observer {
//                    Log.e("TAG", "openBottomSheet: filterListWithCleaner ${it.size}", )
//                })
                viewModel.indexFilteredList.observe(viewLifecycleOwner, Observer {
//                    Log.e("TAG", "openBottomSheet: indexFilteredList: ${it.size}")
                    CoroutineScope(Dispatchers.Main).launch {
                        indexAdapter.updateIndexList(ArrayList(it))
                    }
                })

                for (fItem in this.subFilters) {
                    var anyItemSelected = false
                    for (sf in this.subFilters) {
                        if (sf.isSelected) {
                            anyItemSelected = true
                            break
                        }
                    }
                    isSelected = anyItemSelected
                }
            }
            viewModel.setFilterData(filterList)

            CoroutineScope(Dispatchers.Main).launch {
                dialog.dismiss()
            }
        }

        val clean: TextView = dialog.findViewById<TextView>(R.id.bottomSheetLayout_tv_clear)
        clean.setOnClickListener {
            filterList[filterPos].apply {

                subFilters.forEach {
                    /// remove the filter name in the filter list
                    filterItemNameList.remove(it.name)

                    /// main filter disabled
                    isSelected = false
                    /// sub filter disabled
                    it.isSelected = false
                }
            }
            viewModel.setFilterData(filterList)
            viewModel.setFilterItemName(filterItemNameList)
            filterBottomSheetAdapter.updateFilterList(filterValue)
        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    private fun setRecyclerview() {
        binding.recyclerviewFilter.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = filterAdapter
            setPadding(0, 0, 0, 0)
        }
        binding.recyclerviewMain.apply {
            /// LinearLayoutManager(requireContext(), LinearLayoutManager., false)
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = indexAdapter
            setPadding(0, 0, 0, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}