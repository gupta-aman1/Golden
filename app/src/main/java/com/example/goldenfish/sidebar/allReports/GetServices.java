package com.example.goldenfish.sidebar.allReports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.goldenfish.Constants.Constant;
import com.example.goldenfish.Constants.ConstantsValue;
import com.example.goldenfish.R;
import com.example.goldenfish.Retrofit.RetrofitClient;
import com.example.goldenfish.sidebar.allReports.modelAllReports.ModelServiceData;
import com.example.goldenfish.Utilities.MyUtils;
import com.example.goldenfish.Utilities.SharedPref;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class GetServices extends AppCompatActivity {
    String userid;
    SharedPref sharedPref;
    RecyclerView recycler_services;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_services);
        sharedPref = SharedPref.getInstance(GetServices.this);
        userid = sharedPref.getStringWithNull(Constant.userId);
        recycler_services=findViewById(R.id.recycler_services);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getServices();
    }

    private void getServices()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Services.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetAllServiceForReports","",userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetAllServiceForReports(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    try {
                        progressDialog.dismiss();
                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            JSONArray rootarr=jsonObject1.getJSONArray("Data");

                            ArrayList <ModelServiceData>  modelServiceData= new ArrayList<>();

                            for (int i=0;i<rootarr.length();i++)
                            {
                                JSONObject innerobj= rootarr.getJSONObject(i);

                                String ServiceId=innerobj.getString("ServiceId");
                                String ServiceName=innerobj.getString("ServiceName");

                                modelServiceData.add(new ModelServiceData(ServiceId,ServiceName));
                                //acc.add(AccountDetails);
                             //  acc_id.add(id);
                            }
                            AdapterGetServies adapter = new AdapterGetServies(modelServiceData, GetServices.this);
                            recycler_services.setLayoutManager(new LinearLayoutManager(GetServices.this, RecyclerView.VERTICAL, false));
                            recycler_services.setAdapter(adapter);


                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(GetServices.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(GetServices.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}