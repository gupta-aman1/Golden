package com.example.goldenfish.Retrofit;

import com.example.goldenfish.PanCard.ModelPan.ModelCouponPan;
import com.example.goldenfish.Sidebar.AllReports.ModelAllReports.ModelMainClass;
import com.example.goldenfish.Utilities.UploadFile;
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

    @POST("NewUserRegistration")
    Call<ResponseBody> NewUserRegistration(@Body JsonObject object);

    @POST("GetUsersActivePayoutAccount")
    Call<ResponseBody> GetUsersActivePayoutAccount(@Body JsonObject object);

    @POST("GetMoveToBankSurCharge")
    Call<ResponseBody> GetMoveToBankSurCharge(@Body JsonObject object);

    @POST("MoveToBank")
    Call<ResponseBody> MoveToBank(@Body JsonObject object);

    @POST("GetAllBankList")
    Call<ResponseBody> GetAllBankList(@Body JsonObject object);


    @Multipart
    //  @Headers({"Accept: application/json"})
    @POST("UploadUsersPassbook")
    Call<UploadFile> uploadfile(@Part MultipartBody.Part file);

    @POST("ValidateMpinForTransaction")
    Call<ResponseBody> ValidateMpinForTransaction(@Body JsonObject object);

    @POST("AddPayoutAccount")
    Call<ResponseBody> AddPayoutAccount(@Body JsonObject object);


    @POST("GetWalletBalanceWalletWise")
    Call<ResponseBody> GetWalletBalanceWalletWise(@Body JsonObject object);

    @POST("GetChildsUserType")
    Call<ResponseBody> GetChildsUserType(@Body JsonObject object);

    @POST("GetChildUserSchemeDetails")
    Call<ResponseBody> GetChildUserSchemeDetails(@Body JsonObject object);

    @POST("AddChildUser")
    Call<ResponseBody> AddChildUser(@Body JsonObject object);

    @POST("ChangeUserPassword")
    Call<ResponseBody> ChangeUserPassword(@Body JsonObject object);

    @POST("GetPanCardCouponType")
    Call<ModelCouponPan> GetPanCardCouponType(@Body JsonObject object);

    @POST("GetSurchargeUsingOpId")
    Call<ResponseBody> GetSurchargeUsingOpId(@Body JsonObject object);
}
