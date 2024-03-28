package com.hardik.challengearraylistfilter.data.repository

import com.hardik.challengearraylistfilter.data.remote.RetrofitInstance

class Repository {
    suspend fun getData() = RetrofitInstance.api.getData()
}

