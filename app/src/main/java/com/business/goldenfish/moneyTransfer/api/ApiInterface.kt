package com.business.goldenfish.moneyTransfer.api

import com.business.goldenfish.moneyTransfer.modeldmt.*
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.POST

interface ApiInterface {

    //@POST("GetRemitterDetails")
    @POST("GetRemitterDetails")
    suspend fun getRemiterDetails(@Body jsonObject:JsonObject) : Response<ModelRemitterDetails>


    @POST("RegisterRemitter")
    suspend fun RegisterRemitter(@Body jsonObject:JsonObject) : Response<ModelRemitterReg>

    @POST("ValidateRemitter")
    suspend fun ValidateRemitter(@Body jsonObject:JsonObject) : Response<ModelRemitterValidation>

    @POST("RegisterBenificiary")
    suspend fun RegisterBenificiary(@Body jsonObject:JsonObject) : Response<ModelRegisterBeni>

    @POST("BenificiaryValidate")
    suspend fun BenificiaryValidate(@Body jsonObject:JsonObject) : Response<ModelValidateOtpBeni>

    @POST("BenificiaryAccountValidation")
    suspend fun BenificiaryAccountValidation(@Body jsonObject:JsonObject) : Response<ModelAccValidation>
}