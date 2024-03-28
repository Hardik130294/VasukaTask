package com.hardik.challengearraylistfilter.presentation.view_model


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hardik.challengearraylistfilter.data.repository.Repository

class DataViewModelProviderFactory(@SuppressLint("StaticFieldLeak") private val context: Application, private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataViewModel(context,repository) as T

        }
    throw IllegalStateException("Unknown viewModel class")
}
}