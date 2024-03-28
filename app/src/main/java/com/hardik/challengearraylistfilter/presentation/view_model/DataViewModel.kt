package com.hardik.challengearraylistfilter.presentation.view_model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardik.challengearraylistfilter.data.remote.dto.Index
import com.hardik.challengearraylistfilter.data.remote.dto.ItemResponseDto
import com.hardik.challengearraylistfilter.data.repository.Repository
import com.hardik.challengearraylistfilter.presentation.model.FilterData
import com.hardik.challengearraylistfilter.presentation.model.getUniqueValues
import com.hardik.challengearraylistfilter.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class DataViewModel(private val context: Application, private val repositoryInstance: Repository) :
    ViewModel() {

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val _data: MutableLiveData<Resource<ItemResponseDto>> = MutableLiveData()
    val itemResponseDto: LiveData<Resource<ItemResponseDto>> get() = _data

    private val _index: MutableLiveData<List<Index>> = MutableLiveData()
    val indexList: LiveData<List<Index>> get() = _index

    private val _filterOriginal: MutableLiveData<List<FilterData>> = MutableLiveData()
    val filterOriginal: LiveData<List<FilterData>> get() = _filterOriginal
    private val _filterWithOutCleaner: MutableLiveData<List<FilterData>> = MutableLiveData()
    val filterListWithOutCleaner: LiveData<List<FilterData>> get() = _filterWithOutCleaner
    private val _filterWithCleaner: MutableLiveData<List<FilterData>> = MutableLiveData()
    val filterListWithCleaner: LiveData<List<FilterData>> get() = _filterWithCleaner
    private val _filter: MutableLiveData<List<FilterData>> = MutableLiveData()
    val filterList: LiveData<List<FilterData>> = _filter

    private val _filterItemName: MutableLiveData<List<String>> = MutableLiveData()
    val filterItemNameList: LiveData<List<String>> get() = _filterItemName

    private val _indexFiltered: MutableLiveData<List<Index>> = MutableLiveData()
    val indexFilteredList: LiveData<List<Index>> get() = _indexFiltered

    init {
        getData()
    }

    private fun getFilter(){
        Log.w("TAG", "getFilter: ${filterItemNameList.value}", )
        _filter.postValue(if (filterItemNameList.value.isNullOrEmpty()) {
            _filterWithOutCleaner.value
        } else {
            _filterWithCleaner.value
        })
    }
//    private fun setFilterListWithCleaner(): LiveData<List<FilterData>> {
//        val f = FilterData(filterName = "CleanAll", isSelected = true, ArrayList())
//        val list = mutableListOf(f)
//        list.addAll(_filter.value ?: emptyList())
//        return MutableLiveData(list)
//    }

    fun getData() = viewModelScope.launch {
        safeDataCall()
    }

    private suspend fun safeDataCall() {
        _data.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = repositoryInstance.getData()
                handleDataResponse(response)
            } else {
                _data.postValue(Resource.Error("No internet Connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> _data.postValue(Resource.Error("Network failure!!!"))
                else -> _data.postValue(Resource.Error("Conversion error!!!"))
            }
        }
    }

    private fun handleDataResponse(response: Response<ItemResponseDto>) {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                _data.postValue(Resource.Success(resultResponse))
                getFilters(resultResponse)
            }
        } else {
            _data.postValue(Resource.Error("Failed to fetch data"))
        }
    }

    private fun getFilters(resultResponse: ItemResponseDto) {
        Log.e("TAG", "getFilters: ItemResponseDto", )
        val filterItems = getUniqueValues(resultResponse.result.index)
        _filterOriginal.postValue(filterItems)
        _filterWithOutCleaner.postValue(filterItems)
        _filterWithCleaner.postValue(listOf(FilterData(filterName = "CleanAll", isSelected = true, ArrayList())) + filterItems)
        _filter.postValue(filterItems)
    }

    fun setFilterData(filterDataList: List<FilterData>) {
//        _filterWithOutCleaner.value = _filterWithOutCleaner.value
//        _filterWithCleaner.value = _filterWithCleaner.value
        _filter.value = filterDataList
        getFilter()
    }

    fun setFilterItemName(filterItemName: List<String>) {
        _filterItemName.value = filterItemName
        _filter.postValue(if (filterItemNameList.value.isNullOrEmpty()) {
            _filterWithOutCleaner.value
        } else {
            _filterWithCleaner.value
        })
        getFilteredIndexListRemoving()
    }

    private fun getFilteredIndexListAdding() {
        val filteredIndexSet = mutableSetOf<Index>()

        indexList.observeForever { indexList ->
            filteredIndexSet.clear() // Clear the set before filtering

            indexList.forEach { index ->
                val filterNameList = _filterItemName.value

                if (!filterNameList.isNullOrEmpty()) {
                    filterNameList.forEach { filterName ->
                        val filterNameMatched = (
                                index.curriculumTags.contains(filterName) ||
                                        index.styleTags.contains(filterName) ||
                                        index.skillTags.contains(filterName) ||
                                        index.seriesTags.contains(filterName) ||
                                        index.educator == filterName ||
                                        index.owned.toString() == filterName
                                )
                        if (filterNameMatched) {
                            filteredIndexSet.add(index)
                        }
                    }
                } else {
                    // If filterNameList is empty, add the index directly
                    filteredIndexSet.add(index)
                }
            }
            _indexFiltered.postValue(filteredIndexSet.toList())
        }
    }

    private fun getFilteredIndexListRemoving() {
        val filteredIndexSet = mutableSetOf<Index>()
        val filterNameList = _filterItemName.value ?: emptyList()

        if (filterNameList.isNotEmpty()) {
            indexList.observeForever { indexList ->
                filteredIndexSet.clear() // Clear the set before filtering

                indexList.forEach { index ->
                    val filterNames = listOf(
                        index.curriculumTags,
                        index.styleTags,
                        index.skillTags,
                        index.seriesTags,
                        listOf(index.educator),
                        listOf(index.owned.toString())
                    ).flatten()

//                    if (filterNames.any { it in filterNameList }) {
//                        filteredIndexSet.add(index)
//                    }
                    if (filterNames.containsAll(filterNameList)) {
                        filteredIndexSet.add(index)
                    }
                }
                _indexFiltered.postValue(filteredIndexSet.toList())
            }
        } else {
            // If filterNameList is empty, post the original index list
            _indexFiltered.postValue(_index.value)
        }
    }

    fun setIndexList(index: List<Index>) {
        _index.postValue(index)
    }

    // Check internet connection
    @SuppressLint("ObsoleteSdkInt")
    private fun hasInternetConnection(): Boolean {
        Log.e("TAG", "hasInternetConnection: ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}