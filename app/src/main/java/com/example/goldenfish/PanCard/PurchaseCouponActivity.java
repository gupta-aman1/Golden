package com.example.goldenfish.PanCard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenfish.Common.CommonApi;
import com.example.goldenfish.Common.CommonInterface;
import com.example.goldenfish.Constants.Constant;
import com.example.goldenfish.Constants.ConstantsValue;
import com.example.goldenfish.MoveToBank.MoveToBankActivity;
import com.example.goldenfish.PanCard.ModelPan.ModelCouponPan;
import com.example.goldenfish.R;
import com.example.goldenfish.Retrofit.RetrofitClient;
import com.example.goldenfish.Sidebar.AllReports.AllReportsActivity;
import com.example.goldenfish.Sidebar.AllReports.ModelAllReports.AllReport;
import com.example.goldenfish.Sidebar.AllReports.ModelAllReports.ModelMainClass;
import com.example.goldenfish.Utilities.MyUtils;
import com.example.goldenfish.Utilities.SharedPref;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;

public class PurchaseCouponActivity extends AppCompatActivity implements CommonInterface {
    SharedPref sharedPref;
    String userid,selectedcouponId="";
    Spinner et_coupon;
    HashMap<String,String> coupon_hashmap= new HashMap<>();
    TextView show_surcharge,show_final;
    EditText et_qty;
    Double ChargePer=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_coupon);
        sharedPref = SharedPref.getInstance(PurchaseCouponActivity.this);
        userid = sharedPref.getStringWithNull(Constant.userId);
        et_coupon=findViewById(R.id.et_wallet);
        show_surcharge=findViewById(R.id.show_surcharge);
        et_qty =findViewById(R.id.et_qty);
        show_final=findViewById(R.id.show_final);
        et_qty.addTextChangedListener(rateTextWatcher);
        getCouponType();
        SpinnerValue();

    }

    private TextWatcher rateTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String rate = et_qty.getText().toString().trim();

                if (rate.equalsIgnoreCase("")) {
                    show_final.setVisibility(View.GONE);
                    // Toast.makeText(PurchaseCouponActivity.this, "Please Choose Coupon Type", Toast.LENGTH_SHORT).show();
                } else {
                    Double newRate = Double.parseDouble(rate);
                    calculateAndShow(newRate);
                }
        }
    };

    private void calculateAndShow(Double rate){
        double NetRate = rate * ChargePer;
       // double Total = (NetRate / 10) * wt;
        if(!selectedcouponId.equalsIgnoreCase("-2"))
        {
        show_final.setVisibility(View.VISIBLE);
        show_final.setText("Final Amount : ₹"+String.valueOf(NetRate)+" /-");
        }
     /*  else
        {
            Toast.makeText(PurchaseCouponActivity.this, "Please Choose Coupon Type", Toast.LENGTH_SHORT).show();
        }*/

    }
    private void getCouponType() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Coupon..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Userid", userid);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetPanCardCouponType", "", userid));
        Call<ModelCouponPan> call = RetrofitClient.getInstance().getApi().GetPanCardCouponType(jsonObject);
        call.enqueue(new Callback<ModelCouponPan>() {
            @Override
            public void onResponse(Call<ModelCouponPan> call, retrofit2.Response<ModelCouponPan> response) {

                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String st = null;
                    try {
                        st = response.body().getStatuscode();

                        if (st.equalsIgnoreCase(ConstantsValue.successful)) {
                            ArrayList<String> opname= new ArrayList<>();
                            ArrayList<String> op_id= new ArrayList<>();
                            op_id.add("-2");
                            opname.add("Select Coupon Type");
                            for (int i=0;i<response.body().getData().size();i++)
                            {
                                String id= response.body().getData().get(i).getId();
                                String opName= response.body().getData().get(i).getOperatorName();
                                opname.add(opName);
                                op_id.add(id);
                            }
                            ArrayAdapter dataAdapter = new ArrayAdapter(PurchaseCouponActivity.this, R.layout.support_simple_spinner_dropdown_item, opname);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            et_coupon.setAdapter(dataAdapter);
                            for(int i=0;i<opname.size();i++)
                            {
                                coupon_hashmap.put(opname.get(i),op_id.get(i));
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(PurchaseCouponActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    catch (Exception e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }

                } else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ModelCouponPan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PurchaseCouponActivity.this, "Due to Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SpinnerValue()
    {
        et_coupon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value= adapterView.getItemAtPosition(i).toString();

                selectedcouponId= coupon_hashmap.get(value);
                //Toast.makeText(PurchaseCouponActivity.this, ""+selectedcouponId, Toast.LENGTH_SHORT).show();

                if(!selectedcouponId.equalsIgnoreCase("-2"))
                {
                    JsonObject jsonObject= new JsonObject();
                    jsonObject.addProperty("Userid",userid);
                    jsonObject.addProperty("OpId",selectedcouponId);
                    jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetSurchargeUsingOpId",selectedcouponId,userid));
                    CommonApi.getSurchargeByOperator(PurchaseCouponActivity.this,jsonObject,PurchaseCouponActivity.this);
                }
                else
                {
                    show_final.setVisibility(View.GONE);
                    show_surcharge.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public void getMoveToBankSurcharge(String CommPer, String CommType, String ChargePer, String ChargeType) {
        this.ChargePer= Double.parseDouble(ChargePer);
            show_surcharge.setVisibility(View.VISIBLE);
        show_surcharge.setText("₹ " + ChargePer+ " / Coupon.");

        if(!et_qty.getText().toString().trim().equalsIgnoreCase(""))
        {
            String rate = et_qty.getText().toString().trim();
                Double newRate = Double.parseDouble(rate);
            calculateAndShow(newRate);
        }

    }
    public void ProceedCouponPuchase(View view) {
    }
}