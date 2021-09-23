package com.example.goldenfish.Retrofit;

import com.example.goldenfish.Sidebar.AllReports.ModelAllReports.ModelMainClass;
import com.google.gson.JsonObject;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface interfaceAPI {

    @POST("UserAccountLogin")
    Call<ResponseBody> UserAccountLogin(@Body JsonObject object);

    @POST("ValidateOTPForLogin")
    Call<ResponseBody> ValidateOTPForLogin(@Body JsonObject object);

    @POST("GetWalletBalance")
    Call<ResponseBody> GetWalletBalance(@Body JsonObject object);

    @POST("ForgetPassword")
    Call<ResponseBody> ForgetPassword(@Body JsonObject object);

    @POST("GetALLReports")
    Call<ModelMainClass> GetALLReports(@Body JsonObject object);

    @POST("GetStateListDetails")
    Call<ResponseBody> GetStateListDetails(@Body JsonObject object);

    @POST("GetCityListDetails")
    Call<ResponseBody> GetCityListDetails(@Body JsonObject object);
}
