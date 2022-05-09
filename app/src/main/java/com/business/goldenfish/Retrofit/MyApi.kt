package com.business.goldenfish.Retrofit

import com.business.goldenfish.ledgerreopt.ModelDataLedger
import com.business.goldenfish.ledgerreopt.ModelMainLedger
import com.business.goldenfish.sidebar.allReports.modelAllReports.ModelMainNew
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface MyApi {

//    @POST("GetUsersActivePayoutAccount")
//    fun getAccount()


   // @Headers("Content-Type: application/json")
    @POST("GetAddedFundBankDetails")
    fun getAccount(@Body userData: JsonObject): Response<ResponseBody>

}