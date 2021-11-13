package com.example.goldenfish.AepsSdk.retrofit;

import com.example.goldenfish.AepsSdk.model.AepsModelRequest;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface interfaceAPI {
   // @POST("AEPSTransactionMaster")
  //  Call<JsonObject> getAepsTransaction(@Header("App_Id") String str, @Header("Authorized_keys") String str2, @Header("Pan_No") String str3, @Header("latitude") double str4, @Header("longitude") double str5, @Body AepsModelRequest aepsModelRequest);

//    @POST("Banks")
//    Call<JsonObject> getBank(@Header("App_Id") String str, @Header("Authorized_keys") String str2);

    @POST("GetAepsbankDetails")
    Call<JsonObject> GetAepsbankDetails(@Body JsonObject object);

    @POST("MakeAepsTransaction")
    Call<JsonObject> MakeAepsTransaction(@Body JsonObject object);
}
