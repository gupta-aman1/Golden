package com.example.goldenfish.Sidebar.AllReports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenfish.Common.CommonFun;
import com.example.goldenfish.Common.CommonInterface;
import com.example.goldenfish.Constants.Constant;
import com.example.goldenfish.Constants.ConstantsValue;
import com.example.goldenfish.Dashboard.HomeDashboardActivity;
import com.example.goldenfish.MoveToBank.SuceessScreen;
import com.example.goldenfish.R;
import com.example.goldenfish.Retrofit.RetrofitClient;
import com.example.goldenfish.Sidebar.AllReports.ModelAllReports.AllReport;
import com.example.goldenfish.Sidebar.AllReports.ModelAllReports.ModelMainClass;
import com.example.goldenfish.Utilities.MyUtils;
import com.example.goldenfish.Utilities.SharedPref;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AllReportsActivity extends AppCompatActivity implements CommonInterface {
private String userid;
ArrayList<AllReport> allReports;
    public ArrayList<String> allReportsHead= new ArrayList<>();
    public ViewPager viewPager;
    private TabLayout tabLayout;
    LinearLayout to_date_layout,from_date_layout;
    final Calendar myCalendar = Calendar.getInstance();
    String choosedate="",toDate="",fromDate="";
    TextView tv_to_date,tv_from_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reports);
        to_date_layout=findViewById(R.id.to_date_layout);
         tv_to_date = findViewById(R.id.tv_to_date);
         tv_from_date = findViewById(R.id.tv_from_date);
        from_date_layout=findViewById(R.id.from_date_layout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);
        tabLayout = findViewById(R.id.tabLayout);
       SharedPref sharedPref = SharedPref.getInstance(AllReportsActivity.this);
        userid = sharedPref.getStringWithNull(Constant.userId);

        CommonFun.requestStoragePermission(AllReportsActivity.this,AllReportsActivity.this);



        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if(choosedate.equals("todate")) {

                    tv_to_date.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                    toDate=dayOfMonth + "-" + monthOfYear + "-" + year;
                }
                else
                {
                    tv_from_date.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                    fromDate=dayOfMonth + "-" + monthOfYear + "-" + year;
                }
            }
        };

        to_date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosedate="todate";
                DatePickerDialog datePickerDialog=  new DatePickerDialog(AllReportsActivity.this,R.style.AlertDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        from_date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosedate="fromdate";
                DatePickerDialog datePickerDialog=  new DatePickerDialog(AllReportsActivity.this,R.style.AlertDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    private void getAllReports()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Reports");
       // progressDialog.setMessage("Please wait while logging in...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid","6178");
        jsonObject.addProperty("Filterby","ALL");
        jsonObject.addProperty("FromDate","2021-09-10");
        jsonObject.addProperty("ToDate","2021-09-10");
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetALLReports","ALL"+"|"+"2021-09-10"+"|"+"2021-09-10","6178"));
        Call<ModelMainClass> call = RetrofitClient.getInstance().getApi().GetALLReports(jsonObject);
        call.enqueue(new Callback<ModelMainClass>() {
            @Override
            public void onResponse(Call<ModelMainClass> call, retrofit2.Response<ModelMainClass> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    try {
                        fullRes = response.body().getMessage();
                        //System.out.println("FULL RESP "+fullRes);
                  //  allReports.clear();
                       allReports= (ArrayList<AllReport>) response.body().getData();

                       for(int i=0;i<allReports.size();i++)
                       {
                          String type= allReports.get(i).getStype();
                           allReportsHead.add(type);
                         //  System.out.println("FULL RESP1 "+type);
                       }

                        Set<String> set = new HashSet<>(allReportsHead);
                        allReportsHead.clear();
                        allReportsHead.addAll(set);

                        setupViewPagers(viewPager,allReportsHead);
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
                          //  Toast.makeText(AllReportsActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }*/
                  //  }
                catch (Exception e) {
                        e.printStackTrace();
                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ModelMainClass> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AllReportsActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewPagers(ViewPager viewPager,List<String> name) {
        List<Fragment> mFragmentList = new ArrayList<>();
        List<String> mFragmentTitleList = new ArrayList<>();
        ViewPagerAdapters adapter = new ViewPagerAdapters(getSupportFragmentManager());
        //mFragmentList.add(new FullTTFragment());

        //  mFragmentList.add(new TopUpFragment(2));
        //  mFragmentList.add(new TopUpFragment(3));

        for(int i=0;i<name.size();i++)
        {
            mFragmentTitleList.add(name.get(i));
            mFragmentList.add(new AllReportFragment(i));
        }
        adapter.addFragment(mFragmentList,mFragmentTitleList);
        // adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

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
            mFragmentList=fragment;
            mFragmentTitleList=title;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

    @Override
    public void requestPermission(boolean status) {
        if(status)
        {
            getAllReports();
        }
        else
        {
            CommonFun.showSettingsDialog(AllReportsActivity.this);
        }
    }
}