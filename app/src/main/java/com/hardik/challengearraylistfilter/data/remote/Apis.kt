package com.hardik.challengearraylistfilter.data.remote

import com.hardik.challengearraylistfilter.data.remote.dto.ItemResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface Apis {
    //https://gist.githubusercontent.com/akshayxunison/d026f35698e6de4ace5b350c281d4992/raw/8f1a506716440e3a96aa7914fba083aacf0e9b28/tfRSPNS
    @GET("/akshayxunison/d026f35698e6de4ace5b350c281d4992/raw/8f1a506716440e3a96aa7914fba083aacf0e9b28/tfRSPNS")
    suspend fun getData() : Response<ItemResponseDto>

}