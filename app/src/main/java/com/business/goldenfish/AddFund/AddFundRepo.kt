package com.business.goldenfish.AddFund

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.business.goldenfish.AddFund.ModelAddFund.ModelAddFund
import com.business.goldenfish.AddFund.ModelAddFund.ModelAddFundCommon
import com.business.goldenfish.Constants.Constant
import com.business.goldenfish.Retrofit.RetrofitClient
import com.business.goldenfish.Utilities.MyUtils
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFundRepo  {


    fun  getAccountList(jsonObject: JsonObject) : MutableLiveData<ModelAddFund?>
    {
        var accModel: MutableLiveData<ModelAddFund?> = MutableLiveData()
    //    val jsonObject = JsonObject()
//        jsonObject.addProperty("Userid", "13598")
//        jsonObject.addProperty("UserType", "ZBP")
//        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetAddedFundBankDetails", "ZBP", "13598"))
        val call = RetrofitClient.getInstance().api.GetAddedFundBankDetails(jsonObject)
        call.enqueue(object : Callback<ModelAddFund?> {
            override fun onResponse(
                call: Call<ModelAddFund?>,
                response: Response<ModelAddFund?>
            ) {
                if (response.body() != null) {

                    accModel.value=response.body()
                    // HideProgress(ctx);
                    //    progressDialog.dismiss()
                } else {
                    // progressDialog.dismiss()
                    accModel.value=response.body()
                }
            }

            override fun onFailure(call: Call<ModelAddFund?>, t: Throwable) {
                //  progressDialog.dismiss()
                /* Toast.makeText(
                     this@MoveToBankActivity,
                     "Due to Internet Error",
                     Toast.LENGTH_SHORT
                 ).show()*/
                System.out.println("AMAN "+t.message)
            }
        })
        return accModel
    }

    fun MakeAddFundRequest(jsonObject: JsonObject)  : MutableLiveData<ModelAddFundCommon?>
    {
        var ModelfundReq: MutableLiveData<ModelAddFundCommon?> = MutableLiveData()
        val call = RetrofitClient.getInstance().api.MakeAddFundRequest(jsonObject)
        call.enqueue(object : Callback<ModelAddFundCommon?> {
            override fun onResponse(
                call: Call<ModelAddFundCommon?>,
                response: Response<ModelAddFundCommon?>
            ) {
                if (response.body() != null) {
                    ModelfundReq.value= response.body()
                  //  accModel.value=response.body()
                    // HideProgress(ctx);
                    //    progressDialog.dismiss()
                } else {
                    ModelfundReq.value= response.body()
                    // progressDialog.dismiss()
                   // accModel.value=response.body()
                }
            }

            override fun onFailure(call: Call<ModelAddFundCommon?>, t: Throwable) {
                //  progressDialog.dismiss()
                /* Toast.makeText(
                     this@MoveToBankActivity,
                     "Due to Internet Error",
                     Toast.LENGTH_SHORT
                 ).show()*/
                System.out.println("AMAN "+t.message)
            }
        })
        return ModelfundReq
    }
}