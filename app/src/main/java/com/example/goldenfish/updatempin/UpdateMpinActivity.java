package com.example.goldenfish.updatempin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goldenfish.ChangePassword.ChangePasswordActivity;
import com.example.goldenfish.Constants.Constant;
import com.example.goldenfish.Constants.ConstantsValue;
import com.example.goldenfish.R;
import com.example.goldenfish.Retrofit.RetrofitClient;
import com.example.goldenfish.UserAuth.LoginActivity;
import com.example.goldenfish.Utilities.MyUtils;
import com.example.goldenfish.Utilities.SharedPref;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UpdateMpinActivity extends AppCompatActivity {
    EditText et_old,et_new,etrenew;
    SharedPref sharedPref;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mpin);
        sharedPref = SharedPref.getInstance(UpdateMpinActivity.this);
        userid = sharedPref.getStringWithNull(Constant.userId);
        et_old=findViewById(R.id.et_old);
        et_new=findViewById(R.id.et_new);
        etrenew=findViewById(R.id.et_renew);
    }

    public void UpdateMpinBtn(View view) {
        if(et_old.getText().toString().trim().equalsIgnoreCase("") || et_old.getText().length() !=4)
        {
            et_old.setError("Required");
        }
        else if(et_new.getText().toString().trim().equalsIgnoreCase("") || et_new.getText().length() !=4)
        {
            et_new.setError("Required");
        }
        else if(etrenew.getText().toString().trim().equalsIgnoreCase("") || etrenew.getText().length() !=4)
        {
            etrenew.setError("Required");
        }
        else if(!etrenew.getText().toString().trim().equals(et_new.getText().toString().trim()))
        {
            Toast.makeText(UpdateMpinActivity.this, "Mpin Not Match !!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            changeMpinApi();
            //showMpinDialog();
        }
    }

    private void changeMpinApi()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sending Request.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty("OldMpin",et_old.getText().toString().trim());
        jsonObject.addProperty("NewMpin",etrenew.getText().toString().trim());
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("UpdateTransactionMpin",et_old.getText().toString().trim()+"|"+etrenew.getText().toString().trim(),userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().UpdateTransactionMpin(jsonObject);
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
                            // sendPayoutData();
                           // Toast.makeText(UpdateMpinActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(UpdateMpinActivity.this);
                            builder1.setMessage(jsonObject1.getString("Message"));
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            sharedPref.clearData();
                                            startActivity(new Intent(UpdateMpinActivity.this, LoginActivity.class));
                                            finishAffinity();

                                            // finish();
                                        }
                                    });
                            androidx.appcompat.app.AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();

                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(UpdateMpinActivity.this);
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
                            androidx.appcompat.app.AlertDialog alert11 = builder1.create();
                            alert11.show();
                            // Toast.makeText(AddPayoutAcc.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(UpdateMpinActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}