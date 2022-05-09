package com.business.goldenfish.AddFund

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.business.goldenfish.AddFund.ModelAddFund.ModelAddFund
import com.business.goldenfish.AddFund.ModelAddFund.ModelAddFundCommon
import com.business.goldenfish.Constants.Constant
import com.business.goldenfish.Retrofit.RetrofitClient
import com.business.goldenfish.Utilities.MyUtils
import com.business.goldenfish.Utilities.SharedPref
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFundVM(application: Application) : AndroidViewModel(application){

    private var userType: String?
    private var userid: String?
    var data1: String? ="aman"
    var quotes: LiveData<String?> = MutableLiveData()
     var addFundRepo:AddFundRepo
      var accData: MutableLiveData<ModelAddFund?> = MutableLiveData()
    var ModelfundReq: MutableLiveData<ModelAddFundCommon?> = MutableLiveData()
    var sharedPref: SharedPref? = null
    init {
       /* viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
           // data as MutableLiveData<*>
            getAccount()
        }*/
        sharedPref = SharedPref.getInstance(application)
        userid = sharedPref?.getStringWithNull(Constant.userId)
        userType = sharedPref?.getStringWithNull(Constant.Usertype)
        addFundRepo= AddFundRepo()
    }


    fun getfinalAccountList(jsonObject: JsonObject) : MutableLiveData<ModelAddFund?>
    {
          //  viewModelScope.launch(Dispatchers.IO) {
             //   var result :ModelAddFund?=null
                accData = addFundRepo.getAccountList(jsonObject)

               // accData.postValue(abc)
              /* withContext(Dispatchers.Main) {
                  //  delay(5000)
                    Log.d("ViewModel", "withContext thread: ${Thread.currentThread().name}")


                  //  Log.d("ViewModel", accData)
                    System.out.println("ViewModel "+accData)
                }*/

       //     }

        return accData;
    }

    fun sendAddFundRequest(jsonObject: JsonObject) :MutableLiveData<ModelAddFundCommon?>
    {
        ModelfundReq=addFundRepo.MakeAddFundRequest(jsonObject)

        return ModelfundReq;
    }

   private suspend fun getAccount()
    {
       /* viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
        }*/

        return withContext(Dispatchers.IO) {
            Log.i(TAG, "Getting Quotes")
            val jsonObject = JsonObject()
           // jsonObject.addProperty("Userid", "13598")
           // jsonObject.addProperty("UserType", "ZBP")
            //jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetAddedFundBankDetails", "ZBP", "13598"))
          // data1= RetrofitClient.getInstance().apiNew.getAccount(jsonObject).body()!!.string()

            //quotes= data1 as MutableLiveData

            jsonObject.addProperty("Userid", "13598")
            jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetUsersActivePayoutAccount", "", "13598"))
            val call = RetrofitClient.getInstance().api.GetUsersActivePayoutAccount(jsonObject)
            call.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    if (response.body() != null) {
                        // HideProgress(ctx);
                    //    progressDialog.dismiss()

                        val acc = ArrayList<String?>()
                        val acc_id = ArrayList<String>()
                        acc.add("Select Account")
                        acc_id.add("-2")
                        try {
                            var abc = response.body()!!.string()

                        } catch (e: Exception) {
                        }
                    } else {
                       // progressDialog.dismiss()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                  //  progressDialog.dismiss()
                   /* Toast.makeText(
                        this@MoveToBankActivity,
                        "Due to Internet Error",
                        Toast.LENGTH_SHORT
                    ).show()*/
                }
            })
        }
    }

    //fun getFiles(): LiveData<ModelAddFund> = accData
}