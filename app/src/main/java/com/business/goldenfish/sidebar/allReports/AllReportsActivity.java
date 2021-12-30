package com.business.goldenfish.sidebar.allReports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
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
import androidx.appcompat.widget.Toolbar;

import com.business.goldenfish.Common.CommonFun;
import com.business.goldenfish.Common.CommonInterface;
import com.business.goldenfish.Constants.Constant;
import com.business.goldenfish.Constants.ConstantsValue;
import com.business.goldenfish.R;
import com.business.goldenfish.Retrofit.RetrofitClient;
import com.business.goldenfish.sidebar.allReports.modelAllReports.AllReport;
import com.business.goldenfish.sidebar.allReports.modelAllReports.AllReportNew;
import com.business.goldenfish.sidebar.allReports.modelAllReports.ModelMainNew;
import com.business.goldenfish.Utilities.MyUtils;
import com.business.goldenfish.Utilities.SharedPref;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AllReportsActivity extends AppCompatActivity implements CommonInterface {
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
        setContentView(R.layout.activity_all_reports);
        to_date_layout = findViewById(R.id.to_date_layout);
        tv_to_date = findViewById(R.id.tv_to_date);
        back_button=findViewById(R.id.back_button);
        tv_from_date = findViewById(R.id.tv_from_date);
        from_date_layout = findViewById(R.id.from_date_layout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);
        tabLayout = findViewById(R.id.tabLayout);
        recycler_data=findViewById(R.id.recycler_data);
        SharedPref sharedPref = SharedPref.getInstance(AllReportsActivity.this);
        userid = sharedPref.getStringWithNull(Constant.userId);
        Toolbar toolbar = findViewById(R.id.toolbar);
       // toolbar.setTitle("");
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.activity_title);

        Date date1 = new Date();
        fromDate = formatter.format(date1);
        toDate = formatter.format(date1);

        // getAllReports();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if(getIntent().getExtras()!=null)
        {
            service_id= getIntent().getStringExtra(Constant.service_id);
            System.out.println("MY SERVICE "+service_id);
            toolbarTitle.setText(service_id);
        }

        CommonFun.requestStoragePermission(AllReportsActivity.this, AllReportsActivity.this);

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
                        Toast.makeText(AllReportsActivity.this, "Please choose From Date", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(AllReportsActivity.this, "Please choose From Date to filter", Toast.LENGTH_SHORT).show();
                            }
                            else if(date2.compareTo(date1) < 0)
                            {
                                Toast.makeText(AllReportsActivity.this, "To Date must be greater or equal to From Date", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                CommonFun.requestStoragePermission(AllReportsActivity.this, AllReportsActivity.this);
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
                        Toast.makeText(AllReportsActivity.this, "Please choose To Date", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(AllReportsActivity.this, "Please choose To Date to filter", Toast.LENGTH_SHORT).show();
                            }
                            else if(date1.compareTo(date2) > 0)
                            {
                                Toast.makeText(AllReportsActivity.this, "From Date must be less or equal to To Date", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                CommonFun.requestStoragePermission(AllReportsActivity.this, AllReportsActivity.this);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AllReportsActivity.this, R.style.AlertDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        from_date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosedate = "fromdate";
                DatePickerDialog datePickerDialog = new DatePickerDialog(AllReportsActivity.this, R.style.AlertDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    private void getAllReports() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Reports..");
        // progressDialog.setMessage("Please wait while logging in...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Userid", userid);
        jsonObject.addProperty("Filterby",service_id);
        jsonObject.addProperty("FromDate", fromDate);
        jsonObject.addProperty("ToDate", toDate);

       // System.out.println("MY REQ "+jsonObject);
//        jsonObject.addProperty("FromDate", fromDate);
//        jsonObject.addProperty("ToDate", toDate);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetALLReports", service_id + "|" + fromDate + "|" + toDate, userid));
        Call<ModelMainNew> call = RetrofitClient.getInstance().getApi().GetALLReports(jsonObject);

        System.out.println("Req " + jsonObject);
        call.enqueue(new Callback<ModelMainNew>() {
            @Override
            public void onResponse(Call<ModelMainNew> call, retrofit2.Response<ModelMainNew> response) {

                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String st = null;
                    try {
                        //String service_id="";
                        st = response.body().getStatuscode();

                        if (st.equalsIgnoreCase(ConstantsValue.successful)) {
                           ArrayList<AllReportNew> allReports = (ArrayList<AllReportNew>) response.body().getData();
                           AllReportAdapterNew allReportsAdapter = new AllReportAdapterNew(AllReportsActivity.this,allReports,service_id);
                            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(AllReportsActivity.this, LinearLayoutManager.VERTICAL, false);
                            recycler_data.setLayoutManager(verticalLayoutManager);
                            // allReportsAdapter.notifyDataSetChanged();
                            recycler_data.setAdapter(allReportsAdapter);

                          /*  for (int i = 0; i < allReports.size(); i++) {
                                String type = allReports.get(i).getStype();
                                allReportsHead.add(type);
                            }

                            Set<String> set = new HashSet<>(allReportsHead);
                            allReportsHead.clear();
                            allReportsHead.addAll(set);*/

                           // setupViewPagers(viewPager, allReportsHead);
                        } else {
                            Toast.makeText(AllReportsActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        //System.out.println("FULL RESP "+fullRes);
                        //  allReports.clear();


                        //  JSONObject jsonObject1= new JSONObject(fullRes);
                        //String stCode= jsonObject1.getString(Constant.StatusCode);
                        /*if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                           // JSONArray jsonArray= jsonObject1.getJSONArray("Data");
                           // List< AllReport > arr= (List<AllReport>) jsonArray;
                            /*String  MainBal= jsonArray.getJSONObject(0).getString("MainBal");
                            String AepsBal= jsonArray.getJSONObject(0).getString("AepsBal");
                            tvMainBalance.setText("₹ " +MainBal);
                            tvAepsBalance.setText("₹ " +AepsBal);*/
                    }
                       /* else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();

                        }*/
                    //  }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ModelMainNew> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AllReportsActivity.this, "Due to Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewPagers(ViewPager viewPager, List<String> name) {
        List<Fragment> mFragmentList = new ArrayList<>();
        List<String> mFragmentTitleList = new ArrayList<>();
        ViewPagerAdapters adapter = new ViewPagerAdapters(getSupportFragmentManager());
        //mFragmentList.add(new FullTTFragment());

        //  mFragmentList.add(new TopUpFragment(2));
        //  mFragmentList.add(new TopUpFragment(3));

        for (int i = 0; i < name.size(); i++) {
            mFragmentTitleList.add(name.get(i));
            mFragmentList.add(new AllReportFragment(i));
        }
        adapter.addFragment(mFragmentList, mFragmentTitleList);
        // adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void requestPermission(boolean status) {
        if (status) {
            getAllReports();
        } else {
            CommonFun.showSettingsDialog(AllReportsActivity.this);
        }
    }

    class ViewPagerAdapters extends FragmentStatePagerAdapter {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapters(FragmentManager manager) {

            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
          /*  if(mFragmentTitleList.size()>mFragmentList.size())
            {
                return mFragmentList.size();
            }
            else {
                return mFragmentTitleList.size();
            }*/
        }

        public void addFragment(List<Fragment> fragment, List<String> title) {
            mFragmentList = fragment;
            mFragmentTitleList = title;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }
}