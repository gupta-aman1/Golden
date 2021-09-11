package com.example.goldenfish.UserAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goldenfish.Dashboard.HomeDashboardActivity;
import com.example.goldenfish.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    String aadharCard;
    String address;
    Button btnCancel;
    Button btnLogin;
    Button btnOk;
    AlertDialog builder;
    String email;
    EditText etForgetMobile;
    EditText etForgetUsername;
    EditText etUserId;
    EditText etUserPassword;
    String firmName;
    String forgetMobile;
    String forgetUsername;
    String mobileno;
    String msg;
    String outLetId;
    String ownername;
    String panCard;
    TextView tvForgetPassword;
    String userid;
    String username;
    String usertype;
    //WebServiceInterface webServiceInterface;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_login);
        inhitViews();
      //  this.webServiceInterface = (WebServiceInterface) WebServiceInterface.retrofit.create(WebServiceInterface.class);
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!LoginActivity.this.checkInputs()) {
                    return;
                }
                if (LoginActivity.this.checkInternetState()) {
                    //LoginActivity.this.login();
                    startActivity(new Intent(LoginActivity.this,HomeDashboardActivity.class));
                } else {
                    LoginActivity.this.showSnackbar();
                }
            }
        });
        this.tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (LoginActivity.this.checkInternetState()) {
                    LoginActivity.this.forgetPassword();
                } else {
                    LoginActivity.this.showSnackbar();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void forgetPassword() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.forget_password_dialog_layout, (ViewGroup) null, false);
        this.etForgetUsername = (EditText) view1.findViewById(R.id.etForgetUsername);
        this.etForgetMobile = (EditText) view1.findViewById(R.id.etForgetMobile);
        this.btnCancel = (Button) view1.findViewById(R.id.btnCancel);
        this.btnOk = (Button) view1.findViewById(R.id.btnOk);
        AlertDialog create = new AlertDialog.Builder(this).create();
        this.builder = create;
        create.setCancelable(false);
        this.builder.setView(view1);
        this.btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (LoginActivity.this.checkInputsForForgetPassword()) {
                    LoginActivity loginActivity = LoginActivity.this;
                    loginActivity.forgetUsername = loginActivity.etForgetUsername.getText().toString().trim();
                    LoginActivity loginActivity2 = LoginActivity.this;
                    loginActivity2.forgetMobile = loginActivity2.etForgetMobile.getText().toString().trim();
                    LoginActivity loginActivity3 = LoginActivity.this;
                   // loginActivity3.forgetPasswordCallback(loginActivity3.forgetUsername, LoginActivity.this.forgetMobile);
                }
            }
        });
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.builder.dismiss();
            }
        });
        this.builder.show();
    }

    /* access modifiers changed from: private */
   /* public void forgetPasswordCallback(String forgetUsername2, String forgetMobile2) {
        this.webServiceInterface.forgetPassword(forgetUsername2, forgetMobile2).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        new AlertDialog.Builder(LoginActivity.this).setTitle((CharSequence) "Status").setMessage((CharSequence) new JSONObject(String.valueOf(response.body())).getString("response_msg")).setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                LoginActivity.this.builder.dismiss();
                            }
                        }).show();
                    } catch (JSONException e) {
                        new AlertDialog.Builder(LoginActivity.this).setMessage((CharSequence) "Something went wrong!!!").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
                        e.printStackTrace();
                    }
                } else {
                    new AlertDialog.Builder(LoginActivity.this).setMessage((CharSequence) "Something went wrong!!!").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                new AlertDialog.Builder(LoginActivity.this).setMessage((CharSequence) "Something went wrong!!!").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
            }
        });
    }*/

    /* access modifiers changed from: private */
    public boolean checkInputsForForgetPassword() {
        if (TextUtils.isEmpty(this.etForgetUsername.getText())) {
            this.etForgetUsername.setError("Required");
            return false;
        } else if (!TextUtils.isEmpty(this.etForgetMobile.getText())) {
            return true;
        } else {
            this.etForgetMobile.setError("Required");
            return false;
        }
    }

    /* access modifiers changed from: private */
  /*  public void login() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait while logging in...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String userId = this.etUserId.getText().toString().trim();
        String password = this.etUserPassword.getText().toString().trim();
        if (userId.contains("#")) {
            userId = this.userid.replace("#", "%23");
        } else if (userId.contains("&")) {
            userId = this.userid.replace("&", "%26");
        } else if (userId.contains("/")) {
            userId = this.userid.replace("/", "%2F");
        }
        if (password.contains("#")) {
            password = password.replace("#", "%23");
        } else if (password.contains("&")) {
            password = password.replace("&", "%26");
        } else if (password.contains("/")) {
            password = password.replace("/", "%2F");
        }
        RetrofitClient.getInstance().getApi().login(userId, password).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(String.valueOf(response.body()));
                        String statuscode = jsonObject1.getString(NotificationCompat.CATEGORY_STATUS);
                        if (statuscode.equalsIgnoreCase("0")) {
                            progressDialog.dismiss();
                            LoginActivity.this.showOtpDialog();
                            LoginActivity.this.msg = jsonObject1.getString(NotificationCompat.CATEGORY_MESSAGE);
                            Toast.makeText(LoginActivity.this, "OTP sent", 0).show();
                        } else if (statuscode.equalsIgnoreCase(DiskLruCache.VERSION_1)) {
                            progressDialog.dismiss();
                            new AlertDialog.Builder(LoginActivity.this).setTitle((CharSequence) "Alert").setMessage((CharSequence) jsonObject1.getString(NotificationCompat.CATEGORY_MESSAGE)).setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
                        } else {
                            progressDialog.dismiss();
                            new AlertDialog.Builder(LoginActivity.this).setTitle((CharSequence) "Alert").setMessage((CharSequence) "Something went wrong").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        new AlertDialog.Builder(LoginActivity.this).setTitle((CharSequence) "Alert").setMessage((CharSequence) "Something went wrong").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
                    }
                } else {
                    progressDialog.dismiss();
                    new AlertDialog.Builder(LoginActivity.this).setTitle((CharSequence) "Alert").setMessage((CharSequence) "Something went wrong").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                new AlertDialog.Builder(LoginActivity.this).setTitle((CharSequence) "Alert").setMessage((CharSequence) "Something went wrong").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
            }
        });
    }*/

    /* access modifiers changed from: private */
    public void showOtpDialog() {
        View addSenderOTPDialogView = getLayoutInflater().inflate(R.layout.add_sender_otp_dialog, (ViewGroup) null, false);
        final AlertDialog addSenderOTPDialog = new AlertDialog.Builder(this).create();
        ((Window) Objects.requireNonNull(addSenderOTPDialog.getWindow())).setBackgroundDrawable(new ColorDrawable(0));
        addSenderOTPDialog.setCancelable(false);
        addSenderOTPDialog.setView(addSenderOTPDialogView);
        addSenderOTPDialog.show();
        final EditText etOTP = (EditText) addSenderOTPDialogView.findViewById(R.id.et_otp);
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
                if (!TextUtils.isEmpty(etOTP.getText())) {
                   // LoginActivity.this.verifyOtp(etOTP.getText().toString().trim());
                    addSenderOTPDialog.dismiss();
                    return;
                }
                etOTP.setError("Required");
            }
        });
    }

    /* access modifiers changed from: private */
   /* public void verifyOtp(String userOtp) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait while logging in...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RetrofitClient.getInstance().getApi().verifyLoginOtp(userOtp, this.msg).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject responseObject = new JSONObject(String.valueOf(response.body()));
                        if (responseObject.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("0")) {
                            JSONArray dataArray = responseObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jsonObject = dataArray.getJSONObject(i);
                                LoginActivity.this.userid = jsonObject.getString("Id");
                                LoginActivity.this.ownername = jsonObject.getString("OwnerName");
                                LoginActivity.this.username = jsonObject.getString("Username");
                                LoginActivity.this.usertype = jsonObject.getString("Usertype");
                                LoginActivity.this.mobileno = jsonObject.getString("MobileNo1");
                                LoginActivity.this.firmName = jsonObject.getString("FirmName");
                                LoginActivity.this.email = jsonObject.getString("EmailId");
                                LoginActivity.this.address = jsonObject.getString("Address");
                                LoginActivity.this.panCard = jsonObject.getString("PANCard");
                                LoginActivity.this.aadharCard = jsonObject.getString("aadharcard");
                                LoginActivity.this.outLetId = jsonObject.getString("OutletId");
                            }
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit();
                            editor.putString("userid", LoginActivity.this.userid);
                            editor.putString("username", LoginActivity.this.username);
                            editor.putString("ownername", LoginActivity.this.ownername);
                            editor.putString("mobileno", LoginActivity.this.mobileno);
                            editor.putString("usertype", LoginActivity.this.usertype);
                            editor.putString("address", LoginActivity.this.address);
                            editor.putString("email", LoginActivity.this.email);
                            editor.putString("firmName", LoginActivity.this.firmName);
                            editor.putString("panCard", LoginActivity.this.panCard);
                            editor.putString("aadharCard", LoginActivity.this.aadharCard);
                            editor.putString("outletId", LoginActivity.this.outLetId);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, HomeDashboardActivity.class);
                            LoginActivity.this.finish();
                            LoginActivity.this.startActivity(intent);
                            return;
                        }
                        progressDialog.dismiss();
                        new AlertDialog.Builder(LoginActivity.this).setTitle((CharSequence) "Alert").setMessage((CharSequence) "Something went wrong").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        new AlertDialog.Builder(LoginActivity.this).setTitle((CharSequence) "Alert").setMessage((CharSequence) "Something went wrong").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
                    }
                } else {
                    progressDialog.dismiss();
                    new AlertDialog.Builder(LoginActivity.this).setTitle((CharSequence) "Alert").setMessage((CharSequence) "Something went wrong").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                new AlertDialog.Builder(LoginActivity.this).setTitle((CharSequence) "Alert").setMessage((CharSequence) "Something went wrong").setPositiveButton((CharSequence) "Ok", (DialogInterface.OnClickListener) null).show();
            }
        });
    }*/

    private void inhitViews() {
        this.etUserId = (EditText) findViewById(R.id.et_user_id);
        this.etUserPassword = (EditText) findViewById(R.id.et_user_password);
        this.btnLogin = (Button) findViewById(R.id.btn_login);
        this.tvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
    }

    /* access modifiers changed from: private */
    public boolean checkInputs() {
        if (TextUtils.isEmpty(this.etUserId.getText())) {
            this.etUserId.setError("User ID can't be empty.");
            return false;
        } else if (!TextUtils.isEmpty(this.etUserPassword.getText())) {
            return true;
        } else {
            this.etUserPassword.setError("Password can't be empty.");
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void showSnackbar() {
        Snackbar.make(findViewById(R.id.loginLayout), (CharSequence) "No Internet", 100).show();
    }

    /* access modifiers changed from: private */
    public boolean checkInternetState() {

        ConnectivityManager connectivityManager = ((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
       /* NetworkInfo networkInfo = ((ConnectivityManager) getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        if (networkInfo.getType() == 1 || networkInfo.getType() == 0) {
            return true;
        }
        return false;*/
    }

    public void ClickSignup(View view) {
        startActivity(new Intent(LoginActivity.this,SignupUSer.class));
    }
}