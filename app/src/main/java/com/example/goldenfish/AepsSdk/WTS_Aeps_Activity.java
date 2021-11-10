package com.example.goldenfish.AepsSdk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.goldenfish.AepsSdk.device.Opts;
import com.example.goldenfish.AepsSdk.device.Param;
import com.example.goldenfish.AepsSdk.device.PidData;
import com.example.goldenfish.AepsSdk.device.PidOptions;
import com.example.goldenfish.AepsSdk.model.AepsModelRequest;
import com.example.goldenfish.AepsSdk.retrofit.RetrofitClient;
import com.example.goldenfish.R;
import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.messaging.Constants;
import com.google.gson.JsonObject;
//import com.wts.wts_aeps_release.device.Opts;
//import com.wts.wts_aeps_release.device.Param;
//import com.wts.wts_aeps_release.device.PidData;
//import com.wts.wts_aeps_release.device.PidOptions;
//import com.wts.wts_aeps_release.model.AepsModelRequest;
//import com.wts.wts_aeps_release.retrofit.RetrofitClient;

//import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
//import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.internal.cache.DiskLruCache;
import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
//import org.simpleframework.xml.Serializer;
//import org.simpleframework.xml.core.Persister;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//@SynthesizedClassMap({$$Lambda$WTS_Aeps_Activity$ZLpebXbkZ7iz9qwwbyuBxmnlO5E.class, $$Lambda$WTS_Aeps_Activity$b9Y5cETlWlYAOuoi9kUu5qsNFnU.class, $$Lambda$WTS_Aeps_Activity$rNE41YeG_O7qIJrbVktJtB8VzOw.class})
public class WTS_Aeps_Activity extends AppCompatActivity {
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
    HashMap<String, String> bankMP = new HashMap<>();
    String bankNameStr;
    String bc_Address;
    LinearLayout cashWithdraw_Container;
    ImageView cash_icon;
    TextView chooseBankET;
    LinearLayout chooseBankLY;
    String ci;
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
    String iinBankStr;
    String latitude = "";
    LocationManager locationManager;
    String longitude = "";
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
    String txnTypeNameStr = "";
    String walletBalance = "0.00";

    /* access modifiers changed from: protected */
    @SuppressLint({"SetTextI18n"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_t_s__aeps_);
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
        this.latitude = getIntent().getStringExtra("lat");
        this.longitude = getIntent().getStringExtra("long");
        if (this.app_Id.equalsIgnoreCase("") || this.authorise_Key.equalsIgnoreCase("") || this.pannoStr.equalsIgnoreCase("") || this.latitude.equalsIgnoreCase("") || this.longitude.equalsIgnoreCase("") || this.walletBalance.equalsIgnoreCase("0.00")) {
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
                    WTS_Aeps_Activity.this.firstLY.setVisibility(View.GONE);
                    WTS_Aeps_Activity.this.secondLY.setVisibility(View.VISIBLE);
                    WTS_Aeps_Activity.this.headerTxt.setText("Balance Enquiry");
                } else if (WTS_Aeps_Activity.this.selectedItemtxnType.equalsIgnoreCase("Mini Statement")) {
                    WTS_Aeps_Activity.this.selectedItemtxnType = "SAP";
                    WTS_Aeps_Activity.this.txnTypeNameStr = "Mini Statement";
                    WTS_Aeps_Activity.this.amountET.setVisibility(View.GONE);
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
        this.mobileStr = this.mobileET.getText().toString();
        String bank = this.chooseBankET.getText().toString();
        this.aadhaarStr = this.aadhaarET.getText().toString();
        String amount = this.amountET.getText().toString();
        if (this.mobileStr.equalsIgnoreCase("")) {
            snackbar("Enter mobile number");
        } else if (this.aadhaarStr.equalsIgnoreCase("")) {
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
        AepsModelRequest.Datass datass = new AepsModelRequest.Datass();
        datass.setSrno(this.serialNo);
        datass.setPiddatavalue(this.pidDataStr);
        datass.setCi(this.ci);
        datass.setDc(this.dc);
        datass.setDpid(this.dpId);
        datass.setErrcode(this.errCode);
        datass.setErrinfo("");
        datass.setFcount(this.fcount);
        datass.setHmac(this.hmac);
        datass.setMc(this.mc);
        datass.setMi(this.mi);
        datass.setNmpoints(this.nmPoints);
        datass.setQscore(this.qScore);
        datass.setRdsid(this.rdsId);
        datass.setRdsver(this.rdsVer);
        datass.setSessionKey(this.sessionKey);
        AepsModelRequest.Requestss requestss = new AepsModelRequest.Requestss();
        requestss.setBankiin(this.iinBankStr);
        requestss.setBankname(this.bankNameStr);
        requestss.setAadharno(this.aadhaarStr);
        requestss.setMobileno(this.mobileStr);
        requestss.setSpkey(this.selectedItemtxnType);
        requestss.setAmount(this.amountStr);
        requestss.setData(datass);
        AepsModelRequest aepsModelRequest = new AepsModelRequest();
        aepsModelRequest.setRequest(requestss);
        RetrofitClient.getInstance().getApi().getAepsTransaction(this.app_Id, this.authorise_Key, this.pannoStr, this.latitude, this.longitude, aepsModelRequest).enqueue(new Callback<JsonObject>() {
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: java.lang.String} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: com.wts.wts_aeps_release.WTS_Aeps_Activity$17} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v22, resolved type: com.wts.wts_aeps_release.WTS_Aeps_Activity$17} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v23, resolved type: com.wts.wts_aeps_release.WTS_Aeps_Activity$17} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v25, resolved type: com.wts.wts_aeps_release.WTS_Aeps_Activity$17} */
            /* JADX WARNING: Multi-variable type inference failed */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onResponse(retrofit2.Call<com.google.gson.JsonObject> r35, retrofit2.Response<com.google.gson.JsonObject> r36) {
                /*
                    r34 = this;
                    r1 = r34
                    java.lang.String r0 = "Status"
                    boolean r2 = r36.isSuccessful()
                    java.lang.String r3 = ""
                    r4 = 0
                    if (r2 == 0) goto L_0x0391
                    org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0369 }
                    java.lang.Object r5 = r36.body()     // Catch:{ Exception -> 0x0369 }
                    java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x0369 }
                    r2.<init>(r5)     // Catch:{ Exception -> 0x0369 }
                    java.lang.String r5 = "statuscode"
                    java.lang.String r5 = r2.getString(r5)     // Catch:{ Exception -> 0x0369 }
                    java.lang.String r6 = "TXN"
                    boolean r6 = r5.equalsIgnoreCase(r6)     // Catch:{ Exception -> 0x0369 }
                    java.lang.String r8 = "data"
                    if (r6 == 0) goto L_0x02e7
                    org.json.JSONArray r6 = r2.getJSONArray(r8)     // Catch:{ Exception -> 0x0369 }
                    org.json.JSONObject r8 = r6.getJSONObject(r4)     // Catch:{ Exception -> 0x0369 }
                    java.lang.String r9 = r8.getString(r0)     // Catch:{ Exception -> 0x0369 }
                    java.lang.String r10 = "SUCCESS"
                    boolean r10 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x0369 }
                    java.lang.String r11 = "location"
                    java.lang.String r12 = "status"
                    java.lang.String r13 = "BcLocation"
                    java.lang.String r14 = "ErrorMsg"
                    java.lang.String r15 = "AccountBalance"
                    java.lang.String r7 = "Amount"
                    java.lang.String r4 = "TxnId"
                    r17 = r6
                    java.lang.String r6 = "RRN"
                    r18 = r2
                    java.lang.String r2 = "AadharNo"
                    r19 = r5
                    java.lang.String r5 = "BcName"
                    r20 = r9
                    java.lang.String r9 = "MobileNo"
                    r21 = r14
                    java.lang.String r14 = "BankName"
                    r22 = r11
                    java.lang.String r11 = "AepsMode"
                    if (r10 == 0) goto L_0x01d7
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r10 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x01d2 }
                    java.lang.String r10 = r10.selectedItemtxnType     // Catch:{ Exception -> 0x01d2 }
                    r23 = r3
                    java.lang.String r3 = "SAP"
                    boolean r3 = r10.equalsIgnoreCase(r3)     // Catch:{ Exception -> 0x01cd }
                    if (r3 == 0) goto L_0x011e
                    java.lang.String r0 = r8.getString(r0)     // Catch:{ Exception -> 0x0118 }
                    java.lang.String r3 = r8.getString(r11)     // Catch:{ Exception -> 0x0118 }
                    java.lang.String r10 = r8.getString(r14)     // Catch:{ Exception -> 0x0118 }
                    java.lang.String r16 = r8.getString(r9)     // Catch:{ Exception -> 0x0118 }
                    r21 = r16
                    java.lang.String r16 = r8.getString(r5)     // Catch:{ Exception -> 0x0118 }
                    r24 = r16
                    java.lang.String r16 = r8.getString(r2)     // Catch:{ Exception -> 0x0118 }
                    r25 = r16
                    java.lang.String r16 = r8.getString(r6)     // Catch:{ Exception -> 0x0118 }
                    r26 = r16
                    java.lang.String r16 = r8.getString(r4)     // Catch:{ Exception -> 0x0118 }
                    r27 = r16
                    java.lang.String r13 = r8.getString(r13)     // Catch:{ Exception -> 0x0118 }
                    java.lang.String r16 = "0"
                    r28 = r16
                    r16 = r13
                    java.lang.String r13 = "Ministatementdata"
                    org.json.JSONArray r13 = r8.getJSONArray(r13)     // Catch:{ Exception -> 0x0118 }
                    r29 = r8
                    android.content.Intent r8 = new android.content.Intent     // Catch:{ Exception -> 0x0118 }
                    r30 = r13
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r13 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x0118 }
                    java.lang.Class<com.wts.wts_aeps_release.MiniStatement_Success> r1 = com.wts.wts_aeps_release.MiniStatement_Success.class
                    r8.<init>(r13, r1)     // Catch:{ Exception -> 0x0114 }
                    r1 = r8
                    r1.putExtra(r12, r0)     // Catch:{ Exception -> 0x0114 }
                    r1.putExtra(r11, r3)     // Catch:{ Exception -> 0x0114 }
                    r1.putExtra(r14, r10)     // Catch:{ Exception -> 0x0114 }
                    r8 = r21
                    r1.putExtra(r9, r8)     // Catch:{ Exception -> 0x0114 }
                    r9 = r24
                    r1.putExtra(r5, r9)     // Catch:{ Exception -> 0x0114 }
                    r5 = r25
                    r1.putExtra(r2, r5)     // Catch:{ Exception -> 0x0114 }
                    r2 = r26
                    r1.putExtra(r6, r2)     // Catch:{ Exception -> 0x0114 }
                    r6 = r27
                    r1.putExtra(r4, r6)     // Catch:{ Exception -> 0x0114 }
                    r4 = r28
                    r1.putExtra(r7, r4)     // Catch:{ Exception -> 0x0114 }
                    r7 = r23
                    r1.putExtra(r15, r7)     // Catch:{ Exception -> 0x010d }
                    r11 = r16
                    r12 = r22
                    r1.putExtra(r12, r11)     // Catch:{ Exception -> 0x010d }
                    java.lang.String r12 = "mini"
                    java.lang.String r13 = r30.toString()     // Catch:{ Exception -> 0x010d }
                    r1.putExtra(r12, r13)     // Catch:{ Exception -> 0x010d }
                    r12 = r34
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r13 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x0107 }
                    r13.startActivity(r1)     // Catch:{ Exception -> 0x0107 }
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r13 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x0107 }
                    r13.finish()     // Catch:{ Exception -> 0x0107 }
                    r31 = r7
                    r14 = r12
                    goto L_0x02dd
                L_0x0107:
                    r0 = move-exception
                    r31 = r7
                L_0x010a:
                    r14 = r12
                    goto L_0x036d
                L_0x010d:
                    r0 = move-exception
                    r14 = r34
                    r31 = r7
                    goto L_0x036d
                L_0x0114:
                    r0 = move-exception
                    r14 = r34
                    goto L_0x011a
                L_0x0118:
                    r0 = move-exception
                    r14 = r1
                L_0x011a:
                    r31 = r23
                    goto L_0x036d
                L_0x011e:
                    r29 = r8
                    r3 = r22
                    r10 = r23
                    r8 = r29
                    java.lang.String r0 = r8.getString(r0)     // Catch:{ Exception -> 0x01c8 }
                    java.lang.String r16 = r8.getString(r11)     // Catch:{ Exception -> 0x01c8 }
                    r22 = r16
                    java.lang.String r16 = r8.getString(r14)     // Catch:{ Exception -> 0x01c8 }
                    r23 = r16
                    java.lang.String r16 = r8.getString(r9)     // Catch:{ Exception -> 0x01c8 }
                    r24 = r16
                    java.lang.String r16 = r8.getString(r5)     // Catch:{ Exception -> 0x01c8 }
                    r25 = r16
                    java.lang.String r16 = r8.getString(r2)     // Catch:{ Exception -> 0x01c8 }
                    r26 = r16
                    java.lang.String r16 = r8.getString(r6)     // Catch:{ Exception -> 0x01c8 }
                    r27 = r16
                    java.lang.String r16 = r8.getString(r4)     // Catch:{ Exception -> 0x01c8 }
                    r28 = r16
                    java.lang.String r16 = r8.getString(r7)     // Catch:{ Exception -> 0x01c8 }
                    r29 = r16
                    java.lang.String r16 = r8.getString(r15)     // Catch:{ Exception -> 0x01c8 }
                    r30 = r16
                    r31 = r10
                    r10 = r21
                    java.lang.String r16 = r8.getString(r10)     // Catch:{ Exception -> 0x02e3 }
                    r21 = r16
                    java.lang.String r13 = r8.getString(r13)     // Catch:{ Exception -> 0x02e3 }
                    r32 = r8
                    android.content.Intent r8 = new android.content.Intent     // Catch:{ Exception -> 0x02e3 }
                    r33 = r10
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r10 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x02e3 }
                    java.lang.Class<com.wts.wts_aeps_release.SuccessScreen_Aeps> r1 = com.wts.wts_aeps_release.SuccessScreen_Aeps.class
                    r8.<init>(r10, r1)     // Catch:{ Exception -> 0x0281 }
                    r1 = r8
                    r1.putExtra(r12, r0)     // Catch:{ Exception -> 0x0281 }
                    r8 = r22
                    r1.putExtra(r11, r8)     // Catch:{ Exception -> 0x0281 }
                    r10 = r23
                    r1.putExtra(r14, r10)     // Catch:{ Exception -> 0x0281 }
                    r11 = r24
                    r1.putExtra(r9, r11)     // Catch:{ Exception -> 0x0281 }
                    r9 = r25
                    r1.putExtra(r5, r9)     // Catch:{ Exception -> 0x0281 }
                    r5 = r26
                    r1.putExtra(r2, r5)     // Catch:{ Exception -> 0x0281 }
                    r2 = r27
                    r1.putExtra(r6, r2)     // Catch:{ Exception -> 0x0281 }
                    r6 = r28
                    r1.putExtra(r4, r6)     // Catch:{ Exception -> 0x0281 }
                    r4 = r29
                    r1.putExtra(r7, r4)     // Catch:{ Exception -> 0x0281 }
                    r7 = r30
                    r1.putExtra(r15, r7)     // Catch:{ Exception -> 0x0281 }
                    r1.putExtra(r3, r13)     // Catch:{ Exception -> 0x0281 }
                    r3 = r21
                    r12 = r33
                    r1.putExtra(r12, r3)     // Catch:{ Exception -> 0x0281 }
                    r12 = r34
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r14 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x01c5 }
                    r14.startActivity(r1)     // Catch:{ Exception -> 0x01c5 }
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r14 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x01c5 }
                    r14.finish()     // Catch:{ Exception -> 0x01c5 }
                    r14 = r12
                    goto L_0x02dd
                L_0x01c5:
                    r0 = move-exception
                    goto L_0x010a
                L_0x01c8:
                    r0 = move-exception
                    r31 = r10
                    goto L_0x02e4
                L_0x01cd:
                    r0 = move-exception
                    r31 = r23
                    goto L_0x02e4
                L_0x01d2:
                    r0 = move-exception
                    r31 = r3
                    goto L_0x02e4
                L_0x01d7:
                    r31 = r3
                    r32 = r8
                    r8 = r21
                    r3 = r22
                    java.lang.String r10 = "FAILED"
                    r22 = r3
                    r3 = r20
                    boolean r10 = r3.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x02e3 }
                    if (r10 == 0) goto L_0x0286
                    r10 = r32
                    java.lang.String r0 = r10.getString(r0)     // Catch:{ Exception -> 0x02e3 }
                    java.lang.String r16 = r10.getString(r11)     // Catch:{ Exception -> 0x02e3 }
                    r20 = r16
                    java.lang.String r16 = r10.getString(r14)     // Catch:{ Exception -> 0x02e3 }
                    r21 = r16
                    java.lang.String r16 = r10.getString(r9)     // Catch:{ Exception -> 0x02e3 }
                    r23 = r16
                    java.lang.String r16 = r10.getString(r5)     // Catch:{ Exception -> 0x02e3 }
                    r24 = r16
                    java.lang.String r16 = r10.getString(r2)     // Catch:{ Exception -> 0x02e3 }
                    r25 = r16
                    java.lang.String r16 = r10.getString(r6)     // Catch:{ Exception -> 0x02e3 }
                    r26 = r16
                    java.lang.String r16 = r10.getString(r4)     // Catch:{ Exception -> 0x02e3 }
                    r27 = r16
                    java.lang.String r16 = r10.getString(r7)     // Catch:{ Exception -> 0x02e3 }
                    r28 = r16
                    java.lang.String r16 = r10.getString(r15)     // Catch:{ Exception -> 0x02e3 }
                    r29 = r16
                    java.lang.String r16 = r10.getString(r8)     // Catch:{ Exception -> 0x02e3 }
                    r30 = r16
                    java.lang.String r13 = r10.getString(r13)     // Catch:{ Exception -> 0x02e3 }
                    r32 = r3
                    android.content.Intent r3 = new android.content.Intent     // Catch:{ Exception -> 0x02e3 }
                    r33 = r10
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r10 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x02e3 }
                    java.lang.Class<com.wts.wts_aeps_release.SuccessScreen_Aeps> r1 = com.wts.wts_aeps_release.SuccessScreen_Aeps.class
                    r3.<init>(r10, r1)     // Catch:{ Exception -> 0x0281 }
                    r1 = r3
                    r1.putExtra(r12, r0)     // Catch:{ Exception -> 0x0281 }
                    r3 = r20
                    r1.putExtra(r11, r3)     // Catch:{ Exception -> 0x0281 }
                    r10 = r21
                    r1.putExtra(r14, r10)     // Catch:{ Exception -> 0x0281 }
                    r11 = r23
                    r1.putExtra(r9, r11)     // Catch:{ Exception -> 0x0281 }
                    r9 = r24
                    r1.putExtra(r5, r9)     // Catch:{ Exception -> 0x0281 }
                    r5 = r25
                    r1.putExtra(r2, r5)     // Catch:{ Exception -> 0x0281 }
                    r2 = r26
                    r1.putExtra(r6, r2)     // Catch:{ Exception -> 0x0281 }
                    r6 = r27
                    r1.putExtra(r4, r6)     // Catch:{ Exception -> 0x0281 }
                    r4 = r28
                    r1.putExtra(r7, r4)     // Catch:{ Exception -> 0x0281 }
                    r7 = r29
                    r1.putExtra(r15, r7)     // Catch:{ Exception -> 0x0281 }
                    r12 = r22
                    r1.putExtra(r12, r13)     // Catch:{ Exception -> 0x0281 }
                    r12 = r30
                    r1.putExtra(r8, r12)     // Catch:{ Exception -> 0x0281 }
                    r14 = r34
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r8 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x0367 }
                    r8.startActivity(r1)     // Catch:{ Exception -> 0x0367 }
                    goto L_0x02dd
                L_0x0281:
                    r0 = move-exception
                    r14 = r34
                    goto L_0x036d
                L_0x0286:
                    r14 = r1
                    r33 = r32
                    r32 = r3
                    android.app.AlertDialog$Builder r0 = new android.app.AlertDialog$Builder     // Catch:{ Exception -> 0x0367 }
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r1 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x0367 }
                    r0.<init>(r1)     // Catch:{ Exception -> 0x0367 }
                    android.app.AlertDialog r0 = r0.create()     // Catch:{ Exception -> 0x0367 }
                    android.view.Window r1 = r0.getWindow()     // Catch:{ Exception -> 0x0367 }
                    android.graphics.drawable.ColorDrawable r2 = new android.graphics.drawable.ColorDrawable     // Catch:{ Exception -> 0x0367 }
                    r3 = 0
                    r2.<init>(r3)     // Catch:{ Exception -> 0x0367 }
                    r1.setBackgroundDrawable(r2)     // Catch:{ Exception -> 0x0367 }
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r1 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x0367 }
                    android.view.LayoutInflater r1 = r1.getLayoutInflater()     // Catch:{ Exception -> 0x0367 }
                    int r2 = com.wts.wts_aeps_release.R.layout.show_text     // Catch:{ Exception -> 0x0367 }
                    r3 = 0
                    android.view.View r2 = r1.inflate(r2, r3)     // Catch:{ Exception -> 0x0367 }
                    int r3 = com.wts.wts_aeps_release.R.id.btn     // Catch:{ Exception -> 0x0367 }
                    android.view.View r3 = r2.findViewById(r3)     // Catch:{ Exception -> 0x0367 }
                    android.widget.Button r3 = (android.widget.Button) r3     // Catch:{ Exception -> 0x0367 }
                    int r4 = com.wts.wts_aeps_release.R.id.edit_query     // Catch:{ Exception -> 0x0367 }
                    android.view.View r4 = r2.findViewById(r4)     // Catch:{ Exception -> 0x0367 }
                    android.widget.TextView r4 = (android.widget.TextView) r4     // Catch:{ Exception -> 0x0367 }
                    java.lang.Object r5 = r36.body()     // Catch:{ Exception -> 0x0367 }
                    java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x0367 }
                    r4.setText(r5)     // Catch:{ Exception -> 0x0367 }
                    com.wts.wts_aeps_release.WTS_Aeps_Activity$17$1 r6 = new com.wts.wts_aeps_release.WTS_Aeps_Activity$17$1     // Catch:{ Exception -> 0x0367 }
                    r6.<init>(r0)     // Catch:{ Exception -> 0x0367 }
                    r3.setOnClickListener(r6)     // Catch:{ Exception -> 0x0367 }
                    r0.setView(r2)     // Catch:{ Exception -> 0x0367 }
                    r0.show()     // Catch:{ Exception -> 0x0367 }
                    r6 = 0
                    r0.setCancelable(r6)     // Catch:{ Exception -> 0x0367 }
                L_0x02dd:
                    r0 = r18
                    r1 = r19
                    goto L_0x0361
                L_0x02e3:
                    r0 = move-exception
                L_0x02e4:
                    r14 = r1
                    goto L_0x036d
                L_0x02e7:
                    r14 = r1
                    r18 = r2
                    r31 = r3
                    r19 = r5
                    java.lang.String r0 = "ERR"
                    r1 = r19
                    boolean r0 = r1.equalsIgnoreCase(r0)     // Catch:{ Exception -> 0x0367 }
                    if (r0 == 0) goto L_0x0340
                    android.app.AlertDialog$Builder r0 = new android.app.AlertDialog$Builder     // Catch:{ Exception -> 0x0367 }
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r2 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x0367 }
                    r0.<init>(r2)     // Catch:{ Exception -> 0x0367 }
                    android.app.AlertDialog r0 = r0.create()     // Catch:{ Exception -> 0x0367 }
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r2 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x0367 }
                    android.view.LayoutInflater r2 = r2.getLayoutInflater()     // Catch:{ Exception -> 0x0367 }
                    int r3 = com.wts.wts_aeps_release.R.layout.show_text     // Catch:{ Exception -> 0x0367 }
                    r4 = 0
                    android.view.View r3 = r2.inflate(r3, r4)     // Catch:{ Exception -> 0x0367 }
                    int r4 = com.wts.wts_aeps_release.R.id.btn     // Catch:{ Exception -> 0x0367 }
                    android.view.View r4 = r3.findViewById(r4)     // Catch:{ Exception -> 0x0367 }
                    android.widget.Button r4 = (android.widget.Button) r4     // Catch:{ Exception -> 0x0367 }
                    int r5 = com.wts.wts_aeps_release.R.id.edit_query     // Catch:{ Exception -> 0x0367 }
                    android.view.View r5 = r3.findViewById(r5)     // Catch:{ Exception -> 0x0367 }
                    android.widget.TextView r5 = (android.widget.TextView) r5     // Catch:{ Exception -> 0x0367 }
                    java.lang.Object r6 = r36.body()     // Catch:{ Exception -> 0x0367 }
                    java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Exception -> 0x0367 }
                    r5.setText(r6)     // Catch:{ Exception -> 0x0367 }
                    com.wts.wts_aeps_release.WTS_Aeps_Activity$17$2 r7 = new com.wts.wts_aeps_release.WTS_Aeps_Activity$17$2     // Catch:{ Exception -> 0x0367 }
                    r7.<init>(r0)     // Catch:{ Exception -> 0x0367 }
                    r4.setOnClickListener(r7)     // Catch:{ Exception -> 0x0367 }
                    r0.setView(r3)     // Catch:{ Exception -> 0x0367 }
                    r0.show()     // Catch:{ Exception -> 0x0367 }
                    r7 = 0
                    r0.setCancelable(r7)     // Catch:{ Exception -> 0x0367 }
                    r0 = r18
                    goto L_0x0361
                L_0x0340:
                    r0 = r18
                    java.lang.String r2 = r0.getString(r8)     // Catch:{ Exception -> 0x0367 }
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r3 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this     // Catch:{ Exception -> 0x0367 }
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0367 }
                    r4.<init>()     // Catch:{ Exception -> 0x0367 }
                    java.lang.String r5 = "Failed\n\n"
                    r4.append(r5)     // Catch:{ Exception -> 0x0367 }
                    r4.append(r2)     // Catch:{ Exception -> 0x0367 }
                    java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0367 }
                    r5 = 0
                    android.widget.Toast r3 = android.widget.Toast.makeText(r3, r4, r5)     // Catch:{ Exception -> 0x0367 }
                    r3.show()     // Catch:{ Exception -> 0x0367 }
                L_0x0361:
                    android.app.ProgressDialog r2 = r0     // Catch:{ Exception -> 0x0367 }
                    r2.dismiss()     // Catch:{ Exception -> 0x0367 }
                    goto L_0x0390
                L_0x0367:
                    r0 = move-exception
                    goto L_0x036d
                L_0x0369:
                    r0 = move-exception
                    r14 = r1
                    r31 = r3
                L_0x036d:
                    r0.printStackTrace()
                    android.app.ProgressDialog r1 = r0
                    r1.dismiss()
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r1 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder
                    r2.<init>()
                    r3 = r31
                    r2.append(r3)
                    r2.append(r0)
                    java.lang.String r2 = r2.toString()
                    r3 = 0
                    android.widget.Toast r1 = android.widget.Toast.makeText(r1, r2, r3)
                    r1.show()
                L_0x0390:
                    goto L_0x03b8
                L_0x0391:
                    r14 = r1
                    android.app.ProgressDialog r0 = r0
                    r0.dismiss()
                    okhttp3.ResponseBody r0 = r36.errorBody()
                    java.lang.String r0 = r0.toString()
                    com.wts.wts_aeps_release.WTS_Aeps_Activity r1 = com.wts.wts_aeps_release.WTS_Aeps_Activity.this
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder
                    r2.<init>()
                    r2.append(r3)
                    r2.append(r0)
                    java.lang.String r2 = r2.toString()
                    r3 = 0
                    android.widget.Toast r1 = android.widget.Toast.makeText(r1, r2, r3)
                    r1.show()
                L_0x03b8:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.wts.wts_aeps_release.WTS_Aeps_Activity.AnonymousClass17.onResponse(retrofit2.Call, retrofit2.Response):void");
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
        RetrofitClient.getInstance().getApi().getBank(this.app_Id, this.authorise_Key).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject responseJsonObject = new JSONObject(String.valueOf(response.body()));
                        System.out.println("MY RESP "+responseJsonObject);
                        String response_code = responseJsonObject.getString("statuscode");
                        if (response_code.equalsIgnoreCase("TXN")) {
                            JSONArray dataArray = responseJsonObject.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jsonObject2 = dataArray.getJSONObject(i);
                                String bankname = jsonObject2.getString("Bank_name");
                                WTS_Aeps_Activity.this.bankMP.put(bankname, jsonObject2.getString("Bank_iin"));
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
                                        WTS_Aeps_Activity.this.iinBankStr = WTS_Aeps_Activity.this.bankMP.get(item);
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
                        PidData pidData2 = (PidData) this.serializer.read(PidData.class, result);
                        this.pidData = pidData2;
                        String str = pidData2._Data.value;
                        this.pidDataStr = str;
                        Log.e("xml_data_show", str);
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
                        List<Param> params = this.pidData._DeviceInfo.add_info.params;
                        for (int i = 0; i < params.size(); i++) {
                            String name = params.get(i).name;
                            if (name.equalsIgnoreCase("srno")) {
                                String str2 = params.get(i).value;
                                this.serialNo = str2;
                                Log.e("serialNu", str2);
                            } else if (name.equalsIgnoreCase("sysid")) {
                                Log.e("systemId", params.get(i).value);
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
                    PidData pidData4 = (PidData) this.serializer.read(PidData.class, result3);
                    this.pidData = pidData4;
                    String str5 = pidData4._Data.value;
                    this.pidDataStr = str5;
                    Log.e("xml_data_show", str5);
                    this.sessionKey = this.pidData._Skey.value;
                    this.hmac = this.pidData._Hmac;
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
                    List<Param> params3 = this.pidData._DeviceInfo.add_info.params;
                    for (int i3 = 0; i3 < params3.size(); i3++) {
                        String name3 = params3.get(i3).name;
                        if (name3.equalsIgnoreCase("srno")) {
                            String str6 = params3.get(i3).value;
                            this.serialNo = str6;
                            Log.e("serialNu", str6);
                        } else if (name3.equalsIgnoreCase("sysid")) {
                            Log.e("systemId", params3.get(i3).value);
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
}
