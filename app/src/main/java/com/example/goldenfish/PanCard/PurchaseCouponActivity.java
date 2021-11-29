package com.example.goldenfish.PanCard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenfish.Common.CommonApi;
import com.example.goldenfish.Common.CommonFun;
import com.example.goldenfish.Common.CommonInterface;
import com.example.goldenfish.Constants.Constant;
import com.example.goldenfish.Constants.ConstantsValue;
import com.example.goldenfish.MoveToBank.DetailedData;
import com.example.goldenfish.MoveToBank.SuceessScreen;
import com.example.goldenfish.PanCard.ModelPan.ModelCouponPan;
import com.example.goldenfish.R;
import com.example.goldenfish.Retrofit.RetrofitClient;
import com.example.goldenfish.Utilities.MyUtils;
import com.example.goldenfish.Utilities.SharedPref;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class PurchaseCouponActivity extends AppCompatActivity implements CommonInterface {
    SharedPref sharedPref;
    String userid,selectedcouponId="", VLEId="",CouponType="";
    Spinner et_coupon;
    HashMap<String,String> coupon_hashmap= new HashMap<>();
    TextView show_surcharge,show_final;
    EditText et_qty,et_vleid;
    Double ChargePer=0.0;
    Double finalAmount=0.0;
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
        et_vleid=findViewById(R.id.et_vleid);
        et_qty.addTextChangedListener(rateTextWatcher);
       // GetVLEIdForPanCard();

        if(getIntent().getExtras()!=null)
        {
            VLEId=getIntent().getStringExtra("vle_id");
            et_vleid.setText("VLE id -"+VLEId);
        }
        getCouponType();
        SpinnerValue();

    }
    private void GetVLEIdForPanCard() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Userid", userid);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetVLEIdForPanCard", "", userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetVLEIdForPanCard(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String st = null;
                    try {
                        st = response.body().string();
                        JSONObject jsonObject1= new JSONObject(st);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful)) {

                            JSONArray jsonArray = jsonObject1.getJSONArray("Data");
                            VLEId=  jsonArray.getJSONObject(0).getString("VLEId");
                            et_vleid.setText("VLE id -"+VLEId);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(PurchaseCouponActivity.this, "" + jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PurchaseCouponActivity.this, "Due to Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                    finalAmount=0.0;
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
            finalAmount=NetRate;
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
                CouponType=value;
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
                    finalAmount=0.0;
                    CouponType="";
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
        if(VLEId.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Unable to fetch VLE ID", Toast.LENGTH_SHORT).show();
        }
        else if(selectedcouponId.equalsIgnoreCase("-2"))
        {
            Toast.makeText(this, "Please Choose Coupon Type", Toast.LENGTH_SHORT).show();
        }
        else if(et_qty.getText().toString().trim().equalsIgnoreCase(""))
        {
            et_qty.setError("Required");
        }
        else if(finalAmount ==0.0 || finalAmount ==0)
        {
            Toast.makeText(this, "Amount should be greater than zero", Toast.LENGTH_SHORT).show();
        }
        else
        {
            CommonFun.showMpinDialog(PurchaseCouponActivity.this,userid,PurchaseCouponActivity.this);
        }
    }

    @Override
    public void MpinStatus(boolean status) {
        if(status)
        {
          //  System.out.println("FINAL RESULT "+status);
            PurchaseCouponApi();
        }
    }

    private void PurchaseCouponApi()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading .....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty("CouponId",selectedcouponId);
        jsonObject.addProperty("CouponType",CouponType);
        jsonObject.addProperty("CouponQuantity",et_qty.getText().toString().trim());
        jsonObject.addProperty("VLEId",VLEId);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("PurchaseCouponForPancard",VLEId+"|"+CouponType+"|"+et_qty.getText().toString().trim(),userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().PurchaseCouponForPancard(jsonObject);
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
                            String Head1= jsonObject1.getString("Head1");
                            String Head2= jsonObject1.getString("Head2");
                            String Head3= jsonObject1.getString("Head3");
                            ArrayList<DetailedData> array = new ArrayList<DetailedData>();
                            JSONArray out_arr= jsonObject1.getJSONArray("Data");
                            for (int i = 0; i < out_arr.length(); i++) {

                                JSONObject inn_obj=out_arr.getJSONObject(i);

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
//                                    model.setKey(k);
//                                    model.setValue(inn_obj.getString(k));
//                                        array.add(model);

                                }
                            }

                            System.out.println("Array Data "+array);
                            Intent intent = new Intent(PurchaseCouponActivity.this, SuceessScreen.class);
                            intent.putExtra("list_data", new Gson().toJson(array));
                            intent.putExtra("Head1",Head1);
                            intent.putExtra("Head2",Head2);
                            intent.putExtra("Head3",Head3);
                            intent.putExtra("type","success");
                            startActivity(intent);
                           /* AlertDialog.Builder builder1 = new AlertDialog.Builder(MoveToBankActivity.this);
                            builder1.setMessage(jsonObject1.getString("Message"));
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            finish();
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();*/

                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(PurchaseCouponActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PurchaseCouponActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}