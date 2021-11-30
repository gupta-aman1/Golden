package com.example.goldenfish.ledgerreopt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenfish.Common.CommonFun;
import com.example.goldenfish.Common.CommonInterface;
import com.example.goldenfish.Constants.Constant;
import com.example.goldenfish.Constants.ConstantsValue;
import com.example.goldenfish.R;
import com.example.goldenfish.Retrofit.RetrofitClient;
import com.example.goldenfish.Utilities.MyUtils;
import com.example.goldenfish.Utilities.SharedPref;
import com.example.goldenfish.sidebar.allReports.AllReportAdapterNew;
import com.example.goldenfish.sidebar.allReports.AllReportsActivity;
import com.example.goldenfish.sidebar.allReports.modelAllReports.AllReport;
import com.example.goldenfish.sidebar.allReports.modelAllReports.AllReportNew;
import com.example.goldenfish.sidebar.allReports.modelAllReports.ModelMainNew;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

public class LedgerReportActivity extends AppCompatActivity implements CommonInterface {
    final Calendar myCalendar = Calendar.getInstance();
    public ArrayList<String> allReportsHead = new ArrayList<>();
    public ViewPager viewPager;
    ArrayList<AllReport> allReports;
    LinearLayout to_date_layout, from_date_layout;
    String choosedate = "", toDate = "", fromDate = "";
    TextView tv_to_date, tv_from_date;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private String userid,service_id="";
    private TabLayout tabLayout;
    ImageView back_button;
    RecyclerView recycler_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger_report);
        to_date_layout = findViewById(R.id.to_date_layout);
        tv_to_date = findViewById(R.id.tv_to_date);
        back_button=findViewById(R.id.back_button);
        tv_from_date = findViewById(R.id.tv_from_date);
        from_date_layout = findViewById(R.id.from_date_layout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);
        tabLayout = findViewById(R.id.tabLayout);
        recycler_data=findViewById(R.id.recycler_data);
        SharedPref sharedPref = SharedPref.getInstance(LedgerReportActivity.this);
        userid = sharedPref.getStringWithNull(Constant.userId);
        Toolbar toolbar = findViewById(R.id.toolbar);
        // toolbar.setTitle("");
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.activity_title);

        toolbarTitle.setText("Statement Details");
        Date date1 = new Date();
        fromDate = formatter.format(date1);
        toDate = formatter.format(date1);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        CommonFun.requestStoragePermission(LedgerReportActivity.this, LedgerReportActivity.this);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
              /*  myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);*/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                int month = monthOfYear + 1;
                String fm = "" + month;
                String fd = "" + dayOfMonth;
                if (month < 10) {
                    fm = "0" + month;
                }
                if (dayOfMonth < 10) {
                    fd = "0" + dayOfMonth;
                }
                String date = "" + year + "-" + fm + "-" + fd;
                if (choosedate.equals("todate")) {

                    tv_to_date.setText(date);
                    toDate = date.trim();

                    if (tv_from_date.getText().toString().trim().equalsIgnoreCase("Select Date")) {
                        Toast.makeText(LedgerReportActivity.this, "Please choose From Date", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            Date date1 = sdf.parse(fromDate);
                            Date date2 = sdf.parse(toDate);
                          /*  if (date1.compareTo(date2) < 0) {
                                // getAllReports();.
                                CommonFun.requestStoragePermission(AllReportsActivity.this, AllReportsActivity.this);
                            } else {
                                Toast.makeText(AllReportsActivity.this, "To Date must be greater than From Date", Toast.LENGTH_SHORT).show();
                            }*/
                            if (fromDate.equals("")) {
                                Toast.makeText(LedgerReportActivity.this, "Please choose From Date to filter", Toast.LENGTH_SHORT).show();
                            }
                            else if(date2.compareTo(date1) < 0)
                            {
                                Toast.makeText(LedgerReportActivity.this, "To Date must be greater or equal to From Date", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                CommonFun.requestStoragePermission(LedgerReportActivity.this, LedgerReportActivity.this);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tv_from_date.setText(date);
                    fromDate = date.trim();
                    if (tv_to_date.getText().toString().trim().equalsIgnoreCase("Select date"))
                    {
                        Toast.makeText(LedgerReportActivity.this, "Please choose To Date", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            Date date1 = sdf.parse(fromDate);
                            Date date2 = sdf.parse(toDate);
                           /* if (date1.compareTo(date2) > 0) {
                                Toast.makeText(AllReportsActivity.this, "From Date must be less than To Date", Toast.LENGTH_SHORT).show();
                            } else {
                                //getAllReports();
                                CommonFun.requestStoragePermission(AllReportsActivity.this, AllReportsActivity.this);
                            }*/
                            if (toDate.equals("")) {
                                Toast.makeText(LedgerReportActivity.this, "Please choose To Date to filter", Toast.LENGTH_SHORT).show();
                            }
                            else if(date1.compareTo(date2) > 0)
                            {
                                Toast.makeText(LedgerReportActivity.this, "From Date must be less or equal to To Date", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                CommonFun.requestStoragePermission(LedgerReportActivity.this, LedgerReportActivity.this);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }
        };

        to_date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosedate = "todate";
                DatePickerDialog datePickerDialog = new DatePickerDialog(LedgerReportActivity.this, R.style.AlertDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        from_date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosedate = "fromdate";
                DatePickerDialog datePickerDialog = new DatePickerDialog(LedgerReportActivity.this, R.style.AlertDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }


    @Override
    public void requestPermission(boolean status) {
        if (status) {
            getAllReports();
        } else {
            CommonFun.showSettingsDialog(LedgerReportActivity.this);
        }
    }


    private void getAllReports() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Reports..");
        // progressDialog.setMessage("Please wait while logging in...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Userid", userid);
        jsonObject.addProperty("FromDate", fromDate);
        jsonObject.addProperty("ToDate", toDate);

        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetWalletBiilingSummary",  fromDate + "|" + toDate, userid));
        Call<ModelMainLedger> call = RetrofitClient.getInstance().getApi().GetWalletBiilingSummary(jsonObject);

        System.out.println("Req " + jsonObject);
        call.enqueue(new Callback<ModelMainLedger>() {
            @Override
            public void onResponse(Call<ModelMainLedger> call, retrofit2.Response<ModelMainLedger> response) {

                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String st = null;
                    try {
                        //String service_id="";
                        st = response.body().getStatuscode();

                        if (st.equalsIgnoreCase(ConstantsValue.successful)) {
                            ArrayList<ModelDataLedger> allReports = (ArrayList<ModelDataLedger>) response.body().getData();
                            Adapterledger allReportsAdapter = new Adapterledger(LedgerReportActivity.this,allReports);
                            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(LedgerReportActivity.this, LinearLayoutManager.VERTICAL, false);
                            recycler_data.setLayoutManager(verticalLayoutManager);
                            // allReportsAdapter.notifyDataSetChanged();
                            recycler_data.setAdapter(allReportsAdapter);
                        } else {
                            Toast.makeText(LedgerReportActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ModelMainLedger> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LedgerReportActivity.this, "Due to Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}