package com.business.goldenfish.ChangePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.business.goldenfish.Constants.Constant;
import com.business.goldenfish.Constants.ConstantsValue;
import com.business.goldenfish.PayoutAc.AddPayoutAcc;
import com.business.goldenfish.R;
import com.business.goldenfish.Retrofit.RetrofitClient;
import com.business.goldenfish.UserAuth.LoginActivity;
import com.business.goldenfish.Utilities.MyUtils;
import com.business.goldenfish.Utilities.SharedPref;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ChangePasswordActivity extends AppCompatActivity {
EditText et_old,et_new,etrenew;
    SharedPref sharedPref;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = SharedPref.getInstance(ChangePasswordActivity.this);
        userid = sharedPref.getStringWithNull(Constant.userId);
        setContentView(R.layout.activity_change_password);
        et_old=findViewById(R.id.et_old);
        et_new=findViewById(R.id.et_new);
        etrenew=findViewById(R.id.et_renew);
    }

    public void ChangePassBtn(View view) {
        if(et_old.getText().toString().trim().equalsIgnoreCase(""))
        {
            et_old.setError("Required");
        }
        else if(et_new.getText().toString().trim().equalsIgnoreCase(""))
        {
            et_new.setError("Required");
        }
        else if(etrenew.getText().toString().trim().equalsIgnoreCase(""))
        {
            etrenew.setError("Required");
        }
        else if(!etrenew.getText().toString().trim().equals(et_new.getText().toString().trim()))
        {
            Toast.makeText(ChangePasswordActivity.this, "Password Not Match !!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            showMpinDialog();
        }

    }


    public void showMpinDialog() {
        View addSenderOTPDialogView = getLayoutInflater().inflate(R.layout.layout_mpin, (ViewGroup) null, false);
        final androidx.appcompat.app.AlertDialog addSenderOTPDialog = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        ((Window) Objects.requireNonNull(addSenderOTPDialog.getWindow())).setBackgroundDrawable(new ColorDrawable(0));
        addSenderOTPDialog.setCancelable(false);
        addSenderOTPDialog.setView(addSenderOTPDialogView);
        addSenderOTPDialog.show();
        final EditText et_mpin = (EditText) addSenderOTPDialogView.findViewById(R.id.et_mpin);
        ((ImageView) addSenderOTPDialogView.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addSenderOTPDialog.dismiss();
            }
        });
        ((Button) addSenderOTPDialogView.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addSenderOTPDialog.dismiss();
            }
        });
        ((Button) addSenderOTPDialogView.findViewById(R.id.btn_submit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_mpin.getText())) {
                    ChangePasswordActivity.this.verifyMpin(et_mpin.getText().toString().trim());
                    addSenderOTPDialog.dismiss();
                    return;
                }
                et_mpin.setError("Required");
            }
        });
    }

    private void verifyMpin(String mpin)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Verifying MPIN.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty("Mpin",mpin);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("ValidateMpinForTransaction",mpin,userid));
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
                            changePassApi();
                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();

                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(ChangePasswordActivity.this);
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
                Toast.makeText(ChangePasswordActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changePassApi()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sending Request.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty("OldPassword",et_old.getText().toString().trim());
        jsonObject.addProperty("NewPassword",etrenew.getText().toString().trim());
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("ChangeUserPassword",et_old.getText().toString().trim()+"|"+etrenew.getText().toString().trim(),userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().ChangeUserPassword(jsonObject);
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
                            sharedPref.clearData();
                            Toast.makeText(ChangePasswordActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                            finishAffinity();
                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();

                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(ChangePasswordActivity.this);
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
                Toast.makeText(ChangePasswordActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}