package com.example.goldenfish.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;

import com.example.goldenfish.R;

import java.util.Objects;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int PERMISSION_ID = 44;
    String address;
    String aepsBalance = "00.00";
    CardView aepsCard;
    String balance = "00.00";
    LinearLayout bottomHistoryLayout;
    LinearLayout bottomProfileLayout;
    Button btnAeps;
    DrawerLayout drawer;
    CardView dthCard;
    String email;
    String firmName;
    ImageView imgAepsWallet;
    ImageView imgMainWallet;
    String lat = "0.0";
    String longi = "0.0";
    FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback = new LocationCallback() {
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            HomeDashboardActivity homeDashboardActivity = HomeDashboardActivity.this;
            homeDashboardActivity.lat = mLastLocation.getLatitude() + "";
            HomeDashboardActivity homeDashboardActivity2 = HomeDashboardActivity.this;
            homeDashboardActivity2.longi = mLastLocation.getLongitude() + "";
            //HomeDashboardActivity.this.launchWTSAEPS();
        }
    };
    String mobileNo;
    CardView moneyTransferCard;
    LinearLayout moveToBankLayout;
    String name;
    /* access modifiers changed from: private */
   /* public final NavigationFallback navigationFallback = new NavigationFallback() {
        public void onFallbackNavigateTo(Uri url) {
            HomeDashboardActivity.this.startActivity(new Intent("android.intent.action.VIEW").setData(url).addFlags(268435456));
        }
    };*/
    NavigationView navigationView;
    /*private final View.OnClickListener openUrlButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            SimpleChromeCustomTabs.getInstance().withFallback(HomeDashboardActivity.this.navigationFallback).navigateTo(Uri.parse(HomeDashboardActivity.this.url), HomeDashboardActivity.this);
        }
    };*/
    String ownerName;
   /* private final AvailableAppProvider.PackageFoundCallback packageFoundCallback = new AvailableAppProvider.PackageFoundCallback() {
        public void onPackageFound(String packageName) {
            View findViewById = HomeDashboardActivity.this.findViewById(16908290);
        }

        public void onPackageNotFound() {
            View findViewById = HomeDashboardActivity.this.findViewById(16908290);
        }
    };*/
    String panCard;
    CardView postpaidCard;
    CardView prepaidCard;
    SharedPreferences sharedPreferences;
    TextView tvAepsBalance;
    TextView tvEmailAddress;
    TextView tvFirmName;
    TextView tvLocation;
    TextView tvMainBalance;
    TextView tvNavMobileNo;
    TextView tvNavOwnerName;
    String url;
    String userid;
    //WebServiceInterface webServiceInterface;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_home_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).setDisplayShowTitleEnabled(false);
        inhitViews();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences = defaultSharedPreferences;
        this.userid = defaultSharedPreferences.getString("userid", (String) null);
        this.ownerName = this.sharedPreferences.getString("ownername", (String) null);
        this.mobileNo = this.sharedPreferences.getString("mobileno", (String) null);
        this.panCard = this.sharedPreferences.getString("panCard", (String) null);
        this.name = this.sharedPreferences.getString("ownername", (String) null);
        this.email = this.sharedPreferences.getString("email", (String) null);
        this.address = this.sharedPreferences.getString("address", (String) null);
        this.firmName = this.sharedPreferences.getString("firmName", (String) null);
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        this.url = "http://api.goldenfishdigital.co.in/MAEPS.aspx?Pan=" + this.panCard + "&Name=" + this.name;
        this.tvNavOwnerName.setText(this.ownerName);
        TextView textView = this.tvNavMobileNo;
        textView.setText("Phone : " + this.mobileNo);
        this.tvLocation.setText(this.address);
        this.tvEmailAddress.setText(this.email);
        TextView textView2 = this.tvFirmName;
        textView2.setText("Firm : " + this.firmName);
       // this.webServiceInterface = (WebServiceInterface) WebServiceInterface.retrofit.create(WebServiceInterface.class);
        if (checkInternetState()) {
         //   getBalance();
           // getAepsBalance();
        } else {
            showSnackbar();
        }
        this.navigationView.setNavigationItemSelectedListener(this);
        this.navigationView.getMenu().getItem(0).setChecked(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(17170443));
       /* this.prepaidCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeDashboardActivity.this, RechargeActivity.class);
                intent.putExtra("title", "Prepaid Recharge");
                intent.putExtra(NotificationCompat.CATEGORY_SERVICE, "Mobile");
                intent.putExtra("mainBalance", HomeDashboardActivity.this.balance);
                intent.putExtra("aepsBalance", HomeDashboardActivity.this.aepsBalance);
                HomeDashboardActivity.this.startActivity(intent);
            }
        });
        this.postpaidCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeDashboardActivity.this, RechargeActivity.class);
                intent.putExtra("title", "Postpaid Recharge");
                intent.putExtra(NotificationCompat.CATEGORY_SERVICE, "Postpaid");
                intent.putExtra("mainBalance", HomeDashboardActivity.this.balance);
                intent.putExtra("aepsBalance", HomeDashboardActivity.this.aepsBalance);
                HomeDashboardActivity.this.startActivity(intent);
            }
        });
        this.dthCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeDashboardActivity.this, RechargeActivity.class);
                intent.putExtra("title", "DTH Recharge");
                intent.putExtra(NotificationCompat.CATEGORY_SERVICE, "DTH");
                intent.putExtra("mainBalance", HomeDashboardActivity.this.balance);
                intent.putExtra("aepsBalance", HomeDashboardActivity.this.aepsBalance);
                HomeDashboardActivity.this.startActivity(intent);
            }
        });
        this.aepsCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View view1 = LayoutInflater.from(HomeDashboardActivity.this).inflate(R.layout.select_aeps_layout, (ViewGroup) null, false);
                final AlertDialog builder = new AlertDialog.Builder(HomeDashboardActivity.this).create();
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                builder.setView(view1);
                builder.show();
                ((LinearLayout) view1.findViewById(R.id.aeps_layout1)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        builder.dismiss();
                        Intent i = new Intent("android.intent.action.VIEW", Uri.parse("googlechrome://navigate?url=" + HomeDashboardActivity.this.url));
                        if (i.resolveActivity(HomeDashboardActivity.this.getPackageManager()) == null) {
                            i.setData(Uri.parse(HomeDashboardActivity.this.url));
                        }
                        HomeDashboardActivity.this.startActivity(i);
                    }
                });
                ((LinearLayout) view1.findViewById(R.id.aeps_layout2)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        builder.dismiss();
                        HomeDashboardActivity.this.getLastLocation();
                    }
                });
            }
        });
        this.moveToBankLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomeDashboardActivity.this.startActivity(new Intent(HomeDashboardActivity.this, MoveToBankActivity.class));
            }
        });
        this.moneyTransferCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HomeDashboardActivity.this.startActivity(new Intent(HomeDashboardActivity.this, DMTActivity.class));
            }
        });
        this.imgMainWallet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (HomeDashboardActivity.this.checkInternetState()) {
                    HomeDashboardActivity.this.getBalance();
                } else {
                    HomeDashboardActivity.this.showSnackbar();
                }
            }
        });
        this.imgAepsWallet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (HomeDashboardActivity.this.checkInternetState()) {
                    HomeDashboardActivity.this.getAepsBalance();
                } else {
                    HomeDashboardActivity.this.showSnackbar();
                }
            }
        });
        this.bottomProfileLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HomeDashboardActivity.this.startActivity(new Intent(HomeDashboardActivity.this, ProfileActivity.class));
            }
        });
        this.bottomHistoryLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeDashboardActivity.this, AllReportsActivity.class);
                intent.putExtra("title", "All Report");
                HomeDashboardActivity.this.startActivity(intent);
            }
        });*/
    }

    /* access modifiers changed from: private */
    public void getLastLocation() {
        if (!checkPermissions()) {
            requestPermissions();
        }
       // else if (isLocationEnabled()) {
            this.mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                public void onComplete(Task<Location> task) {
                    Location location = task.getResult();
                    if (location == null) {
                        HomeDashboardActivity.this.requestNewLocationData();
                        return;
                    }
                    HomeDashboardActivity homeDashboardActivity = HomeDashboardActivity.this;
                    homeDashboardActivity.lat = location.getLatitude() + "";
                    HomeDashboardActivity homeDashboardActivity2 = HomeDashboardActivity.this;
                    homeDashboardActivity2.longi = location.getLongitude() + "";
                    //HomeDashboardActivity.this.launchWTSAEPS();
                }
            });
       // }
       /* else {
            Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
            startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        }*/
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, this.PERMISSION_ID);
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            return true;
        }
        return false;
    }

   /* private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(FirebaseAnalytics.LOCATION);
        return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
    }*/

    /* access modifiers changed from: private */
    public void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(100);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        this.mFusedLocationClient = fusedLocationProviderClient;
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, this.mLocationCallback, Looper.myLooper());
    }

    /* access modifiers changed from: private */
   /* public void launchWTSAEPS() {
        if (this.balance.equalsIgnoreCase("0.00")) {
            this.balance = "0.0";
        }
        Intent intent = new Intent(this, WTS_Aeps_Activity.class);
        intent.putExtra("app_Id", "106");
        intent.putExtra("authorise_Key", "XLbdpgCcdK0rcfwDw3pBIdXZxAozuEirBfqyHNPj66srr2JczEMk4A==");
        intent.putExtra("main_wallet_bal", this.balance);
        intent.putExtra("panno", this.panCard);
        intent.putExtra("lat", this.lat);
        intent.putExtra("long", this.longi);
        startActivity(intent);
    }*/

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.PERMISSION_ID && grantResults.length > 0 && grantResults[0] == 0) {
            getLastLocation();
        }
    }

    /* access modifiers changed from: protected */
   /* public void onPause() {
        SimpleChromeCustomTabs.getInstance().disconnectFrom(this);
        super.onPause();
    }

    private void checkForPackageAvailable() {
        SimpleChromeCustomTabs.getInstance().findBestPackage(this.packageFoundCallback, this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
       // checkForPackageAvailable();
       // SimpleChromeCustomTabs.getInstance().connectTo(this);
    }

    private void inhitViews() {
        this.btnAeps = (Button) findViewById(R.id.btn_aeps);
        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.prepaidCard = (CardView) findViewById(R.id.prepaid_card);
        this.postpaidCard = (CardView) findViewById(R.id.postpaid_card);
        this.dthCard = (CardView) findViewById(R.id.dth_card);
        this.aepsCard = (CardView) findViewById(R.id.aeps_card);
        this.moneyTransferCard = (CardView) findViewById(R.id.money_transfer_card);
        this.tvMainBalance = (TextView) findViewById(R.id.tvMainBalance);
        this.tvAepsBalance = (TextView) findViewById(R.id.tv_aeps_balance);
        this.imgMainWallet = (ImageView) findViewById(R.id.img_main_wallet);
        this.imgAepsWallet = (ImageView) findViewById(R.id.img_aeps_wallet);
        View headerView = this.navigationView.getHeaderView(0);
        this.tvNavOwnerName = (TextView) headerView.findViewById(R.id.tv_user_name);
        this.tvNavMobileNo = (TextView) headerView.findViewById(R.id.tv_mobile_number);
        this.tvEmailAddress = (TextView) headerView.findViewById(R.id.tv_email);
        this.tvFirmName = (TextView) headerView.findViewById(R.id.tv_firm_name);
        this.tvLocation = (TextView) headerView.findViewById(R.id.tv_location);
        this.bottomProfileLayout = (LinearLayout) findViewById(R.id.bottom_profile_layout);
        this.bottomHistoryLayout = (LinearLayout) findViewById(R.id.bottom_history_layout);
        this.moveToBankLayout = (LinearLayout) findViewById(R.id.move_to_bank_layout);
    }

   /* public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case R.id.nav_aeps_ledger_report:
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                Intent intent = new Intent(this, AepsLedgerReportActivity.class);
                intent.putExtra("title", "AEPS Ledger Report");
                startActivity(intent);
                break;
            case R.id.nav_aeps_report:
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                Intent intent2 = new Intent(this, AepsReportActivity.class);
                intent2.putExtra("title", "AEPS Report");
                startActivity(intent2);
                break;
            case R.id.nav_all_report:
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                Intent intent3 = new Intent(this, AllReportsActivity.class);
                intent3.putExtra("title", "All Report");
                startActivity(intent3);
                break;
            case R.id.nav_change_password:
                Intent intent4 = new Intent(this, ChangePasswordActivity.class);
                intent4.putExtra("title", "Change Password");
                startActivity(intent4);
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                break;
            case R.id.nav_complaint:
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                Intent intent5 = new Intent(this, ComplaintActivity.class);
                intent5.putExtra("title", "Make Complaint");
                startActivity(intent5);
                break;
            case R.id.nav_credit_report:
                Intent intent6 = new Intent(this, CreditDebitReportActivity.class);
                intent6.putExtra("title", "Credit Report");
                startActivity(intent6);
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                break;
            case R.id.nav_debit_report:
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                Intent intent7 = new Intent(this, CreditDebitReportActivity.class);
                intent7.putExtra("title", "Debit Report");
                startActivity(intent7);
                break;
            case R.id.nav_home:
                this.drawer.closeDrawer((int) GravityCompat.START, true);
                break;
            case R.id.nav_ledger_report:
                Intent intent8 = new Intent(this, LedgerReportActivity.class);
                intent8.putExtra("title", "Ledger Report");
                startActivity(intent8);
                this.drawer.closeDrawer((int) GravityCompat.START, true);
                break;
            case R.id.nav_log_out:
                new AlertDialog.Builder(this).setTitle((CharSequence) "Confirmation").setMessage((CharSequence) "Are you sure ?").setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(HomeDashboardActivity.this).edit();
                        editor.clear();
                        editor.apply();
                        HomeDashboardActivity.this.startActivity(new Intent(HomeDashboardActivity.this, LoginActivity.class));
                        HomeDashboardActivity.this.finish();
                    }
                }).setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null).show();
                this.drawer.closeDrawer((int) GravityCompat.START);
                break;
            case R.id.nav_move_to_bank:
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                startActivity(new Intent(this, MoveToBankActivity.class));
                break;
            case R.id.nav_settlement_report:
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                Intent intent9 = new Intent(this, SettlementReportActivity.class);
                intent9.putExtra("title", "Settlement Report");
                startActivity(intent9);
                break;
            case R.id.nav_support:
                showSupportDialog();
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                break;
        }*/
        /*return false;
    }*/

   /* private void showSupportDialog() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.support_dialog, (ViewGroup) null, false);
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        builder.setView(view1);
        builder.show();
        ((LinearLayout) view1.findViewById(R.id.call_layout1)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HomeDashboardActivity.this.openCaller("7268974426");
                builder.dismiss();
            }
        });
        ((LinearLayout) view1.findViewById(R.id.call_layout2)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HomeDashboardActivity.this.openCaller("9198385390");
                builder.dismiss();
            }
        });
        ((LinearLayout) view1.findViewById(R.id.call_layout3)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HomeDashboardActivity.this.openCaller("9198385390");
                builder.dismiss();
            }
        });
        ((LinearLayout) view1.findViewById(R.id.whats_app_layout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+917268974426&text=Hello I am"));
                    HomeDashboardActivity.this.startActivity(intent);
                    builder.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((LinearLayout) view1.findViewById(R.id.mail_layout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"goldenfishdigitalseva@gmail.com"});
                intent.setType("message/rfs822");
                HomeDashboardActivity.this.startActivity(Intent.createChooser(intent, "Choose Email Client"));
                builder.dismiss();
            }
        });
    }*/

    /* access modifiers changed from: private */
    public void openCaller(String supportNumber) {
        Intent intent = new Intent("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:" + supportNumber));
        startActivity(intent);
    }

    /* access modifiers changed from: private */
    public boolean checkInternetState() {
        ConnectivityManager connectivityManager = ((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    /* access modifiers changed from: private */
    public void showSnackbar() {
        Snackbar.make(findViewById(R.id.drawer_layout), (CharSequence) "No Internet", 100).show();
    }

    /* access modifiers changed from: private */
   /* public void getBalance() {
        final ProgressDialog pDialog = new ProgressDialog(this, R.style.MyTheme);
        pDialog.setMessage("Loading....");
        pDialog.setProgressStyle(16973853);
        pDialog.setCancelable(false);
        pDialog.show();
        this.webServiceInterface.getBalance(this.userid).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(String.valueOf(response.body()));
                        if (jsonObject1.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("0")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                HomeDashboardActivity.this.balance = jsonObject.getString("new_bal");
                                TextView textView = HomeDashboardActivity.this.tvMainBalance;
                                textView.setText("₹ " + HomeDashboardActivity.this.balance);
                            }
                            pDialog.dismiss();
                            return;
                        }
                        pDialog.dismiss();
                        HomeDashboardActivity.this.tvMainBalance.setText("₹ 0.0");
                    } catch (JSONException e) {
                        pDialog.dismiss();
                        HomeDashboardActivity.this.tvMainBalance.setText("₹ 0.0");
                        e.printStackTrace();
                    }
                } else {
                    pDialog.dismiss();
                    HomeDashboardActivity.this.tvMainBalance.setText("₹ 0.0");
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                pDialog.dismiss();
                HomeDashboardActivity.this.tvMainBalance.setText("₹ 0.0");
            }
        });
    }*/

    /* access modifiers changed from: private */
  /*  public void getAepsBalance() {
        final ProgressDialog pDialog = new ProgressDialog(this, R.style.MyTheme);
        pDialog.setMessage("Loading....");
        pDialog.setProgressStyle(16973853);
        pDialog.setCancelable(false);
        pDialog.show();
        this.webServiceInterface.getAepsBalance(this.userid).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(String.valueOf(response.body()));
                        if (jsonObject1.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("0")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                HomeDashboardActivity.this.aepsBalance = jsonObject.getString("new_bal");
                                TextView textView = HomeDashboardActivity.this.tvAepsBalance;
                                textView.setText("₹ " + HomeDashboardActivity.this.aepsBalance);
                            }
                            pDialog.dismiss();
                            return;
                        }
                        pDialog.dismiss();
                        HomeDashboardActivity.this.tvAepsBalance.setText("₹ 00.00");
                    } catch (JSONException e) {
                        pDialog.dismiss();
                        HomeDashboardActivity.this.tvAepsBalance.setText("₹ 00.00");
                        e.printStackTrace();
                    }
                } else {
                    pDialog.dismiss();
                    HomeDashboardActivity.this.tvAepsBalance.setText("₹ 00.00");
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                pDialog.dismiss();
                HomeDashboardActivity.this.tvAepsBalance.setText("₹ 00.00");
            }
        });
    }*/

    public void onBackPressed() {
        if (this.drawer.isDrawerOpen((int) GravityCompat.START)) {
            this.drawer.closeDrawer((int) GravityCompat.START, true);
        } else {
            new AlertDialog.Builder(this).setTitle((CharSequence) "Confirmation").setMessage((CharSequence) "You want to exit?").setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    HomeDashboardActivity.this.finish();
                }
            }).setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) null).show();
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        if (checkInternetState()) {
            //getBalance();
        } else {
            showSnackbar();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
