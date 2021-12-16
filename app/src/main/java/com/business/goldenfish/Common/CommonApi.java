package com.business.goldenfish.Common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.business.goldenfish.AepsSdk.WTS_Aeps_Activity;
import com.business.goldenfish.Constants.Constant;
import com.business.goldenfish.Constants.ConstantsValue;
import com.business.goldenfish.MoveToBank.MoveToBankActivity;
import com.business.goldenfish.R;
import com.business.goldenfish.Retrofit.RetrofitClient;
import com.business.goldenfish.Utilities.MyUtils;
import com.business.goldenfish.Utilities.UploadFile;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonApi {

    public static void getSurcharge(Activity activity, JsonObject jsonObject,CommonInterface commonInterface)
    {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Loading.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //JsonObject jsonObject= new JsonObject();
        //jsonObject.addProperty("Userid",userid);
        //jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetUsersActivePayoutAccount","",userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetMoveToBankSurCharge(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    try {

                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            JSONArray rootarr=jsonObject1.getJSONArray("Data");


                            for (int i=0;i<rootarr.length();i++)
                            {
                                JSONObject innerobj= rootarr.getJSONObject(i);

                                String CommPer=innerobj.getString("CommPer");
                                String CommType=innerobj.getString("CommType");
                                String ChargePer=innerobj.getString("ChargePer");
                                String ChargeType=innerobj.getString("ChargeType");
                               commonInterface.getMoveToBankSurcharge(CommPer,CommType,ChargePer,ChargeType);
                            }


                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(activity, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getSurchargeByOperator(Activity activity, JsonObject jsonObject,CommonInterface commonInterface)
    {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Loading.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetSurchargeUsingOpId(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    try {

                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            JSONArray rootarr=jsonObject1.getJSONArray("Data");


                            for (int i=0;i<rootarr.length();i++)
                            {
                                JSONObject innerobj= rootarr.getJSONObject(i);
                                String CommPer=innerobj.getString("CommPer");
                                String CommType=innerobj.getString("CommType");
                                String ChargePer=innerobj.getString("ChargePer");
                                String ChargeType=innerobj.getString("ChargeType");
                                commonInterface.getMoveToBankSurcharge(CommPer,CommType,ChargePer,ChargeType);
                            }


                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(activity, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getState(Activity activity,CommonInterface commonInterface)
    {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Loading States...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Userid", "13598");
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetStateListDetails", "", "13598"));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetStateListDetails(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    ArrayList al1 = new ArrayList<String>();
                    ArrayList  al2 = new ArrayList<String>();
                    al1.add("Select State");
                    al2.add("-2");
                    try {

                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            JSONArray rootarr=jsonObject1.getJSONArray("Data");


                            for (int i=0;i<rootarr.length();i++)
                            {
                                JSONObject innerobj= rootarr.getJSONObject(i);
                                String StateName=innerobj.getString("StateName");
                                String Id=innerobj.getString("Id");
                                al1.add(StateName);
                                al2.add(Id);
                                commonInterface.getStates(al1,al2);
                            }


                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(activity, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getCity(Activity activity,CommonInterface commonInterface,String state_id)
    {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Loading City...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Userid", "13598");
        jsonObject.addProperty("StateId", state_id);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetCityListDetails", state_id, "13598"));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetCityListDetails(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    ArrayList al1 = new ArrayList<String>();
                    ArrayList  al2 = new ArrayList<String>();
                    al1.add("Select City");
                    al2.add("-2");
                    try {

                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            JSONArray rootarr=jsonObject1.getJSONArray("Data");


                            for (int i=0;i<rootarr.length();i++)
                            {
                                JSONObject innerobj= rootarr.getJSONObject(i);
                                String StateName=innerobj.getString("CityName");
                                String Id=innerobj.getString("Id");
                                al1.add(StateName);
                                al2.add(Id);
                                commonInterface.getCity(al1,al2);
                            }


                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(activity, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void verifyMPIN(Activity activity,String mpin,String userId,CommonInterface commonInterface)
    {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Loading.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userId);
        jsonObject.addProperty("Mpin",mpin);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("ValidateMpinForTransaction",mpin,userId));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().ValidateMpinForTransaction(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;

                    try {

                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            commonInterface.MpinStatus(true);
                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();

                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(activity);
                            builder1.setMessage(jsonObject1.getString("Message"));
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            // finish();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            //Toast.makeText(MoveToBankActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void sendOTP(Activity activity,String userId,String mobile,CommonInterface commonInterface)
    {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Sending OTP.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userId);
        jsonObject.addProperty("Mobile",mobile);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("SendOTPForOutlet",mobile,userId));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().SendOTPForOutlet(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;

                    try {

                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            Toast.makeText(activity, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                            commonInterface.OTPtatus(true);
                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();

                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(activity);
                            builder1.setMessage(jsonObject1.getString("Message"));
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            // finish();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            //Toast.makeText(MoveToBankActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getBalanceWalletWise(Activity activity, JsonObject jsonObject,CommonInterface commonInterface)
    {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Loading.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //JsonObject jsonObject= new JsonObject();
        //jsonObject.addProperty("Userid",userid);
        //jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetUsersActivePayoutAccount","",userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetWalletBalanceWalletWise(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    try {

                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {

                           JSONObject data= jsonObject1.getJSONObject("Data");
                          String bal= data.getString("Bal");
                                commonInterface.getWalletBalance(bal);



                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(activity, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void serverUpload(File myfile, final Context context, final OnResponse listener, String... args) {
        for(String s:args)
        {
            System.out.println("FINAL"+s);
        }

// Compressing Image Before Upload to Server
        try {
            myfile = new Compressor.Builder(context).setQuality(50)
                    .setCompressFormat(Bitmap.CompressFormat.PNG).build().compressToFile(myfile);
        }
        catch (Throwable t) {
            //Log.e("COMPRESS", "Error compressing file." + t.toString ());
            t.printStackTrace ();
        }
        try {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Being Process");
            progressDialog.setMessage("Please wait, while processing....");
            progressDialog.show();
            RequestBody reqFile;
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            if (myfile.toString().contains(".pdf")){
                reqFile = RequestBody.create(MediaType.parse("application/pdf"),myfile);
            }else {
                reqFile = RequestBody.create(MediaType.parse("image/*"), myfile);
            }
            MultipartBody.Part body = MultipartBody.Part.createFormData("myFile", timeStamp + myfile.getName(), reqFile);

            Call<UploadFile> call = RetrofitClient.getInstance().getApi().uploadfile(body);

            call.enqueue(new Callback<UploadFile>() {
                @Override
                public void onResponse(Call<UploadFile> call, Response<UploadFile> response) {
                    progressDialog.dismiss();
                    try {
                        if (response.body() != null) {
                            String globalDocumentPath = null;
                            String statusCode = response.body().getStatuscode();
                            System.out.println("FINALSTATUS "+statusCode);
                           // Code = statusCode;

                            if (statusCode.equalsIgnoreCase("TXN")) {
                                String message = response.body().getMessage();
                                globalDocumentPath = response.body().getData();
                                listener.onSuccess(globalDocumentPath);
                                // Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                            } else if (statusCode.equalsIgnoreCase("ERR")) {
                                Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Something went to wrong", Toast.LENGTH_SHORT).show();
                                listener.onFailure(new Exception(response.body().getMessage()));
                            }
                        }
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        System.out.println("FINALSTATUS "+e.getMessage());
                        e.printStackTrace();
                        listener.onFailure(e);
                    }
                }

                @Override
                public void onFailure(Call<UploadFile> call, Throwable t) {
                    progressDialog.dismiss();
                    String error = t.getMessage().toString();
                    System.out.println("FINALSTATUS "+t.getMessage());
                    listener.onFailure(new Exception(t.getMessage()));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("FINALSTATUS "+e.getMessage());
        }
    }

    public interface OnResponse {
        void onSuccess(String result);

        void onFailure(Exception e);
    }
}
