package com.business.goldenfish.recharges;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.business.goldenfish.Common.CommonFun;
import com.business.goldenfish.Common.CommonInterface;
import com.business.goldenfish.Constants.Constant;
import com.business.goldenfish.Constants.ConstantsValue;
import com.business.goldenfish.MoveToBank.DetailedData;
import com.business.goldenfish.MoveToBank.MoveToBankActivity;
import com.business.goldenfish.MoveToBank.SuceessScreen;
import com.business.goldenfish.PanCard.PurchaseCouponActivity;
import com.business.goldenfish.PayoutAc.AddPayoutAcc;
import com.business.goldenfish.R;
import com.business.goldenfish.Retrofit.RetrofitClient;
import com.business.goldenfish.Utilities.MyUtils;
import com.business.goldenfish.Utilities.SharedPref;
import com.business.goldenfish.Utilities.UtilsIP;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.business.goldenfish.Retrofit.RetrofitClient.BASE_URL;
import static com.business.goldenfish.Retrofit.RetrofitClient.IMAGE_URL;

public class SelectOperatorActivity extends AppCompatActivity implements CommonInterface {
GridView opertorgrid;
TextView activity_title;
ImageView back_button,logoopr;
LinearLayout amount_layout,operator_layout;
TextView number,operaotre;
    ArrayList<Model_Class_For_Prepaid_Operator> listItems = new ArrayList<Model_Class_For_Prepaid_Operator>();

    String input_num="",userid="",OpId="";
    EditText et_amt;
    SharedPref sharedPref;
    String service_name, service_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_operator);
        opertorgrid=findViewById(R.id.opertorgrid);
        activity_title=findViewById(R.id.activity_title);
        back_button=findViewById(R.id.back_button);
        amount_layout=findViewById(R.id.amount_layout);
        operaotre=findViewById(R.id.operaotre);
        et_amt=findViewById(R.id.et_amt);
        logoopr=findViewById(R.id.logojio);
        number=findViewById(R.id.number);
        operator_layout=findViewById(R.id.operator_layout);
        sharedPref = SharedPref.getInstance(SelectOperatorActivity.this);
        userid = sharedPref.getStringWithNull(Constant.userId);

        if(getIntent().getExtras()!=null)
        {
            input_num=  getIntent().getStringExtra("number");
            service_name=  getIntent().getStringExtra("service_name");
            service_id=  getIntent().getStringExtra("service_id");
            activity_title.setText(service_name);
        }
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        opertorgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                        SharedPreferences sharedPreferences = PreferenceManager
//                                .getDefaultSharedPreferences(Mobileprepaid.this);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
              //  sharedPref.putInt(ContentsValue.key, position);
                String operator = listItems.get(position).getOperatorName();
                String image = listItems.get(position).getImage();
                 OpId = listItems.get(position).getId();
                operator_layout.setVisibility(View.GONE);
                amount_layout.setVisibility(View.VISIBLE);

                number.setText(input_num);
                operaotre.setText(operator);
                Picasso.with(SelectOperatorActivity.this)
                        .load(image)
                        .into(logoopr);
            }
        });

        getOperatorList();
    }

     private void getOperatorList()
    {
      ProgressDialog  pDialog = new ProgressDialog(SelectOperatorActivity.this, R.style.MyTheme);
        pDialog.setMessage("Loading....");
        pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
        pDialog.setCancelable(false);
        pDialog.show();
        String url = BASE_URL+"GetOperatorServicewise";
        RequestQueue requestQueue = Volley.newRequestQueue(SelectOperatorActivity.this);
        Map<String, String> params = new HashMap();
        params.put("Userid", userid);
        params.put("ServiceId", service_id);
        params.put("checksum", MyUtils.encryption("GetOperatorServicewise",service_id,userid));

        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //   Toast.makeText(step1signup.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    String r_code = obj.getString("Statuscode");
                    if (r_code.equals("TXN")) {

                        JSONArray jsonArray = obj.getJSONArray("Data");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);


                            String id = jsonObject2.getString("Id");

                            String Operatorname = jsonObject2.getString("OperatorName");
                            String Img = jsonObject2.getString("Image");

                            String imageurl = IMAGE_URL +Img;

                            Model_Class_For_Prepaid_Operator memberList = new Model_Class_For_Prepaid_Operator();
                            memberList.setOperatorName(Operatorname);
                            memberList.setImage(imageurl);
                            memberList.setId(id);
                            listItems.add(memberList);
                           // oplogo.add(imageurl);
                         //   OperatorList1.add(id);



                        }
                        CustomListAdapter memberAdapter = new CustomListAdapter(SelectOperatorActivity.this, R.layout.operator_view_layout, listItems);
                        opertorgrid.setAdapter(memberAdapter);
                        pDialog.dismiss();
                       // getOperatorDetails();
                    } else {
                        // Toast.makeText(Mobileprepaid.this, "" + obj.getString("Message"), Toast.LENGTH_SHORT).show();

                        pDialog.dismiss();

                    }

                } catch (Exception e) {



                    pDialog.dismiss();

                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(Mobileprepaid.this, "" + error, Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }) {

        };
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void ProceedFinal(View view) {
        if(et_amt.getText().toString().trim().equalsIgnoreCase("") || et_amt.getText().toString().trim().equalsIgnoreCase("0"))
        {
            et_amt.setError("Enter Amount");
        }
        else
        {
            CommonFun.showMpinDialog(SelectOperatorActivity.this,userid,SelectOperatorActivity.this);

        }
    }
    @Override
    public void MpinStatus(boolean status) {
        if(status)
        {
            SendRechargeRequest();
        }
    }
    @Override
    public void onBackPressed() {
        if (amount_layout.getVisibility() == View.VISIBLE) {
            amount_layout.setVisibility(View.GONE);
            operator_layout.setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
        }
    }

    public void changeOperator(View view) {
        amount_layout.setVisibility(View.GONE);
        operator_layout.setVisibility(View.VISIBLE);
    }


    private void SendRechargeRequest()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing Request .....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty("Amount",et_amt.getText().toString().trim());
        jsonObject.addProperty("ServiceId",service_id);
        jsonObject.addProperty("OpId",OpId);
        jsonObject.addProperty("Mobile",input_num);
        jsonObject.addProperty("IpAddress", UtilsIP.getIPAddress(true));
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("MakeRechargeRequest",input_num+"|"+et_amt.getText().toString().trim()+"|"+OpId,userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().MakeRechargeRequest(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                  /*//  ArrayList<String> acc= new ArrayList<>();
                  //  ArrayList<String> acc_id= new ArrayList<>();
                  //  acc.add("Select Account");
                  //  acc_id.add("-2");*/
                    try {

                        fullRes = response.body().string();
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
                            Intent intent = new Intent(SelectOperatorActivity.this, SuceessScreen.class);
                            intent.putExtra("list_data", new Gson().toJson(array));
                            intent.putExtra("Head1",Head1);
                            intent.putExtra("Head2",Head2);
                            intent.putExtra("Head3",Head3);
                            intent.putExtra("type","success");
                            intent.putExtra("service",service_name);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);

                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(SelectOperatorActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(SelectOperatorActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SelectOperatorActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}