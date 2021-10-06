package com.example.goldenfish.Common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.goldenfish.Constants.Constant;
import com.example.goldenfish.Constants.ConstantsValue;
import com.example.goldenfish.MoveToBank.MoveToBankActivity;
import com.example.goldenfish.R;
import com.example.goldenfish.Retrofit.RetrofitClient;
import com.example.goldenfish.Utilities.MyUtils;
import com.example.goldenfish.Utilities.UploadFile;
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

    public   static void getSurcharge(Activity activity, JsonObject jsonObject,CommonInterface commonInterface)
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