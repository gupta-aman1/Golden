package com.business.goldenfish.Dashboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import com.business.goldenfish.AddUser.AddUserActivity;
import com.business.goldenfish.Aeps.WebviewAeps;
import com.business.goldenfish.AepsSdk.WTS_Aeps_Activity;
import com.business.goldenfish.ChangePassword.ChangePasswordActivity;
import com.business.goldenfish.Common.CommonInterface;
import com.business.goldenfish.Constants.Constant;
import com.business.goldenfish.Constants.ConstantsValue;
import com.business.goldenfish.MoveToBank.MoveToBankActivity;
import com.business.goldenfish.PanCard.PsaRegistrationActivity;
import com.business.goldenfish.PanCard.PurchaseCouponActivity;
import com.business.goldenfish.PayoutAc.AddPayoutAcc;
import com.business.goldenfish.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.business.goldenfish.Retrofit.RetrofitClient;
import com.business.goldenfish.ledgerreopt.LedgerReportActivity;
import com.business.goldenfish.recharges.RechargeMainActivity;
import com.business.goldenfish.sidebar.allReports.AllReportsActivity;
import com.business.goldenfish.sidebar.allReports.GetServices;
import com.business.goldenfish.UserAuth.LoginActivity;
import com.business.goldenfish.Utilities.GeoLocation;
import com.business.goldenfish.Utilities.GpsInterface;
import com.business.goldenfish.Utilities.GpsListener;
import com.business.goldenfish.Utilities.MyUtils;
import com.business.goldenfish.Utilities.OnDataReceiverListener;
import com.business.goldenfish.Utilities.SharedPref;
import com.business.goldenfish.updatempin.UpdateMpinActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnDataReceiverListener, GpsInterface, CommonInterface {
    int PERMISSION_ID = 44;
    String address;
    String aepsBalance = "00.00";
    CardView aepsCard;
    String balance = "00.00";
    LinearLayout bottomHistoryLayout;
    LinearLayout bottomProfileLayout;
    Button btnAeps;
    DrawerLayout drawer;
    CardView dthCard,bus_card;
    String email;
    String firmName;
    ImageView imgAepsWallet;
    ImageView imgMainWallet;
    String lat = "0.0";
    String longi = "0.0";
    double lattitude=0.0;
    double longitude=0.0;

    FusedLocationProviderClient mFusedLocationClient;
    GeoLocation geoLocation;
    private GpsListener gpsListener;
    private boolean isGpsOn;
    private boolean onecall=false;
    private boolean faslecall=false;
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
    CardView prepaidCard,broad_band_card,hotel_card,flight_card,electricity_card,water_card,gas_card;
    SharedPreferences sharedPreferences;
    public static TextView tvAepsBalance;
    TextView tvEmailAddress;
    TextView tvFirmName;
    TextView tvLocation;
   public static TextView tvMainBalance;
    TextView tvNavMobileNo;
    TextView tvNavOwnerName;
    String url;
    String userid,MainBal,AepsBal;
    SharedPref sharedPref;
    private String OwnerName;
    private String PANCard;
    RecyclerView recycler;
    private ArrayList<ModelDashboard> recyclerDataArrayList;
    //WebServiceInterface webServiceInterface;

    /* access modifiers changed from: protected */
        public static String apiStatus="";

    public static void recivedSms(String message)
    {
        apiStatus=message;
        //tvMainBalance.setText(message);
       // tvAepsBalance.setText(message);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_home_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).setDisplayShowTitleEnabled(false);
        inhitViews();

       /* SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences = defaultSharedPreferences;
        this.userid = defaultSharedPreferences.getString("userid", (String) null);
        this.ownerName = this.sharedPreferences.getString("ownername", (String) null);
        this.mobileNo = this.sharedPreferences.getString("mobileno", (String) null);
        this.panCard = this.sharedPreferences.getString("panCard", (String) null);
        this.name = this.sharedPreferences.getString("ownername", (String) null);
        this.email = this.sharedPreferences.getString("email", (String) null);
        this.address = this.sharedPreferences.getString("address", (String) null);
        this.firmName = this.sharedPreferences.getString("firmName", (String) null);*/
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
       // this.url = "http://api.goldenfishdigital.co.in/MAEPS.aspx?Pan=" + this.panCard + "&Name=" + this.name;
        //this.tvNavOwnerName.setText(this.ownerName);
       // TextView textView = this.tvNavMobileNo;
       // textView.setText("Phone : " + this.mobileNo);
        //this.tvLocation.setText(this.address);
        //this.tvEmailAddress.setText(this.email);
       // TextView textView2 = this.tvFirmName;
        //textView2.setText("Firm : " + this.firmName);
       // System.out.println("TEST");
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
        this.prepaidCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Intent intent = new Intent(HomeDashboardActivity.this, RechargeActivity.class);
                intent.putExtra("title", "Prepaid Recharge");
                intent.putExtra(NotificationCompat.CATEGORY_SERVICE, "Mobile");
                intent.putExtra("mainBalance", HomeDashboardActivity.this.balance);
                intent.putExtra("aepsBalance", HomeDashboardActivity.this.aepsBalance);
                HomeDashboardActivity.this.startActivity(intent);*/

                String webViewURL="https://uat.goldenfishdigital.co.in/PrepaidRecharge.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
                String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        this.postpaidCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Intent intent = new Intent(HomeDashboardActivity.this, RechargeActivity.class);
                intent.putExtra("title", "Postpaid Recharge");
                intent.putExtra(NotificationCompat.CATEGORY_SERVICE, "Postpaid");
                intent.putExtra("mainBalance", HomeDashboardActivity.this.balance);
                intent.putExtra("aepsBalance", HomeDashboardActivity.this.aepsBalance);
                HomeDashboardActivity.this.startActivity(intent);*/

                String webViewURL="https://uat.goldenfishdigital.co.in/PostPaidRecharge.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
                String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        this.dthCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              /*  Intent intent = new Intent(HomeDashboardActivity.this, RechargeActivity.class);
                intent.putExtra("title", "DTH Recharge");
                intent.putExtra(NotificationCompat.CATEGORY_SERVICE, "DTH");
                intent.putExtra("mainBalance", HomeDashboardActivity.this.balance);
                intent.putExtra("aepsBalance", HomeDashboardActivity.this.aepsBalance);
                HomeDashboardActivity.this.startActivity(intent);*/
                String webViewURL="https://uat.goldenfishdigital.co.in/DTHRecharge.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
                String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        this.broad_band_card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String webViewURL="https://uat.goldenfishdigital.co.in/BroadBandRecharge.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
                String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        this.moneyTransferCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String webViewURL="https://uat.goldenfishdigital.co.in/MoneyTransfer.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
                String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        this.bus_card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String webViewURL="https://uat.goldenfishdigital.co.in/BusBooking.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
                String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        this.hotel_card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String webViewURL="https://uat.goldenfishdigital.co.in/HotelBooking.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
                String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        this.electricity_card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String webViewURL="https://uat.goldenfishdigital.co.in/Electricity.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
                String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        this.gas_card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String webViewURL="https://uat.goldenfishdigital.co.in/Gas.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
                String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        this.water_card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String webViewURL="https://uat.goldenfishdigital.co.in/Water.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
                String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        this.aepsCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String webViewURL="https://uat.goldenfishdigital.co.in/ApesLogin.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
              //  String webViewURL="https://uat.goldenfishdigital.co.in/ApesLogin.aspx?UserName=AMIT%20SAHANI&PanNo=EQZPS7002H";
               String url = webViewURL.replaceAll(" ","%20");
                Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
                intent.putExtra("url",webViewURL);
                startActivity(intent);
               /* Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webViewURL));
                startActivity(browserIntent);*/
               /* Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(webViewURL));
                startActivity(i);*/
               /* View view1 = LayoutInflater.from(HomeDashboardActivity.this).inflate(R.layout.select_aeps_layout, (ViewGroup) null, false);
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
                });*/
               /* ((LinearLayout) view1.findViewById(R.id.aeps_layout2)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        builder.dismiss();
                        HomeDashboardActivity.this.getLastLocation();
                    }
                });*/
            }
        });
        this.moveToBankLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomeDashboardActivity.this.startActivity(new Intent(HomeDashboardActivity.this, MoveToBankActivity.class));
            }
        });
       /* this.moneyTransferCard.setOnClickListener(new View.OnClickListener() {
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
        });*/
        this.bottomHistoryLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeDashboardActivity.this, GetServices.class);
               // intent.putExtra("title", "All Report");
                HomeDashboardActivity.this.startActivity(intent);
            }
        });

        geoLocation= new GeoLocation(HomeDashboardActivity.this,HomeDashboardActivity.this);

        IntentFilter mfilter = new IntentFilter(
                "android.location.PROVIDERS_CHANGED");
        gpsListener = new GpsListener(HomeDashboardActivity.this, this);
        registerReceiver(gpsListener, mfilter);
    }

   /* public HomeDashboardActivity(Context context)
    {

    }*/
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
    public void launchWTSAEPS() {
        if (this.AepsBal.equalsIgnoreCase("0.00")) {
            this.AepsBal = "0.0";
        }
        System.out.println("lat "+lattitude);
        Intent intent = new Intent(this, WTS_Aeps_Activity.class);
        intent.putExtra("app_Id", "106");
        intent.putExtra("authorise_Key", "XLbdpgCcdK0rcfwDw3pBIdXZxAozuEirBfqyHNPj66srr2JczEMk4A==");
        intent.putExtra("main_wallet_bal", this.AepsBal);
        intent.putExtra("panno",PANCard);
        intent.putExtra("lat",lattitude);
        intent.putExtra("long",longitude);
        startActivity(intent);
    }

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
        geoLocation.startLocationButtonClick();

        if(apiStatus.equalsIgnoreCase(ConstantsValue.CallApiBal))
        {
         //   System.out.println("Hello");
            getWalletBalance();
        }
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
        broad_band_card=findViewById(R.id.broad_band_card);
        this.moneyTransferCard = (CardView) findViewById(R.id.money_transfer_card);
        hotel_card=findViewById(R.id.hotel_card);
        water_card=findViewById(R.id.water_card);
        gas_card=findViewById(R.id.gas_card);
        bus_card=findViewById(R.id.bus_card);
        flight_card=findViewById(R.id.flight_card);
        electricity_card=findViewById(R.id.electricity_card);
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
        recycler=findViewById(R.id.recycler);
        sharedPref = SharedPref.getInstance(HomeDashboardActivity.this);
        userid = sharedPref.getStringWithNull(Constant.userId);
        String FirmName = sharedPref.getStringWithNull(Constant.FirmName);
        String MobileNo1 = sharedPref.getStringWithNull(Constant.MobileNo1);
         OwnerName = sharedPref.getStringWithNull(Constant.OwnerName);
        PANCard = sharedPref.getStringWithNull(Constant.PANCard);
        String EmailId = sharedPref.getStringWithNull(Constant.EmailId);
        String UserType = sharedPref.getStringWithNull(Constant.Usertype);

        this.tvFirmName.setText("Firm Name : "+FirmName);
        this.tvNavMobileNo.setText("Mobile No. : "+MobileNo1);
        this.tvNavOwnerName.setText(OwnerName);
        this.tvEmailAddress.setText(EmailId);
        recyclerDataArrayList=new ArrayList<>();
        recyclerDataArrayList.add(new ModelDashboard("Prepaid",R.drawable.prepaid,"",""));
        recyclerDataArrayList.add(new ModelDashboard("DTH",R.drawable.dish,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Postpaid",R.drawable.postpaid,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Money Transfer",R.drawable.money_transfer,"",""));
        recyclerDataArrayList.add(new ModelDashboard("AEPS",R.drawable.aeps,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Broadband",R.drawable.broadband,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Bus",R.drawable.bus,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Hotel",R.drawable.hotel,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Flight",R.drawable.flight,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Electricity",R.drawable.electricity,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Water",R.drawable.water,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Gas",R.drawable.gas,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Loan EMI",R.drawable.loan_emi,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Purchase Coupon",R.drawable.coupon_pan,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Insurance",R.drawable.insurance_dash,"",""));
        recyclerDataArrayList.add(new ModelDashboard("PSA Registration",R.drawable.user_reg_dash,"",""));
        if(!UserType.equalsIgnoreCase("Retailer"))
        {
            recyclerDataArrayList.add(new ModelDashboard("Add User",R.drawable.add_user_dash,"",""));
        }
        recyclerDataArrayList.add(new ModelDashboard("Education Fee",R.drawable.edu_dash,"",""));
        recyclerDataArrayList.add(new ModelDashboard("ATM",R.drawable.atm_dash,"",""));
        recyclerDataArrayList.add(new ModelDashboard("NSDL Authorized Pan Card",R.drawable.pan_dash,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Cable TV",R.drawable.cable_tv_dash,"",""));
        recyclerDataArrayList.add(new ModelDashboard("Fastag",R.drawable.fastag_dash,"",""));
        // added data from arraylist to adapter class.
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(recyclerDataArrayList,this,this);

        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager=new GridLayoutManager(this,3);

        // at last set adapter to recycler view.
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        //this.tvLocation.setText("Babu");
        getWalletBalance();
    }
    private void getWalletBalance()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Balance");
        progressDialog.setMessage("Please wait while logging in...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetWalletBalance","",userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetWalletBalance(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    try {

                        fullRes = response.body().string();
                       // System.out.println("WALLET "+fullRes);
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            apiStatus="";
                            JSONArray jsonArray= jsonObject1.getJSONArray("Data");
                           MainBal= jsonArray.getJSONObject(0).getString("MainBal");
                            AepsBal= jsonArray.getJSONObject(0).getString("AepsBal");
                            tvMainBalance.setText("₹ " +MainBal);
                            tvAepsBalance.setText("₹ " +AepsBal);
                        }
                        else
                        {
                           // apiStatus="";
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(HomeDashboardActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeDashboardActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case R.id.nav_ledger_report:
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                Intent intent = new Intent(this, LedgerReportActivity.class);
               // intent.putExtra("title", "Ledger Report");
                startActivity(intent);
                break;

            case R.id.nav_all_report:
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                Intent intent3 = new Intent(this, GetServices.class);
            //    Intent intent3 = new Intent(this, AllReportsActivity.class);
                //intent3.putExtra("title", "All Report");
                startActivity(intent3);
                break;
            case R.id.nav_update_mpin:
                Intent intent5 = new Intent(this, UpdateMpinActivity.class);
                //intent5.putExtra("title", "Update Mpin");
                startActivity(intent5);
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                break;
            case R.id.nav_change_password:
                Intent intent4 = new Intent(this, ChangePasswordActivity.class);
               //  intent4.putExtra("title", "Change Password");
                startActivity(intent4);
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                break;
            case R.id.nav_complaint:
               /* this.drawer.closeDrawer((int) GravityCompat.START, false);
                Intent intent5 = new Intent(this, ComplaintActivity.class);
                intent5.putExtra("title", "Make Complaint");
                startActivity(intent5);*/
                break;
            case R.id.nav_credit_report:
              /*  Intent intent6 = new Intent(this, CreditDebitReportActivity.class);
                intent6.putExtra("title", "Credit Report");
                startActivity(intent6);
                this.drawer.closeDrawer((int) GravityCompat.START, false);*/
                break;
            case R.id.nav_debit_report:
               /* this.drawer.closeDrawer((int) GravityCompat.START, false);
                Intent intent7 = new Intent(this, CreditDebitReportActivity.class);
                intent7.putExtra("title", "Debit Report");
                startActivity(intent7);*/
                break;
            case R.id.nav_home:
                this.drawer.closeDrawer((int) GravityCompat.START, true);
                break;

            case R.id.nav_log_out:
                new AlertDialog.Builder(this).setTitle((CharSequence) "Confirmation").setMessage((CharSequence) "Are you sure ?").setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                      sharedPref.clearData();
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

            case R.id.nav_support:
               // showSupportDialog();
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                break;

            case R.id.nav_add_payout:
                this.drawer.closeDrawer((int) GravityCompat.START, false);
                startActivity(new Intent(this, AddPayoutAcc.class));
                break;
        }
        return false;
    }

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
    public void onDataReceived(String myData, Double latitude, Double longitude,String address) {
        this.lattitude=latitude;
        this.longitude=longitude;
        this.tvLocation.setText(address);
        System.out.println("LAt "+lattitude);
    }

    @Override
    public void dashboardClick(String val) {
        if(val.equalsIgnoreCase("prepaid"))
        {
            Intent intent = new Intent(HomeDashboardActivity.this, RechargeMainActivity.class);
             intent.putExtra(Constant.service_name,ConstantsValue.MobilePrepaid);
             intent.putExtra(Constant.service_id,ConstantsValue.MobilePrepaidId);
             intent.putExtra(Constant.MainBal,MainBal);
             intent.putExtra(Constant. AepsBal, AepsBal);
            startActivity(intent);
          /*  String webViewURL="https://uat.goldenfishdigital.co.in/PrepaidRecharge.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);*/
        }
        else if(val.equalsIgnoreCase("dth"))
        {

            Intent intent = new Intent(HomeDashboardActivity.this, RechargeMainActivity.class);
            intent.putExtra(Constant.service_name,ConstantsValue.DTH);
            intent.putExtra(Constant.service_id,ConstantsValue.DTHId);
            intent.putExtra(Constant.MainBal,MainBal);
            intent.putExtra(Constant. AepsBal, AepsBal);
            startActivity(intent);

           /* String webViewURL="https://uat.goldenfishdigital.co.in/DTHRecharge.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);*/
        }

        else if(val.equalsIgnoreCase("postpaid"))
        {
            Intent intent = new Intent(HomeDashboardActivity.this, RechargeMainActivity.class);
            intent.putExtra(Constant.service_name,ConstantsValue.MobilePostPaid);
            intent.putExtra(Constant.service_id,ConstantsValue.MobilePostPaidId);
            intent.putExtra(Constant.MainBal,MainBal);
            intent.putExtra(Constant. AepsBal, AepsBal);
            startActivity(intent);

           /* String webViewURL="https://uat.goldenfishdigital.co.in/PostPaidRecharge.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);*/
        }

        else if(val.equalsIgnoreCase("Money Transfer"))
        {
            String webViewURL="https://uat.goldenfishdigital.co.in/MoneyTransfer.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
        else if(val.equalsIgnoreCase("AEPS"))
        {
            /*String webViewURL="https://uat.goldenfishdigital.co.in/ApesLogin.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webViewURL));
            startActivity(browserIntent);*/
           /* String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);*/

            launchWTSAEPS();
        }
        else if(val.equalsIgnoreCase("Broadband"))
        {
            String webViewURL="https://uat.goldenfishdigital.co.in/BroadBandRecharge.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
        else if(val.equalsIgnoreCase("Bus"))
        {
            String webViewURL="https://uat.goldenfishdigital.co.in/BusBooking.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
        else if(val.equalsIgnoreCase("Hotel"))
        {
            String webViewURL="https://uat.goldenfishdigital.co.in/HotelBooking.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
        else if(val.equalsIgnoreCase("Flight"))
        {

        }
        else if(val.equalsIgnoreCase("Electricity"))
        {
            String webViewURL="https://uat.goldenfishdigital.co.in/Electricity.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
        else if(val.equalsIgnoreCase("Water"))
        {
            String webViewURL="https://uat.goldenfishdigital.co.in/Water.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
        else if(val.equalsIgnoreCase("Gas"))
        {
            String webViewURL="https://uat.goldenfishdigital.co.in/Gas.aspx?UserName="+OwnerName+"&PanNo="+PANCard;
            String url = webViewURL.replaceAll(" ","%20");
            Intent intent = new Intent(HomeDashboardActivity.this, WebviewAeps.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
        else if(val.equalsIgnoreCase("Add User"))
        {
            Intent intent = new Intent(HomeDashboardActivity.this, AddUserActivity.class);
           // intent.putExtra("url",url);
            startActivity(intent);
        }
        else if(val.equalsIgnoreCase("Purchase Coupon"))
        {
            /*Intent intent = new Intent(HomeDashboardActivity.this, PurchaseCouponActivity.class);
            // intent.putExtra("url",url);
            startActivity(intent);*/
            getPsaStatus("purchase");
        }
        else if(val.equalsIgnoreCase("PSA Registration"))
        {
            getPsaStatus("registration");
        }
    }

    @Override
    public void walletBal(String status) {
        System.out.println("MY BAL "+status);
    }


    private void getPsaStatus(String type)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetPSAStatus","",userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetPSAStatus(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    try {
                        fullRes = response.body().string();
                        System.out.println("FINAL RESP1 "+fullRes);
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {

                            JSONArray jsonArray= jsonObject1.getJSONArray("Data");
                            String  status= jsonArray.getJSONObject(0).getString("status");
                            String VLEId= jsonObject1.getString("VLEId");
                      if(type.equalsIgnoreCase("registration"))
                      {
                          if (status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("NA")) {
                              Intent intent = new Intent(HomeDashboardActivity.this, PsaRegistrationActivity.class);
                              intent.putExtra("vle_id",VLEId);
                              startActivity(intent);
                          } else {
                              PanDialog(status);
                          }
                      }
                      else if(type.equalsIgnoreCase("purchase"))
                      {
                          if (status.equalsIgnoreCase("Approved"))
                          {
                              Intent intent = new Intent(HomeDashboardActivity.this, PurchaseCouponActivity.class);
                              intent.putExtra("vle_id",VLEId);
                                startActivity(intent);
                          } else {
                              PanDialog(status);
                          }
                      }


                        }
                        else
                        {

                            progressDialog.dismiss();
                            Toast.makeText(HomeDashboardActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeDashboardActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PanDialog(String status) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(HomeDashboardActivity.this).create();
        final LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.alert_dialog_cmn, null);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button pan_btn = convertView.findViewById(R.id.pan_btn);
        //ImageView close = convertView.findViewById(R.id.close);
        final TextView pan_heading = convertView.findViewById(R.id.pan_heading);
        final TextView pan_status = convertView.findViewById(R.id.pan_status);

        if(status.equalsIgnoreCase("Pending"))
        {
            pan_status.setText(status);
            pan_heading.setText("Your PSA Registartion request is under Process, Please wait.");
        }
        else if(status.equalsIgnoreCase("Approved"))
        {
            pan_status.setText(status);
            pan_heading.setText("You are successfully register as PSA.");
        }
        else if(status.equalsIgnoreCase("Rejected"))
        {
            pan_status.setText(status);
            pan_heading.setText("Your PSA Registration request is rejected by UTI. Kindly register again with correct information.");
        }
        else
        {
            pan_status.setText(status);
            pan_heading.setText("PSA Id is mandatory for Purchase Coupon");
        }
        pan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(convertView);
        alertDialog.show();
        alertDialog.setCancelable(false);
    }
}
