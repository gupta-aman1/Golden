package com.business.goldenfish.AepsSdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//import com.android.tools.r8.annotations.SynthesizedClassMap;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.business.goldenfish.AepsSdk.device.Opts;
import com.business.goldenfish.AepsSdk.device.Param;
import com.business.goldenfish.AepsSdk.device.PidData;
import com.business.goldenfish.AepsSdk.device.PidOptions;
import com.business.goldenfish.AepsSdk.model.AepsModelRequest;
import com.business.goldenfish.AepsSdk.model.BankModel;
import com.business.goldenfish.AepsSdk.model.ModelAepsResp;
import com.business.goldenfish.AepsSdk.model.ModelMakeAepsTran;
import com.business.goldenfish.AepsSdk.model.ServerModel;
import com.business.goldenfish.AepsSdk.model.ServerModel1;
import com.business.goldenfish.AepsSdk.retrofit.RetrofitClient;
import com.business.goldenfish.Common.CommonApi;
import com.business.goldenfish.Common.CommonFun;
import com.business.goldenfish.Common.CommonInterface;
import com.business.goldenfish.Constants.Constant;
import com.business.goldenfish.Constants.ConstantsValue;
import com.business.goldenfish.MoveToBank.DetailedData;
import com.business.goldenfish.MoveToBank.MoveToBankActivity;
import com.business.goldenfish.MoveToBank.SuceessScreen;
import com.business.goldenfish.PanCard.PurchaseCouponActivity;
import com.business.goldenfish.R;
import com.business.goldenfish.Utilities.GeoLocation;
import com.business.goldenfish.Utilities.GpsInterface;
import com.business.goldenfish.Utilities.GpsListener;
import com.business.goldenfish.Utilities.MyUtils;
import com.business.goldenfish.Utilities.OnDataReceiverListener;
import com.business.goldenfish.Utilities.SharedPref;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import com.wts.wts_aeps_release.device.Opts;
//import com.wts.wts_aeps_release.device.Param;
//import com.wts.wts_aeps_release.device.PidData;
//import com.wts.wts_aeps_release.device.PidOptions;
//import com.wts.wts_aeps_release.model.AepsModelRequest;
//import com.wts.wts_aeps_release.retrofit.RetrofitClient;

//import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
//import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.ResponseBody;
import okhttp3.internal.cache.DiskLruCache;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
//import org.simpleframework.xml.Serializer;
//import org.simpleframework.xml.core.Persister;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.business.goldenfish.AepsSdk.retrofit.RetrofitClient.BASE_URL;
import static com.business.goldenfish.Constants.Constant.userId;

//@SynthesizedClassMap({$$Lambda$WTS_Aeps_Activity$ZLpebXbkZ7iz9qwwbyuBxmnlO5E.class, $$Lambda$WTS_Aeps_Activity$b9Y5cETlWlYAOuoi9kUu5qsNFnU.class, $$Lambda$WTS_Aeps_Activity$rNE41YeG_O7qIJrbVktJtB8VzOw.class})
public class WTS_Aeps_Activity extends AppCompatActivity implements OnDataReceiverListener, GpsInterface,CommonInterface {
    /* access modifiers changed from: private */
    public int MANTRA_CODE = 1;
    /* access modifiers changed from: private */
    public int MORPHO_CODE = 3;
    /* access modifiers changed from: private */
    public int STARTEK_CODE = 2;
    EditText aadhaarET;
    LinearLayout aadhaarPay_Container;
    String aadhaarStr;
    ImageView aadharpay_icon;
    EditText amountET;
    LinearLayout amountLY_Fix;
    String amountStr;
    String app_Id = "";
    String authorise_Key = "";
    TextView avBalanceTT;
    ImageView backPress;
    LinearLayout balanceEnquiry_Container;
    ImageView balance_icon;
    ArrayList<String> bankArray = new ArrayList<>();
    HashMap<String, BankModel> bankMP = new HashMap<>();
    String bankNameStr;
    String bc_Address;
    LinearLayout cashWithdraw_Container;
    ImageView cash_icon;
    TextView chooseBankET;
    LinearLayout chooseBankLY;
    String ci,iCount,pType;
    Context context;
    Button customerDetailBtn;
    String dc;
    LinearLayout deviceLayout;
    String dpId;
    String errCode;
    String errInfo = "";
    String fcount;
    LinearLayout firstLY;
    TextView five1000Btn;
    TextView five2000Btn;
    TextView five3000Btn;
    TextView five5000Btn;
    TextView five500Btn;
    TextView headerTxt;
    String hmac;
    String iinBankStr,bankId;
    double latitude = 0.0;
    LocationManager locationManager;
    double longitude = 0.0;
    RelativeLayout ly;
    LinearLayout mantra_Container;
    ImageView mantra_icon;
    String mc;
    String mi;
    LinearLayout miniStatement_Container;
    ImageView mini_icon;
    EditText mobileET;
    String mobileStr;
    LinearLayout morpho_Container;
    ImageView morpho_icon;
    String nmPoints;
    String pCount;
    String pannoStr = "";
    PidData pidData = null;
    String pidDataStr;
    String printDeviceName;
    Button proceedBtn;
    Button proceedDeviceBtn;
    String qScore;
    String rdsId;
    String rdsVer;
    LinearLayout secondLY;
    String selectedDeviceItem = "";
    String selectedItemtxnType = "";
    String serialNo;
    Serializer serializer = null;
    String sessionKey;
    SpinnerDialog spinnerDialog;
    LinearLayout startek_Container;
    ImageView startek_icon;
    CheckBox terms_and_condition_Check;
    TextView terms_and_condition_Txt;
    String txnTypeNameStr = "",userid,outletId="";
    String walletBalance = "0.00";
    SharedPref sharedPref;
    FusedLocationProviderClient mFusedLocationClient;
    GeoLocation geoLocation;
    private GpsListener gpsListener;
    private boolean isGpsOn;
    private boolean onecall=false;
    private boolean faslecall=false;
    String mobileno="";
    String nameStr="";
    //  String="";
//  String="";
    String address1Str="";
    String firm_name1="";
    String email1="";
    String pan_card1="";
    String pincodeStr="";
    private String Username="",MobileNo1="",Address="",FirmName="",EmailId="",PANCard="",PIN="";

    /* access modifiers changed from: protected */
    @SuppressLint({"SetTextI18n"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_t_s__aeps_);
        sharedPref = SharedPref.getInstance(WTS_Aeps_Activity.this);
        userid = sharedPref.getStringWithNull(userId);
        outletId = sharedPref.getStringWithNull(Constant.OutletId);
        Username = sharedPref.getStringWithNull(Constant.Username);
        MobileNo1 = sharedPref.getStringWithNull(Constant.MobileNo1);
        Address = sharedPref.getStringWithNull(Constant.Address);
        FirmName = sharedPref.getStringWithNull(Constant.FirmName);
        EmailId = sharedPref.getStringWithNull(Constant.EmailId);
        PANCard = sharedPref.getStringWithNull(Constant.PANCard);
        PIN = sharedPref.getStringWithNull(Constant.PIN);


        this.context = this;
        this.cashWithdraw_Container = (LinearLayout) findViewById(R.id.cashWithdraw_Container);
        this.balanceEnquiry_Container = (LinearLayout) findViewById(R.id.balanceEnquiry_Container);
        this.miniStatement_Container = (LinearLayout) findViewById(R.id.miniStatement_Container);
        this.aadhaarPay_Container = (LinearLayout) findViewById(R.id.aadhaarPay_Container);
        this.cash_icon = (ImageView) findViewById(R.id.cash_icon);
        this.balance_icon = (ImageView) findViewById(R.id.balance_icon);
        this.mini_icon = (ImageView) findViewById(R.id.mini_icon);
        this.aadharpay_icon = (ImageView) findViewById(R.id.aadharpay_icon);
        this.proceedBtn = (Button) findViewById(R.id.proceedBtn);
        this.ly = (RelativeLayout) findViewById(R.id.ly);
        this.firstLY = (LinearLayout) findViewById(R.id.firstLY);
        this.secondLY = (LinearLayout) findViewById(R.id.secondLY);
        this.deviceLayout = (LinearLayout) findViewById(R.id.deviceLayout);
        this.chooseBankLY = (LinearLayout) findViewById(R.id.chooseBankLY);
        this.avBalanceTT = (TextView) findViewById(R.id.avBalanceTT);
        this.proceedDeviceBtn = (Button) findViewById(R.id.proceedDeviceBtn);
        this.terms_and_condition_Check = (CheckBox) findViewById(R.id.terms_and_condition_Check);
        this.terms_and_condition_Txt = (TextView) findViewById(R.id.terms_and_condition_Txt);
        this.chooseBankET = (TextView) findViewById(R.id.chooseBankET);
        this.mobileET = (EditText) findViewById(R.id.mobileET);
        this.aadhaarET = (EditText) findViewById(R.id.aadhaarET);
        this.amountET = (EditText) findViewById(R.id.amountET);
        this.customerDetailBtn = (Button) findViewById(R.id.nextBtn);
        this.proceedBtn = (Button) findViewById(R.id.proceedBtn);
//System.out.println("");
        this.morpho_Container = (LinearLayout) findViewById(R.id.morpho_Container);
        this.startek_Container = (LinearLayout) findViewById(R.id.startek_Container);
        this.mantra_Container = (LinearLayout) findViewById(R.id.mantra_Container);
        this.morpho_icon = (ImageView) findViewById(R.id.morpho_icon);
        this.startek_icon = (ImageView) findViewById(R.id.startek_icon);
        this.mantra_icon = (ImageView) findViewById(R.id.mantra_icon);
        this.backPress = (ImageView) findViewById(R.id.backPress);
        this.headerTxt = (TextView) findViewById(R.id.headerTxt);
        this.amountLY_Fix = (LinearLayout) findViewById(R.id.amountLY_Fix);
        this.five500Btn = (TextView) findViewById(R.id.five500Btn);
        this.five1000Btn = (TextView) findViewById(R.id.five1000Btn);
        this.five2000Btn = (TextView) findViewById(R.id.five2000Btn);
        this.five3000Btn = (TextView) findViewById(R.id.five3000Btn);
        this.five5000Btn = (TextView) findViewById(R.id.five5000Btn);
        this.serializer = new Persister();
        this.app_Id = getIntent().getStringExtra("app_Id");
        this.authorise_Key = getIntent().getStringExtra("authorise_Key");
        this.walletBalance = getIntent().getStringExtra("main_wallet_bal");
        this.pannoStr = getIntent().getStringExtra("panno");
        this.latitude = getIntent().getDoubleExtra("lat",0.0);
        this.longitude = getIntent().getDoubleExtra("long",0.0);
        System.out.println("Latititude "+latitude+ " | "+"Longitudee "+longitude);

       // registerForNewUser();
        if (this.app_Id.equalsIgnoreCase("") || this.authorise_Key.equalsIgnoreCase("") || this.pannoStr.equalsIgnoreCase("")) {
          //  if (this.app_Id.equalsIgnoreCase("") || this.authorise_Key.equalsIgnoreCase("") || this.pannoStr.equalsIgnoreCase("") || this.latitude ==0.0 || this.longitude==0.0 || this.walletBalance.equalsIgnoreCase("0.00")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Info!!!");
            alert.setMessage("Your Intent value is missing please check the intent value before open SDK");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    WTS_Aeps_Activity.this.finish();
                }
            });
            alert.setCancelable(false);
            alert.show();
        } else {
            getBank();
            TextView textView = this.avBalanceTT;
            textView.setText("₹ " + this.walletBalance);
        }
        this.five500Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WTS_Aeps_Activity.this.amountET.setText("500");
            }
        });
        this.five1000Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WTS_Aeps_Activity.this.amountET.setText("1000");
            }
        });
        this.five2000Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WTS_Aeps_Activity.this.amountET.setText("2000");
            }
        });
        this.five3000Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WTS_Aeps_Activity.this.amountET.setText("3000");
            }
        });
        this.five5000Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WTS_Aeps_Activity.this.amountET.setText("5000");
            }
        });
        detailInfoCode();
        firstLyCode();
        deviceCode();
        this.backPress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WTS_Aeps_Activity.this.onBackPressed();
            }
        });

        geoLocation= new GeoLocation(WTS_Aeps_Activity.this, WTS_Aeps_Activity.this);
        IntentFilter mfilter = new IntentFilter(
                "android.location.PROVIDERS_CHANGED");
        gpsListener = new GpsListener(WTS_Aeps_Activity.this, this);
        registerReceiver(gpsListener, mfilter);

    }
    private void registerForNewUser() {

        final AlertDialog alertDialog = new AlertDialog.Builder(WTS_Aeps_Activity.this).create();
        final LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.layout_user_info, null);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button registerBeneBtn = convertView.findViewById(R.id.registerBeneBtn);
        ImageView close = convertView.findViewById(R.id.close);
        final EditText mobileNumberET = convertView.findViewById(R.id.mobileNumberET);
        //  final EditText aadhaarET = convertView.findViewById(R.id.aadhaarET);
        final EditText nameET = convertView.findViewById(R.id.nameET);
        //  final EditText alernamteMobileET = convertView.findViewById(R.id.alernamteMobileET);
        final EditText address1 = convertView.findViewById(R.id.address1);
        final EditText firm_name = convertView.findViewById(R.id.firm_name);
        final EditText email = convertView.findViewById(R.id.email);
        final EditText pan_card = convertView.findViewById(R.id.pan_card);
        //stateET = convertView.findViewById(R.id.stateET);
        //cityET = convertView.findViewById(R.id.cityET);
        final EditText pincodeET = convertView.findViewById(R.id.pincodeET);
      //  mobileNumberET.setText(mobile);
        mobileNumberET.setEnabled(false);
        mobileNumberET.setText(MobileNo1);
        nameET.setText(Username);
        address1.setText(Address);
        firm_name.setText(FirmName);
        email.setText(EmailId);
        pan_card.setText(PANCard);
        pincodeET.setText(PIN);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        registerBeneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileno = mobileNumberET.getText().toString().trim();
                nameStr = nameET.getText().toString().trim();
               // String aadhaarStr = aadhaarET.getText().toString();
               // String alternateMobileStr = alernamteMobileET.getText().toString();
                address1Str = address1.getText().toString().trim();
                firm_name1 = firm_name.getText().toString().trim();
                email1 = email.getText().toString().trim();
                pan_card1 = pan_card.getText().toString().trim();
                pincodeStr = pincodeET.getText().toString().trim();
                // String dobStr = dateofBirth.getText().toString();
                if (mobileno.equalsIgnoreCase("") || mobileno.equals("null")){
                    mobileNumberET.setError("required");
                }else if (nameStr.equalsIgnoreCase("") || nameStr.equals("null")){
                    nameET.setError("required");
                }
                else if (address1Str.equalsIgnoreCase("") || address1Str.equals("null")){
                    address1.setError("required");
                }else if (firm_name1.equalsIgnoreCase("") || firm_name1.equals("null")){
                    firm_name.setError("required");
                }else if (pincodeStr.equalsIgnoreCase("") || pincodeStr.equals("null")){
                    pincodeET.setError("required");
                }
                else if (email1.equalsIgnoreCase("") || email1.equals("null")){
                    email.setError("required");
                }
                else if (pan_card1.equalsIgnoreCase("") || pan_card1.equals("null")){
                    pan_card.setError("required");
                }
                else if (address1Str.length() < 4){
                    address1.setError("Minimum character are 4");
                }
                else {
                  //  Toast.makeText(WTS_Aeps_Activity.this, "hello", Toast.LENGTH_SHORT).show();
                    nowRegisterUser(alertDialog,mobileno,nameStr,address1Str,firm_name1,email1,pan_card1,pincodeStr);
                }
            }
        });
        alertDialog.setView(convertView);
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    private void nowRegisterUser(final AlertDialog alertDialog,final String mobileStr,final String nameStr, String address1Str, String address2Str, final String stateStr, String cityStr, String pincodeStr) {

        alertDialog.dismiss();
        CommonApi.sendOTP(this,userId,mobileStr,WTS_Aeps_Activity.this);


    }

    @Override
    public void OTPtatus(boolean status) {
        if(status == true)
        {

            CommonFun.showOTPDialog(WTS_Aeps_Activity.this,WTS_Aeps_Activity.this);
        }
    }

    @Override
    public void getEnteredOTP(String otp) {
        VerifyingData(otp);

    }
    private void VerifyingData(String otp)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Verifying OTP .....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty("Mobile",mobileno);
        jsonObject.addProperty("OTP",otp);
        jsonObject.addProperty("UserName",nameStr);
        jsonObject.addProperty("Address",address1Str);
        jsonObject.addProperty("Pincode",pincodeStr);
        jsonObject.addProperty("Company",firm_name1);
        jsonObject.addProperty("EmailId",email1);
        jsonObject.addProperty("Pancard",pan_card1);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("ValidateOTPForOutlet",mobileno+"|"+otp,userid));
        Call<ResponseBody> call = com.business.goldenfish.Retrofit.RetrofitClient.getInstance().getApi().ValidateOTPForOutlet(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    try {

                        fullRes = response.body().string();
                        System.out.println("FINAL RESULT "+fullRes);
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            String Data= jsonObject1.getString("Data");
                            sharedPref.putString(Constant.OutletId,Data);
                            finish();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(WTS_Aeps_Activity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(WTS_Aeps_Activity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deviceCode() {
        this.morpho_Container.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(WTS_Aeps_Activity.this.morpho_Container)) {
                    WTS_Aeps_Activity.this.selectedDeviceItem = "Morpho";
                    WTS_Aeps_Activity.this.morpho_icon.setVisibility(View.VISIBLE);
                    WTS_Aeps_Activity.this.startek_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.mantra_icon.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.startek_Container.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(WTS_Aeps_Activity.this.startek_Container)) {
                    WTS_Aeps_Activity.this.selectedDeviceItem = "Startek";
                    WTS_Aeps_Activity.this.morpho_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.startek_icon.setVisibility(View.VISIBLE);
                    WTS_Aeps_Activity.this.mantra_icon.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.mantra_Container.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(WTS_Aeps_Activity.this.mantra_Container)) {
                    WTS_Aeps_Activity.this.selectedDeviceItem = "Mantra";
                    WTS_Aeps_Activity.this.morpho_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.startek_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.mantra_icon.setVisibility(View.VISIBLE);
                }
            }
        });

        this.proceedDeviceBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (WTS_Aeps_Activity.this.selectedDeviceItem.equalsIgnoreCase("")) {
                    WTS_Aeps_Activity.this.snackbar("Please select device");
                } else if (WTS_Aeps_Activity.this.selectedDeviceItem.equalsIgnoreCase("Morpho")) {
                    try {
                        WTS_Aeps_Activity.this.printDeviceName = "Morpho";
                        String pidOption = WTS_Aeps_Activity.this.getPIDOptions();
                        Log.e("pid_option_value", pidOption);
                        if (pidOption != null) {
                            Log.e("PidOptions", pidOption);
                            Intent intent2 = new Intent();
                            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
                            intent2.putExtra("PID_OPTIONS", pidOption);
                            WTS_Aeps_Activity.this.startActivityForResult(intent2, WTS_Aeps_Activity.this.MORPHO_CODE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("NotInstall", e.toString());
                        WTS_Aeps_Activity wTS_Aeps_Activity = WTS_Aeps_Activity.this;
                        wTS_Aeps_Activity.appNotInstall(wTS_Aeps_Activity.printDeviceName);
                    }
                } else if (WTS_Aeps_Activity.this.selectedDeviceItem.equalsIgnoreCase("Startek")) {
                    try {
                        WTS_Aeps_Activity.this.printDeviceName = "Startek";
                        String pidOption2 = WTS_Aeps_Activity.this.getPIDOptions();
                        Log.e("pid_option_value", pidOption2);
                        if (pidOption2 != null) {
                            Log.e("PidOptions", pidOption2);
                            Intent intent22 = new Intent();
                            intent22.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
                            intent22.putExtra("PID_OPTIONS", pidOption2);
                            WTS_Aeps_Activity.this.startActivityForResult(intent22, WTS_Aeps_Activity.this.STARTEK_CODE);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Log.e("NotInstall", e2.toString());
                        WTS_Aeps_Activity wTS_Aeps_Activity2 = WTS_Aeps_Activity.this;
                        wTS_Aeps_Activity2.appNotInstall(wTS_Aeps_Activity2.printDeviceName);
                    }
                } else if (WTS_Aeps_Activity.this.selectedDeviceItem.equalsIgnoreCase("Mantra")) {
                    try {
                        WTS_Aeps_Activity.this.printDeviceName = "Mantra";
                        String pidOption3 = WTS_Aeps_Activity.this.getPIDOptions();
                        Log.e("pid_option_value", pidOption3);
                        if (pidOption3 != null) {
                            Log.e("PidOptions", pidOption3);
                            Intent intent23 = new Intent();
                            intent23.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
                            intent23.putExtra("PID_OPTIONS", pidOption3);
                            WTS_Aeps_Activity.this.startActivityForResult(intent23, WTS_Aeps_Activity.this.MANTRA_CODE);
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        Log.e("NotInstall", e3.toString());
                        WTS_Aeps_Activity wTS_Aeps_Activity3 = WTS_Aeps_Activity.this;
                        wTS_Aeps_Activity3.appNotInstall(wTS_Aeps_Activity3.printDeviceName);
                    }
                }
            }
        });
    }

    private void firstLyCode() {
        this.cashWithdraw_Container.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(WTS_Aeps_Activity.this.cashWithdraw_Container)) {
                    WTS_Aeps_Activity.this.selectedItemtxnType = "Cash Withdraw";
                    WTS_Aeps_Activity.this.cash_icon.setVisibility(View.VISIBLE);
                    WTS_Aeps_Activity.this.balance_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.mini_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.aadharpay_icon.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.balanceEnquiry_Container.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(WTS_Aeps_Activity.this.balanceEnquiry_Container)) {
                    WTS_Aeps_Activity.this.selectedItemtxnType = "Balance Enquiry";
                    WTS_Aeps_Activity.this.cash_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.balance_icon.setVisibility(View.VISIBLE);
                    WTS_Aeps_Activity.this.mini_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.aadharpay_icon.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.miniStatement_Container.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(WTS_Aeps_Activity.this.miniStatement_Container)) {
                    WTS_Aeps_Activity.this.selectedItemtxnType = "Mini Statement";
                    WTS_Aeps_Activity.this.cash_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.balance_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.mini_icon.setVisibility(View.VISIBLE);
                    WTS_Aeps_Activity.this.aadharpay_icon.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.aadhaarPay_Container.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(WTS_Aeps_Activity.this.aadhaarPay_Container)) {
                    WTS_Aeps_Activity.this.selectedItemtxnType = "Aadhaar Pay";
                    WTS_Aeps_Activity.this.cash_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.balance_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.mini_icon.setVisibility(View.INVISIBLE);
                    WTS_Aeps_Activity.this.aadharpay_icon.setVisibility(View.VISIBLE);
                }
            }
        });
        this.proceedBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n"})
            public void onClick(View v) {
               // outletId=null;
               // System.out.println("OUTLET ID "+outletId);
                if(!outletId.equalsIgnoreCase("") && !outletId.equals("null") && outletId != null) {
                    //Toast.makeText(WTS_Aeps_Activity.this, "hello", Toast.LENGTH_SHORT).show();
                    if (WTS_Aeps_Activity.this.selectedItemtxnType.equalsIgnoreCase("Cash Withdraw")) {
                        WTS_Aeps_Activity.this.selectedItemtxnType = "WAP";
                        WTS_Aeps_Activity.this.txnTypeNameStr = "Cash Withdraw";
                        WTS_Aeps_Activity.this.amountET.setVisibility(View.VISIBLE);
                        WTS_Aeps_Activity.this.amountLY_Fix.setVisibility(View.VISIBLE);
                        WTS_Aeps_Activity.this.firstLY.setVisibility(View.GONE);
                        WTS_Aeps_Activity.this.secondLY.setVisibility(View.VISIBLE);
                        WTS_Aeps_Activity.this.headerTxt.setText("Cash Withdraw");
                    } else if (WTS_Aeps_Activity.this.selectedItemtxnType.equalsIgnoreCase("Balance Enquiry")) {
                        WTS_Aeps_Activity.this.selectedItemtxnType = "BAP";
                        WTS_Aeps_Activity.this.txnTypeNameStr = "Balance Enquiry";
                        WTS_Aeps_Activity.this.amountET.setVisibility(View.GONE);
                        WTS_Aeps_Activity.this.amountLY_Fix.setVisibility(View.GONE);
                        WTS_Aeps_Activity.this.firstLY.setVisibility(View.GONE);
                        WTS_Aeps_Activity.this.secondLY.setVisibility(View.VISIBLE);
                        WTS_Aeps_Activity.this.headerTxt.setText("Balance Enquiry");
                    } else if (WTS_Aeps_Activity.this.selectedItemtxnType.equalsIgnoreCase("Mini Statement")) {
                        WTS_Aeps_Activity.this.selectedItemtxnType = "SAP";
                        WTS_Aeps_Activity.this.txnTypeNameStr = "Mini Statement";
                        WTS_Aeps_Activity.this.amountET.setVisibility(View.GONE);
                        WTS_Aeps_Activity.this.amountLY_Fix.setVisibility(View.GONE);
                        WTS_Aeps_Activity.this.firstLY.setVisibility(View.GONE);
                        WTS_Aeps_Activity.this.secondLY.setVisibility(View.VISIBLE);
                        WTS_Aeps_Activity.this.headerTxt.setText("Mini Statement");
                    } else if (WTS_Aeps_Activity.this.selectedItemtxnType.equalsIgnoreCase("Aadhaar Pay")) {
                        WTS_Aeps_Activity.this.selectedItemtxnType = "MZZ";
                        WTS_Aeps_Activity.this.txnTypeNameStr = "Aadhaar Pay";
                        WTS_Aeps_Activity.this.amountET.setVisibility(View.VISIBLE);
                        WTS_Aeps_Activity.this.amountLY_Fix.setVisibility(View.VISIBLE);
                        WTS_Aeps_Activity.this.firstLY.setVisibility(View.GONE);
                        WTS_Aeps_Activity.this.secondLY.setVisibility(View.VISIBLE);
                        WTS_Aeps_Activity.this.headerTxt.setText("Aadhaar Pay");
                    } else {
                        WTS_Aeps_Activity.this.snackbar("Please select your preference");
                    }
                }
                else
                {
                    registerForNewUser();
                }
            }
        });
    }

    private void detailInfoCode() {
        this.customerDetailBtn.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                WTS_Aeps_Activity.this.lambda$detailInfoCode$0$WTS_Aeps_Activity(view);
            }
        });
        this.chooseBankLY.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                WTS_Aeps_Activity.this.lambda$detailInfoCode$1$WTS_Aeps_Activity(view);
            }
        });
    }

    public /* synthetic */ void lambda$detailInfoCode$0$WTS_Aeps_Activity(View v) {
        this.mobileStr = this.mobileET.getText().toString().trim();
        String bank = this.chooseBankET.getText().toString().trim();
        this.aadhaarStr = this.aadhaarET.getText().toString().trim();
        String amount = this.amountET.getText().toString().trim();
        if (latitude == 0.0 || longitude == 0.0)
        {
            OnGPS();
        }
        else if (this.mobileStr.equalsIgnoreCase("") || this.mobileStr.length()!=10) {
            snackbar("Enter mobile number");
        } else if (this.aadhaarStr.equalsIgnoreCase("") || this.aadhaarStr.length()!=12) {
            snackbar("Enter aadhaar number");
        } else if (bank.equalsIgnoreCase("")) {
            snackbar("Select Bank");
        } else if (!this.selectedItemtxnType.equalsIgnoreCase("WAP") && !this.selectedItemtxnType.equalsIgnoreCase("MZZ")) {
            this.amountStr = "0";
            this.firstLY.setVisibility(View.GONE);
            this.secondLY.setVisibility(View.GONE);
            this.deviceLayout.setVisibility(View.VISIBLE);
        } else if (amount.equalsIgnoreCase("")) {
            snackbar("Please enter amount");
        } else {
            this.amountStr = amount;
            this.firstLY.setVisibility(View.GONE);
            this.secondLY.setVisibility(View.GONE);
            this.deviceLayout.setVisibility(View.VISIBLE);
        }
    }

    public /* synthetic */ void lambda$detailInfoCode$1$WTS_Aeps_Activity(View v) {
        try {
            this.spinnerDialog.showSpinerDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void snackbar(String s) {
        Snackbar.make((View) this.ly, (CharSequence) s, 10000).show();
    }

    private void getTransactionNow() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ServerModel serverModel= new ServerModel();
        serverModel.setUserid(userid);
        serverModel.setChecksum(MyUtils.encryption("MakeAepsTransaction", this.mobileET.getText().toString().trim()+"|"+this.aadhaarET.getText().toString().trim()+"|"+this.bankId+"|"+this.iinBankStr+"|"+this.amountET.getText().toString().trim(), userid));
        serverModel.setMobile(this.mobileET.getText().toString().trim());
        serverModel.setAadhaarUid(this.aadhaarET.getText().toString().trim());
        serverModel.setTransactionType(this.selectedItemtxnType);
        serverModel.setBankId(this.bankId);
        serverModel.setBankIIN(this.iinBankStr);
        serverModel.setAmount(this.amountStr);
        serverModel.setLatitude(String.valueOf(this.latitude));
        serverModel.setLongitude(String.valueOf(this.longitude));
        serverModel.setPidDataType("X");
        serverModel.setPidData(this.pidDataStr);
        serverModel.setCi(this.ci);
        serverModel.setDc(this.dc);
        serverModel.setDpId(this.dpId);
        serverModel.setErrCode(this.errCode);
        serverModel.setErrInfo(this.errInfo);
        serverModel.setfCount(this.fcount);
        serverModel.settType("null");
        serverModel.sethMac(this.hmac);
        serverModel.setiCount(this.iCount);
        serverModel.setMc(this.mc);
        serverModel.setMi(this.mi);
        serverModel.setnMPoints(this.nmPoints);
        serverModel.setpCount(this.pCount);
        serverModel.setpType(this.pType);
        serverModel.setqScore(this.qScore);
        serverModel.setrDSId(this.rdsId);
        serverModel.setrDSVer(this.rdsVer);
        serverModel.setSessionKey(this.sessionKey);
        serverModel.setSrno(this.serialNo);
        Gson gson = new Gson();
        String json = gson.toJson(serverModel);
        JSONObject mJSONObject=null;
        JsonObject gsonObject=null;
        try {
             mJSONObject = new JSONObject(json);
             System.out.println("JSON FINAl "+mJSONObject);

            org.json.JSONObject object =mJSONObject;
            JsonParser jsonParser = new JsonParser();
             gsonObject = (JsonObject)jsonParser.parse(object.toString());
        } catch (JSONException e) {
            //e.printStackTrace();
            Toast.makeText(WTS_Aeps_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //  System.out.println("REQ "+aepsModelRequest);
        RetrofitClient.getInstance().getApi().MakeAepsTransaction(gsonObject).enqueue(new Callback<JsonObject>() {

            public void onResponse(retrofit2.Call<com.google.gson.JsonObject> r35, retrofit2.Response<com.google.gson.JsonObject> r36) {
                if (r36.isSuccessful()) {
                    try {
                       // JSONObject responseJsonObject = new JSONObject(String.valueOf(r36.body()));
                        System.out.println("MY RESP "+String.valueOf(r36.body()));
                      //  System.out.println("MY RESP1 "+selectedItemtxnType);
                        Gson gson = new Gson();
                        ModelAepsResp m = gson.fromJson(String.valueOf(r36.body()), ModelAepsResp.class);

                           // ArrayList<ModelMakeAepsTran> modelMakeAepsTrans = new ArrayList<>();

                            if (m.getStatuscode().equalsIgnoreCase("TXN")) {

                                ArrayList<DetailedData> array = new ArrayList<DetailedData>();

                            /*    if(selectedItemtxnType.equalsIgnoreCase("BAP")|| selectedItemtxnType.equalsIgnoreCase("WAP") || selectedItemtxnType.equalsIgnoreCase("MZZ") )
                                {*/

                                    JSONObject jsonObject1= new JSONObject(String.valueOf(r36.body()));

                                    JSONArray out_arr= jsonObject1.getJSONArray("Data");

                                    for (int i = 0; i < out_arr.length(); i++) {

                                        JSONObject inn_obj = out_arr.getJSONObject(i);

                                        Iterator key = inn_obj.keys();
                                        while (key.hasNext()) {
                                            String k = key.next().toString();
                                            System.out.println("Key : " + k + ", value : "
                                                    + inn_obj.getString(k));

                                            if(k.equalsIgnoreCase("ReqDate"))
                                            {
                                                SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                                // SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a"); //here 'a' for AM/PM
                                                SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

                                                Date date = null;
                                                try {
                                                    date = sourceFormat.parse(inn_obj.getString(k));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                String formattedDate = destFormat.format(date);
                                                array.add(new DetailedData(k,formattedDate.replace("am", "AM").replace("pm","PM")));
                                            }
                                            else {
                                                array.add(new DetailedData(k, inn_obj.getString(k)));
                                            }
                                        //    array.add(new DetailedData(k, inn_obj.getString(k)));
                                        }
                                    }
                                    System.out.println("Array Data "+array);
                                    Intent intent = new Intent(WTS_Aeps_Activity.this, SuceessScreen.class);
                                    intent.putExtra("list_data", new Gson().toJson(array));
                                    intent.putExtra("Head1",m.getHead1());
                                    intent.putExtra("Head2",m.getHead2());
                                    intent.putExtra("Head3",m.getHead3());
                                    intent.putExtra("type","success");
                                     intent.putExtra("service",selectedItemtxnType);
                                    if(selectedItemtxnType.equalsIgnoreCase("SAP"))
                                     {
                                        // intent.putExtra("service",selectedItemtxnType);
                                     }
                                    startActivity(intent);

                               /* startActivity(new Intent(WTS_Aeps_Activity.this, SuccessScreen_Aeps.class)
                                        .putExtra("status_code",m.getStatuscode())
                                        .putExtra("head1", m.getHead1())
                                        .putExtra("head2", m.getHead2())
                                        .putExtra("head3", m.getHead3())
                                        .putExtra("id", String.valueOf(m.getData().get(0).getId()))
                                        .putExtra("txn_type",String.valueOf(m.getData().get(0).getTxntype()))
                                        .putExtra("bc_id",String.valueOf(m.getData().get(0).getBcId()))
                                        .putExtra("bank_name",String.valueOf(m.getData().get(0).getBankName()))
                                        .putExtra("op_name",String.valueOf(m.getData().get(0).getOpname()))
                                        .putExtra("order_id",String.valueOf(m.getData().get(0).getOrderId()))
                                        .putExtra("txn_id",String.valueOf(m.getData().get(0).getTransactionId()))
                                        .putExtra("amount",String.valueOf(m.getData().get(0).getAmount()))
                                        .putExtra("comm",String.valueOf(m.getData().get(0).getCommission()))
                                        .putExtra("mobile",String.valueOf(m.getData().get(0).getMobileno()))
                                        .putExtra("aadhaar",String.valueOf(m.getData().get(0).getAadharNumber()))
                                        .putExtra("ministmt",String.valueOf(m.getData().get(0).getMiniSmt()))
                                        .putExtra("reason",String.valueOf(m.getData().get(0).getReason()))
                                        .putExtra("status",String.valueOf(m.getData().get(0).getStatus()))
                                        .putExtra("req_date",String.valueOf(m.getData().get(0).getReqDate()))
                                        .putExtra("acc_bal",String.valueOf(m.getData().get(0).getAccountBal()))
                                );*/
                           // }

                               /* else
                                {

                                    Intent intent = new Intent(WTS_Aeps_Activity.this, SuceessScreen.class);
                                    intent.putExtra("list_data", new Gson().toJson(array));
                                    intent.putExtra("Head1",m.getHead1());
                                    intent.putExtra("Head2",m.getHead2());
                                    intent.putExtra("Head3",m.getHead3());
                                    intent.putExtra("type","success");
                                    intent.putExtra("service","mini");
                                    startActivity(intent);

                                   /* startActivity(new Intent(WTS_Aeps_Activity.this, MiniStatement_Success.class)
                                            .putExtra("status_code",m.getStatuscode())
                                            .putExtra("head1", m.getHead1())
                                            .putExtra("head2", m.getHead2())
                                            .putExtra("head3", m.getHead3())
                                            .putExtra("id", String.valueOf(m.getData().get(0).getId()))
                                            .putExtra("txn_type",String.valueOf(m.getData().get(0).getTxntype()))
                                            .putExtra("bc_id",String.valueOf(m.getData().get(0).getBcId()))
                                            .putExtra("bank_name",String.valueOf(m.getData().get(0).getBankName()))
                                            .putExtra("op_name",String.valueOf(m.getData().get(0).getOpname()))
                                            .putExtra("order_id",String.valueOf(m.getData().get(0).getOrderId()))
                                            .putExtra("txn_id",String.valueOf(m.getData().get(0).getTransactionId()))
                                            .putExtra("amount",String.valueOf(m.getData().get(0).getAmount()))
                                            .putExtra("comm",String.valueOf(m.getData().get(0).getCommission()))
                                            .putExtra("mobile",String.valueOf(m.getData().get(0).getMobileno()))
                                            .putExtra("aadhaar",String.valueOf(m.getData().get(0).getAadharNumber()))
                                            .putExtra("ministmt",String.valueOf(m.getData().get(0).getMiniSmt()))
                                            .putExtra("reason",String.valueOf(m.getData().get(0).getReason()))
                                            .putExtra("status",String.valueOf(m.getData().get(0).getStatus()))
                                            .putExtra("req_date",String.valueOf(m.getData().get(0).getReqDate()))
                                            .putExtra("acc_bal",String.valueOf(m.getData().get(0).getAccountBal()))
                                    );*/
                                //}*/
                        }
                            else
                            {
                                Toast.makeText(WTS_Aeps_Activity.this, ""+m.getMessage(), Toast.LENGTH_SHORT).show();
                                ArrayList<DetailedData> array = new ArrayList<DetailedData>();
                                JSONObject jsonObject1= new JSONObject(String.valueOf(r36.body()));

                                JSONArray out_arr= jsonObject1.getJSONArray("Data");

                                for (int i = 0; i < out_arr.length(); i++) {

                                    JSONObject inn_obj = out_arr.getJSONObject(i);

                                    Iterator key = inn_obj.keys();
                                    while (key.hasNext()) {
                                        String k = key.next().toString();
                                        System.out.println("Key : " + k + ", value : "
                                                + inn_obj.getString(k));

                                        if(k.equalsIgnoreCase("ReqDate"))
                                        {
                                            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                            // SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a"); //here 'a' for AM/PM
                                            SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

                                            Date date = null;
                                            try {
                                                date = sourceFormat.parse(inn_obj.getString(k));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            String formattedDate = destFormat.format(date);
                                            array.add(new DetailedData(k,formattedDate.replace("am", "AM").replace("pm","PM")));
                                        }
                                        else {
                                            array.add(new DetailedData(k, inn_obj.getString(k)));
                                        }
                                        //    array.add(new DetailedData(k, inn_obj.getString(k)));
                                    }
                                }
                                System.out.println("Array Data "+array);
                                Intent intent = new Intent(WTS_Aeps_Activity.this, SuceessScreen.class);
                                intent.putExtra("list_data", new Gson().toJson(array));
                                intent.putExtra("Head1",m.getHead1());
                                intent.putExtra("Head2",m.getHead2());
                                intent.putExtra("Head3",m.getHead3());
                                intent.putExtra("type","failed");
                                intent.putExtra("service",selectedItemtxnType);

                                if(selectedItemtxnType.equalsIgnoreCase("SAP"))
                                {
                                   // intent.putExtra("service",selectedItemtxnType);
                                }
                                startActivity(intent);
                                /*if(selectedItemtxnType.equalsIgnoreCase("BAP")|| selectedItemtxnType.equalsIgnoreCase("WAP") || selectedItemtxnType.equalsIgnoreCase("MZZ") )
                                {
                                    ArrayList<DetailedData> array = new ArrayList<DetailedData>();

                                    JSONObject jsonObject1= new JSONObject(String.valueOf(r36.body()));

                                    JSONArray out_arr= jsonObject1.getJSONArray("Data");

                                    for (int i = 0; i < out_arr.length(); i++) {

                                        JSONObject inn_obj = out_arr.getJSONObject(i);

                                        Iterator key = inn_obj.keys();
                                        while (key.hasNext()) {
                                            String k = key.next().toString();
                                            System.out.println("Key : " + k + ", value : "
                                                    + inn_obj.getString(k));

                                            if(k.equalsIgnoreCase("ReqDate"))
                                            {
                                                SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                                // SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a"); //here 'a' for AM/PM
                                                SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

                                                Date date = null;
                                                try {
                                                    date = sourceFormat.parse(inn_obj.getString(k));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                String formattedDate = destFormat.format(date);
                                                array.add(new DetailedData(k,formattedDate.replace("am", "AM").replace("pm","PM")));
                                            }
                                            else {
                                                array.add(new DetailedData(k, inn_obj.getString(k)));
                                            }
                                           // array.add(new DetailedData(k, inn_obj.getString(k)));
                                        }
                                    }
                                    Intent intent = new Intent(WTS_Aeps_Activity.this, SuceessScreen.class);
                                    intent.putExtra("list_data", new Gson().toJson(array));
                                    intent.putExtra("Head1",m.getHead1());
                                    intent.putExtra("Head2",m.getHead2());
                                    intent.putExtra("Head3",m.getHead3());
                                    intent.putExtra("type","failed");
                                    startActivity(intent);
                                    /*startActivity(new Intent(WTS_Aeps_Activity.this, SuccessScreen_Aeps.class)
                                            .putExtra("status_code",m.getStatuscode())
                                            .putExtra("head1", m.getHead1())
                                            .putExtra("head2", m.getHead2())
                                            .putExtra("head3", m.getHead3())
                                            .putExtra("id", String.valueOf(m.getData().get(0).getId()))
                                            .putExtra("txn_type",String.valueOf(m.getData().get(0).getTxntype()))
                                            .putExtra("bc_id",String.valueOf(m.getData().get(0).getBcId()))
                                            .putExtra("bank_name",String.valueOf(m.getData().get(0).getBankName()))
                                            .putExtra("op_name",String.valueOf(m.getData().get(0).getOpname()))
                                            .putExtra("order_id",String.valueOf(m.getData().get(0).getOrderId()))
                                            .putExtra("txn_id",String.valueOf(m.getData().get(0).getTransactionId()))
                                            .putExtra("amount",String.valueOf(m.getData().get(0).getAmount()))
                                            .putExtra("comm",String.valueOf(m.getData().get(0).getCommission()))
                                            .putExtra("mobile",String.valueOf(m.getData().get(0).getMobileno()))
                                            .putExtra("aadhaar",String.valueOf(m.getData().get(0).getAadharNumber()))
                                            .putExtra("ministmt",String.valueOf(m.getData().get(0).getMiniSmt()))
                                            .putExtra("reason",String.valueOf(m.getData().get(0).getReason()))
                                            .putExtra("status",String.valueOf(m.getData().get(0).getStatus()))
                                            .putExtra("req_date",String.valueOf(m.getData().get(0).getReqDate()))
                                            .putExtra("acc_bal",String.valueOf(m.getData().get(0).getAccountBal()))
                                    );*/
                               // }*/
                              /*  else
                                {
                                    startActivity(new Intent(WTS_Aeps_Activity.this, MiniStatement_Success.class)
                                            .putExtra("status_code",m.getStatuscode())
                                            .putExtra("head1", m.getHead1())
                                            .putExtra("head2", m.getHead2())
                                            .putExtra("head3", m.getHead3())
                                            .putExtra("id", String.valueOf(m.getData().get(0).getId()))
                                            .putExtra("txn_type",String.valueOf(m.getData().get(0).getTxntype()))
                                            .putExtra("bc_id",String.valueOf(m.getData().get(0).getBcId()))
                                            .putExtra("bank_name",String.valueOf(m.getData().get(0).getBankName()))
                                            .putExtra("op_name",String.valueOf(m.getData().get(0).getOpname()))
                                            .putExtra("order_id",String.valueOf(m.getData().get(0).getOrderId()))
                                            .putExtra("txn_id",String.valueOf(m.getData().get(0).getTransactionId()))
                                            .putExtra("amount",String.valueOf(m.getData().get(0).getAmount()))
                                            .putExtra("comm",String.valueOf(m.getData().get(0).getCommission()))
                                            .putExtra("mobile",String.valueOf(m.getData().get(0).getMobileno()))
                                            .putExtra("aadhaar",String.valueOf(m.getData().get(0).getAadharNumber()))
                                            .putExtra("ministmt",String.valueOf(m.getData().get(0).getMiniSmt()))
                                            .putExtra("reason",String.valueOf(m.getData().get(0).getReason()))
                                            .putExtra("status",String.valueOf(m.getData().get(0).getStatus()))
                                            .putExtra("req_date",String.valueOf(m.getData().get(0).getReqDate()))
                                            .putExtra("acc_bal",String.valueOf(m.getData().get(0).getAccountBal()))
                                    );
                                }*/
                            }
                        progressDialog.dismiss();

                    } catch (Exception e) {
                        System.out.println("Array Data EXC "+e.getMessage());
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                    AlertDialog.Builder alert = new AlertDialog.Builder(WTS_Aeps_Activity.this);
                    alert.setTitle("Alert!!!");
                    alert.setMessage("Something went wrong please contact helpline number");
                    alert.setCancelable(false);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                String error = t.toString();
                WTS_Aeps_Activity wTS_Aeps_Activity = WTS_Aeps_Activity.this;
                Toast.makeText(wTS_Aeps_Activity, "" + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getBank() {
        final AlertDialog pDialog = new AlertDialog.Builder(this).create();
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetAepsbankDetails","",userid));
        RetrofitClient.getInstance().getApi().GetAepsbankDetails(jsonObject).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject responseJsonObject = new JSONObject(String.valueOf(response.body()));
                       // System.out.println("MY RESP "+responseJsonObject);
                        String response_code = responseJsonObject.getString("Statuscode");
                        if (response_code.equalsIgnoreCase("TXN")) {
                            JSONArray dataArray = responseJsonObject.getJSONArray("Data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jsonObject2 = dataArray.getJSONObject(i);
                                String bankname = jsonObject2.getString("BankName");
                                WTS_Aeps_Activity.this.bankMP.put(bankname, new BankModel(jsonObject2.getString("IIN"),jsonObject2.getString("Id")));
                                WTS_Aeps_Activity.this.bankArray.add(bankname);
                                WTS_Aeps_Activity wTS_Aeps_Activity = WTS_Aeps_Activity.this;
                                SpinnerDialog spinnerDialog = null;
                                SpinnerDialog spinnerDialog2 = new SpinnerDialog(WTS_Aeps_Activity.this, WTS_Aeps_Activity.this.bankArray, "Select or Search Bank", R.style.DialogAnimations_SmileWindow, "Close  ");
                                wTS_Aeps_Activity.spinnerDialog = spinnerDialog2;
                                WTS_Aeps_Activity.this.spinnerDialog.setCancellable(true);
                                WTS_Aeps_Activity.this.spinnerDialog.setShowKeyboard(false);
                                WTS_Aeps_Activity.this.spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                                    public void onClick(String item, int position) {
                                        WTS_Aeps_Activity.this.chooseBankET.setText(item);
                                        WTS_Aeps_Activity.this.iinBankStr = WTS_Aeps_Activity.this.bankMP.get(item).getIIN();
                                        WTS_Aeps_Activity.this.bankId = WTS_Aeps_Activity.this.bankMP.get(item).getId();
                                        WTS_Aeps_Activity.this.bankNameStr = item;
                                    }
                                });
                            }
                            pDialog.dismiss();
                        } else if (response_code.equalsIgnoreCase("ERR")) {
                            pDialog.dismiss();
                            new AlertDialog.Builder(WTS_Aeps_Activity.this).setCancelable(false).setTitle("Alert").setMessage("Ye api response nhi hone pe section run hua h").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    WTS_Aeps_Activity.this.finish();
                                }
                            }).show();
                        } else {
                            pDialog.dismiss();
                            new AlertDialog.Builder(WTS_Aeps_Activity.this).setCancelable(false).setTitle("Alert").setMessage("Ye jab server se failed mila h response me").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    WTS_Aeps_Activity.this.finish();
                                }
                            }).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        pDialog.dismiss();
                    }
                } else {
                    pDialog.dismiss();
                    AlertDialog.Builder alert = new AlertDialog.Builder(WTS_Aeps_Activity.this);
                    alert.setTitle("Alert!!!");
                    alert.setMessage("Something went wrong please contact helpline number");
                    alert.setCancelable(false);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                pDialog.dismiss();
                pDialog.dismiss();
                AlertDialog.Builder alert = new AlertDialog.Builder(WTS_Aeps_Activity.this);
                alert.setTitle("Alert!!!");
                alert.setMessage("Something went wrong please contact helpline number");
                alert.setCancelable(false);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new ArrayList();
        if (data == null) {
            return;
        }
        if (requestCode == this.STARTEK_CODE) {
            if (resultCode == -1) {
                String result = data.getStringExtra("PID_DATA");
                if (result.contains("Device not ready")) {
                    deviceNotConnected();
                } else if (!result.contains("srno") || !result.contains("rdsId") || !result.contains("rdsVer")) {
                    deviceNotConnected();
                } else {
                    try {
                        PidData pidData3 = (PidData) this.serializer.read(PidData.class, result);
                        this.pidData = pidData3;
                        String str3 = pidData3._Data.value;
                        this.pidDataStr = str3;
                        Log.e("xml_data_show", str3);
                        this.hmac = this.pidData._Hmac;
                        this.sessionKey = this.pidData._Skey.value;
                        this.dpId = this.pidData._DeviceInfo.dpId;
                        this.rdsId = this.pidData._DeviceInfo.rdsId;
                        this.rdsVer = this.pidData._DeviceInfo.rdsVer;
                        this.dc = this.pidData._DeviceInfo.dc;
                        this.mc = this.pidData._DeviceInfo.mc;
                        this.mi = this.pidData._DeviceInfo.mi;
                        this.errCode = this.pidData._Resp.errCode;
                        this.errInfo = this.pidData._Resp.errInfo;
                        this.errCode = this.pidData._Resp.errCode;
                        this.fcount = this.pidData._Resp.fCount;
                        this.qScore = this.pidData._Resp.qScore;
                        this.nmPoints = this.pidData._Resp.nmPoints;
                        this.ci = this.pidData._Skey.ci;
                        this.iCount = this.pidData._Resp.iCount;
                        this.pType = this.pidData._Resp.pType;
                        this.pCount = this.pidData._Resp.pCount;
                        List<Param> params2 = this.pidData._DeviceInfo.add_info.params;
                        for (int i2 = 0; i2 < params2.size(); i2++) {
                            String name2 = params2.get(i2).name;
                            if (name2.equalsIgnoreCase("srno")) {
                                String str4 = params2.get(i2).value;
                                this.serialNo = str4;
                                Log.e("serialNu", str4);
                            } else if (name2.equalsIgnoreCase("sysid")) {
                                Log.e("systemId", params2.get(i2).value);
                            }
                        }
                        getTransactionNow();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == this.MANTRA_CODE) {
            if (resultCode == -1) {
                String result2 = data.getStringExtra("PID_DATA");
                if (result2.contains("Device not ready")) {
                    deviceNotConnected();
                } else if (!result2.contains("srno") || !result2.contains("rdsId") || !result2.contains("rdsVer")) {
                    deviceNotConnected();
                } else {
                    try {
                        PidData pidData3 = (PidData) this.serializer.read(PidData.class, result2);
                        this.pidData = pidData3;
                        String str3 = pidData3._Data.value;
                        this.pidDataStr = str3;
                        Log.e("xml_data_show", str3);
                        this.hmac = this.pidData._Hmac;
                        this.sessionKey = this.pidData._Skey.value;
                        this.dpId = this.pidData._DeviceInfo.dpId;
                        this.rdsId = this.pidData._DeviceInfo.rdsId;
                        this.rdsVer = this.pidData._DeviceInfo.rdsVer;
                        this.dc = this.pidData._DeviceInfo.dc;
                        this.mc = this.pidData._DeviceInfo.mc;
                        this.mi = this.pidData._DeviceInfo.mi;
                        this.errCode = this.pidData._Resp.errCode;
                        this.errInfo = this.pidData._Resp.errInfo;
                        this.errCode = this.pidData._Resp.errCode;
                        this.fcount = this.pidData._Resp.fCount;
                        this.qScore = this.pidData._Resp.qScore;
                        this.nmPoints = this.pidData._Resp.nmPoints;
                        this.ci = this.pidData._Skey.ci;
                        this.iCount = this.pidData._Resp.iCount;
                        this.pType = this.pidData._Resp.pType;
                        this.pCount = this.pidData._Resp.pCount;
                        List<Param> params2 = this.pidData._DeviceInfo.add_info.params;
                        for (int i2 = 0; i2 < params2.size(); i2++) {
                            String name2 = params2.get(i2).name;
                            if (name2.equalsIgnoreCase("srno")) {
                                String str4 = params2.get(i2).value;
                                this.serialNo = str4;
                                Log.e("serialNu", str4);
                            } else if (name2.equalsIgnoreCase("sysid")) {
                                Log.e("systemId", params2.get(i2).value);
                            }
                        }
                        getTransactionNow();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        } else if (requestCode == this.MORPHO_CODE && resultCode == -1) {
            String result3 = data.getStringExtra("PID_DATA");
            if (result3.contains("Device not ready")) {
                deviceNotConnected();
            } else if (!result3.contains("srno") || !result3.contains("rdsId") || !result3.contains("rdsVer")) {
                deviceNotConnected();
            } else {
                try {
                    PidData pidData3 = (PidData) this.serializer.read(PidData.class, result3);
                    this.pidData = pidData3;
                    String str3 = pidData3._Data.value;
                    this.pidDataStr = str3;
                    Log.e("xml_data_show", str3);
                    this.hmac = this.pidData._Hmac;
                    this.sessionKey = this.pidData._Skey.value;
                    this.dpId = this.pidData._DeviceInfo.dpId;
                    this.rdsId = this.pidData._DeviceInfo.rdsId;
                    this.rdsVer = this.pidData._DeviceInfo.rdsVer;
                    this.dc = this.pidData._DeviceInfo.dc;
                    this.mc = this.pidData._DeviceInfo.mc;
                    this.mi = this.pidData._DeviceInfo.mi;
                    this.errCode = this.pidData._Resp.errCode;
                    this.errInfo = this.pidData._Resp.errInfo;
                    this.errCode = this.pidData._Resp.errCode;
                    this.fcount = this.pidData._Resp.fCount;
                    this.qScore = this.pidData._Resp.qScore;
                    this.nmPoints = this.pidData._Resp.nmPoints;
                    this.ci = this.pidData._Skey.ci;
                    this.iCount = this.pidData._Resp.iCount;
                    this.pType = this.pidData._Resp.pType;
                    this.pCount = this.pidData._Resp.pCount;
                    List<Param> params2 = this.pidData._DeviceInfo.add_info.params;
                    for (int i2 = 0; i2 < params2.size(); i2++) {
                        String name2 = params2.get(i2).name;
                        if (name2.equalsIgnoreCase("srno")) {
                            String str4 = params2.get(i2).value;
                            this.serialNo = str4;
                            Log.e("serialNu", str4);
                        } else if (name2.equalsIgnoreCase("sysid")) {
                            Log.e("systemId", params2.get(i2).value);
                        }
                    }
                    getTransactionNow();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    private void deviceNotConnected() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View convertView = getLayoutInflater().inflate(R.layout.device_not_connected, (ViewGroup) null);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ((ImageView) convertView.findViewById(R.id.image_set)).setImageResource(R.drawable.not_connected_ic);
        ((TextView) convertView.findViewById(R.id.tag_line)).setText("Device not connected!");
        ((TextView) convertView.findViewById(R.id.device_name)).setText("Pls connect your finger print Scanner");
        Button done_btn = (Button) convertView.findViewById(R.id.done_btn);
        done_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(convertView);
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    private void OnGPS() {
        locationSharingOff();
    }

    private void locationSharingOff() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View convertView = getLayoutInflater().inflate(R.layout.device_not_connected, (ViewGroup) null);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Button done_btn = (Button) convertView.findViewById(R.id.done_btn);
        ((ImageView) convertView.findViewById(R.id.image_set)).setImageResource(R.drawable.location_img_ic);
        ((TextView) convertView.findViewById(R.id.tag_line)).setText("Location Sharing is Off!");
        ((TextView) convertView.findViewById(R.id.device_name)).setText("To use AEPS Service, you need to turn your location sharing on");
        done_btn.setText("Turn Location On");
        done_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WTS_Aeps_Activity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(convertView);
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    /* access modifiers changed from: private */
    public String getPIDOptions() {
        try {

            Opts opts = new Opts();
            opts.fCount = DiskLruCache.VERSION_1;
            opts.fType = "0";
            opts.format = "0";
            opts.timeout = "15000";
            opts.wadh = "";
            opts.iCount = "0";
            opts.iType = "0";
            opts.pCount = "0";
            opts.pType = "0";
            opts.pidVer = "2.0";
            opts.env = "P";
            PidOptions pidOptions = new PidOptions();
            pidOptions.ver = "1.0";
            pidOptions.Opts = opts;
            Serializer serializer2 = new Persister();
            StringWriter writer = new StringWriter();
            serializer2.write((Object) pidOptions, (Writer) writer);
            return writer.toString();
        } catch (Exception e) {
            Log.e("Error", e.toString());
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void appNotInstall(final String name) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View convertView = getLayoutInflater().inflate(R.layout.device_not_connected, (ViewGroup) null);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Button done_btn = (Button) convertView.findViewById(R.id.done_btn);
        ((ImageView) convertView.findViewById(R.id.image_set)).setImageResource(R.drawable.app_not_install);
        ((TextView) convertView.findViewById(R.id.tag_line)).setText("Application not installed!");
        ((TextView) convertView.findViewById(R.id.device_name)).setText("To use " + name + " device, please download it's Application from Playstore!");
        done_btn.setText("Download App");
        done_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (name.equalsIgnoreCase("Mantra")) {
                    WTS_Aeps_Activity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice")));
                } else if (name.equalsIgnoreCase("Startek")) {
                    WTS_Aeps_Activity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk")));
                } else if (name.equalsIgnoreCase("Morpho")) {
                    WTS_Aeps_Activity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice")));
                } else if (name.equalsIgnoreCase("3M Cogent")) {
                    Toast.makeText(WTS_Aeps_Activity.this.context, "No application", Toast.LENGTH_LONG).show();
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(convertView);
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    public void onBackPressed() {
        if (this.deviceLayout.getVisibility() == View.VISIBLE) {
            this.deviceLayout.setVisibility(View.GONE);
            this.secondLY.setVisibility(View.VISIBLE);
        } else if (this.secondLY.getVisibility() == View.VISIBLE) {
            this.deviceLayout.setVisibility(View.GONE);
            this.secondLY.setVisibility(View.GONE);
            this.firstLY.setVisibility(View.VISIBLE);
            this.cash_icon.setVisibility(View.INVISIBLE);
            this.balance_icon.setVisibility(View.INVISIBLE);
            this.mini_icon.setVisibility(View.INVISIBLE);
            this.aadharpay_icon.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        geoLocation.startLocationButtonClick();
    }

    @Override
    protected void onPause() {
        super.onPause();
        geoLocation.stopLocationUpdates();
    }


    @Override
    public void onGpsStatusChanged(boolean gpsStatus) {
        isGpsOn = gpsStatus;
        if(isGpsOn) {

            faslecall=false;
            if(onecall==false)
            {
                onecall=true;
                //  Toast.makeText(this, "GPS on", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            onecall=false;

            if(faslecall==false)
            {
                faslecall=true;
                //   Toast.makeText(this, "GPS OFF", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(gpsListener);
    }


    @Override
    public void onDataReceived(String myData, Double latitude, Double longitude, String address) {
        this.latitude=latitude;
        this.longitude=longitude;
    }
}
