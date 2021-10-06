package com.example.goldenfish.MoveToBank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenfish.Common.CommonApi;
import com.example.goldenfish.Common.CommonInterface;
import com.example.goldenfish.Constants.Constant;
import com.example.goldenfish.Constants.ConstantsValue;
import com.example.goldenfish.Dashboard.HomeDashboardActivity;
import com.example.goldenfish.PayoutAc.AddPayoutAcc;
import com.example.goldenfish.R;
import com.example.goldenfish.Retrofit.RetrofitClient;
import com.example.goldenfish.UserAuth.LoginActivity;
import com.example.goldenfish.Utilities.MyUtils;
import com.example.goldenfish.Utilities.SharedPref;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MoveToBankActivity extends AppCompatActivity implements CommonInterface {
    String userid;
    private String selectedAccountId,selectedWallet,selectedMode,Surcharge;
    SharedPref sharedPref;
    Spinner et_acc,et_wallet,et_mode;
    HashMap<String,String> acc_hashmap= new HashMap<>();
    EditText et_amount;
    TextView show_bal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_to_bank);
        et_acc=findViewById(R.id.et_acc);
        et_wallet=findViewById(R.id.et_wallet);
        et_mode=findViewById(R.id.et_mode);
        et_amount=findViewById(R.id.et_amount);
        show_bal=findViewById(R.id.show_bal);
        sharedPref = SharedPref.getInstance(MoveToBankActivity.this);
        userid = sharedPref.getStringWithNull(Constant.userId);

        ArrayList<String> wallet = new ArrayList<>();
        wallet.add("Select Wallet");
        wallet.add("Wallet 1");
        wallet.add("Wallet 2");

        ArrayAdapter dataAdapter = new ArrayAdapter(MoveToBankActivity.this, R.layout.support_simple_spinner_dropdown_item, wallet);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_wallet.setAdapter(dataAdapter);


        ArrayList<String> mode = new ArrayList<>();
        mode.add("Select Payment Mode");
        mode.add("NEFT");
        mode.add("IMPS");

        ArrayAdapter dataAdapter1 = new ArrayAdapter(MoveToBankActivity.this, R.layout.support_simple_spinner_dropdown_item, mode);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_mode.setAdapter(dataAdapter1);

        getActiveAccounts();

        SpinnerValue();


        /*//SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
       // SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a"); //here 'a' for AM/PM
        SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

        Date date = null;
        try {
            date = sourceFormat.parse("2021-10-07T00:47:06");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = destFormat.format(date);
System.out.println(formattedDate.replace("am", "AM").replace("pm","PM"));
       // Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();*/
    }

    private void getActiveAccounts()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Account.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetUsersActivePayoutAccount","",userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetUsersActivePayoutAccount(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    ArrayList<String> acc= new ArrayList<>();
                    ArrayList<String> acc_id= new ArrayList<>();
                    acc.add("Select Account");
                    acc_id.add("-2");
                    try {

                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            JSONArray rootarr=jsonObject1.getJSONArray("Data");


                            for (int i=0;i<rootarr.length();i++)
                            {
                               JSONObject innerobj= rootarr.getJSONObject(i);

                               String id=innerobj.getString("Id");
                               String AccountDetails=innerobj.getString("AccountDetails");
                               acc.add(AccountDetails);
                               acc_id.add(id);
                            }

                            ArrayAdapter dataAdapter = new ArrayAdapter(MoveToBankActivity.this, R.layout.support_simple_spinner_dropdown_item, acc);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            et_acc.setAdapter(dataAdapter);


                            for(int i=0;i<acc.size();i++)
                            {
                                acc_hashmap.put(acc.get(i),acc_id.get(i));
                            }

                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(MoveToBankActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MoveToBankActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SpinnerValue()
    {
       et_acc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              String value= adapterView.getItemAtPosition(i).toString();

               selectedAccountId= acc_hashmap.get(value);

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       et_wallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               selectedWallet= adapterView.getItemAtPosition(i).toString();

               if(!selectedWallet.equalsIgnoreCase("Select Wallet"))
               {
                   JsonObject jsonObject= new JsonObject();
                   jsonObject.addProperty("Userid",userid);
                   jsonObject.addProperty("WalletType",selectedWallet);
                   jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetWalletBalanceWalletWise",selectedWallet,userid));
                   CommonApi.getBalanceWalletWise(MoveToBankActivity.this,jsonObject,MoveToBankActivity.this);
               }
               else
               {
                   show_bal.setText("");
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        et_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMode= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void ProceedMoveToBank(View view) {
        if(selectedWallet.equalsIgnoreCase("Select Wallet"))
        {
            Toast.makeText(MoveToBankActivity.this, "Select Wallet", Toast.LENGTH_SHORT).show();
        }
        else if(selectedAccountId.equalsIgnoreCase("-2"))
        {
            Toast.makeText(MoveToBankActivity.this, "Select Account", Toast.LENGTH_SHORT).show();
        }
        else if(selectedMode.equalsIgnoreCase("Select Payment Mode"))
        {
            Toast.makeText(MoveToBankActivity.this, "Select Payment Mode", Toast.LENGTH_SHORT).show();
        }
        else if(et_amount.getText().toString().trim().equalsIgnoreCase(""))
        {
            et_amount.setError("Required");
        }
        else {
            JsonObject jsonObject= new JsonObject();
            jsonObject.addProperty("Userid",userid);
            jsonObject.addProperty("PayoutMode",selectedMode);
            jsonObject.addProperty("Amount",et_amount.getText().toString());
            jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetMoveToBankSurCharge",selectedMode+"|"+et_amount.getText().toString(),userid));
            CommonApi.getSurcharge(this,jsonObject,this);

        }

    }

    @Override
    public void getMoveToBankSurcharge(String CommPer, String CommType, String ChargePer, String ChargeType) {

       // System.out.println("SURCARGE "+ChargeType+ "|"+ ChargePer);
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MoveToBankActivity.this).create();
        LayoutInflater inflater = getLayoutInflater();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View dialogView = inflater.inflate(R.layout.movetobankdialog,null);



        TextView head = (TextView) dialogView.findViewById(R.id.head);
        TextView subhead1 = (TextView) dialogView.findViewById(R.id.subhead1);
        TextView subhead2 = (TextView) dialogView.findViewById(R.id.subhead2);
        Button btn_yes = (Button) dialogView.findViewById(R.id.btn_yes);
        Button btn_no = (Button) dialogView.findViewById(R.id.btn_no);
        head.setText("Are you Sure to Proceed?");
        subhead1.setText("Charges for Transferring Amount Via " + selectedMode+" :");
        if (ChargeType.equalsIgnoreCase("false")) {
            // surchargeTxt.setText("Surcharge = ₹ " + resSurcharge);
            subhead2.setText("Charge = ₹ " + ChargePer);
        } else {
            subhead2.setText("Surcharge = " + ChargePer + "%");
        }
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showMpinDialog();

            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    private void MovetoApi(String WalletType,String AccountDetailsId,String Amount,String PayoutMode)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading .....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty("WalletType",WalletType);
        jsonObject.addProperty("AccountDetailsId",AccountDetailsId);
        jsonObject.addProperty("Amount",Amount);
        jsonObject.addProperty("PayoutMode",PayoutMode);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("MoveToBank",WalletType+"|"+AccountDetailsId+"|"+Amount+"|"+PayoutMode,userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().MoveToBank(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    ArrayList<String> acc= new ArrayList<>();
                    ArrayList<String> acc_id= new ArrayList<>();
                    acc.add("Select Account");
                    acc_id.add("-2");
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
                            Intent intent = new Intent(MoveToBankActivity.this, SuceessScreen.class);
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
                            Toast.makeText(MoveToBankActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MoveToBankActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyMpin(String mpin)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Verifying MPIN.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty("Mpin",mpin);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("ValidateMpinForTransaction",mpin,userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().ValidateMpinForTransaction(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;

                    try {

                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                           // sendPayoutData();
                            MovetoApi(selectedWallet,selectedAccountId,et_amount.getText().toString().trim(),selectedMode);
                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();

                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(MoveToBankActivity.this);
                            builder1.setMessage(jsonObject1.getString("Message"));
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            // finish();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                             //Toast.makeText(MoveToBankActivity.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MoveToBankActivity.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showMpinDialog() {
        View addSenderOTPDialogView = getLayoutInflater().inflate(R.layout.layout_mpin, (ViewGroup) null, false);
        final androidx.appcompat.app.AlertDialog addSenderOTPDialog = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        ((Window) Objects.requireNonNull(addSenderOTPDialog.getWindow())).setBackgroundDrawable(new ColorDrawable(0));
        addSenderOTPDialog.setCancelable(false);
        addSenderOTPDialog.setView(addSenderOTPDialogView);
        addSenderOTPDialog.show();
        final EditText et_mpin = (EditText) addSenderOTPDialogView.findViewById(R.id.et_mpin);
        ((ImageView) addSenderOTPDialogView.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addSenderOTPDialog.dismiss();
            }
        });
        ((Button) addSenderOTPDialogView.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addSenderOTPDialog.dismiss();
            }
        });
        ((Button) addSenderOTPDialogView.findViewById(R.id.btn_submit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_mpin.getText())) {
                    MoveToBankActivity.this.verifyMpin(et_mpin.getText().toString().trim());
                    addSenderOTPDialog.dismiss();
                    return;
                }
                et_mpin.setError("Required");
            }
        });
    }

    @Override
    public void getWalletBalance(String bal) {
       // System.out.println("BAL "+bal);
        show_bal.setText(bal);
    }
}