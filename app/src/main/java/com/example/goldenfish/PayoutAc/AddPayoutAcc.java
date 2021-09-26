package com.example.goldenfish.PayoutAc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.goldenfish.Constants.Constant;
import com.example.goldenfish.Constants.ConstantsValue;
import com.example.goldenfish.MoveToBank.ModelBank;
import com.example.goldenfish.MoveToBank.MoveToBankActivity;
import com.example.goldenfish.R;
import com.example.goldenfish.Retrofit.RetrofitClient;
import com.example.goldenfish.UserAuth.LoginActivity;
import com.example.goldenfish.Utilities.MyUtils;
import com.example.goldenfish.Utilities.SharedPref;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddPayoutAcc extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    SharedPref sharedPref;
    HashMap<String, ModelBank> bankHashmap= new HashMap<String, ModelBank>();
    String userid,selectedBankId;
    Spinner et_bank,et_accounttype;
    EditText et_ifsc,et_accno,et_acc_name;
    private String accounttype;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_REQUEST = 1888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payout_acc);
        sharedPref = SharedPref.getInstance(AddPayoutAcc.this);
        userid = sharedPref.getStringWithNull(Constant.userId);
        et_bank=findViewById(R.id.et_bank);
        et_accno=findViewById(R.id.et_accno);
        et_ifsc=findViewById(R.id.et_ifsc);
        et_acc_name=findViewById(R.id.et_acc_name);
        et_accounttype=findViewById(R.id.et_accounttype);

        ArrayList<String> acc = new ArrayList<>();
        acc.add("Select Account");
        acc.add("Savings");
        acc.add("Current");

        ArrayAdapter dataAdapter = new ArrayAdapter(AddPayoutAcc.this, R.layout.support_simple_spinner_dropdown_item, acc);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_accounttype.setAdapter(dataAdapter);
        getBank();
        SpinnerValue();
    }
    private void getBank()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Banks.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetAllBankList","",userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().GetAllBankList(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.body() != null){
                    // HideProgress(ctx);
                    progressDialog.dismiss();
                    String fullRes = null;
                    ArrayList<String> bankname= new ArrayList<>();
                    ArrayList<ModelBank> bankid= new ArrayList<>();
                    bankname.add("Select Bank");
                    bankid.add(new ModelBank("-2",""));
                    try {

                        fullRes = response.body().string();
                        JSONObject jsonObject1= new JSONObject(fullRes);
                        String stCode= jsonObject1.getString(Constant.StatusCode);
                        if (stCode.equalsIgnoreCase(ConstantsValue.successful))
                        {
                            JSONArray rootarr=jsonObject1.getJSONArray("Data");
                                System.out.println("DATA "+rootarr);

                            for (int i=0;i<rootarr.length();i++)
                            {
                                JSONObject innerobj= rootarr.getJSONObject(i);

                                String id=innerobj.getString("bankid");
                                String bank_name=innerobj.getString("bankname");
                                String ifsc=innerobj.getString("ifsc");
                                bankname.add(bank_name);
                                bankid.add(new ModelBank(id,ifsc));
                            }

                            ArrayAdapter dataAdapter = new ArrayAdapter(AddPayoutAcc.this, R.layout.support_simple_spinner_dropdown_item, bankname);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            et_bank.setAdapter(dataAdapter);


                            for(int i=0;i<bankname.size();i++)
                            {
                                bankHashmap.put(bankname.get(i),bankid.get(i));
                            }

                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();
                            Toast.makeText(AddPayoutAcc.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddPayoutAcc.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SpinnerValue()
    {
        et_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value= adapterView.getItemAtPosition(i).toString();

                //selectedAccountId= bankHashmap.get(value);
               selectedBankId= bankHashmap.get(value).getBankid();
               et_ifsc.setText(bankHashmap.get(value).getIfsc());
              //  System.out.println("Bank details "+bankHashmap.get(value).getBankid()+" | "+bankHashmap.get(value).getIfsc());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        
        et_accounttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                accounttype= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void uploadPassbook(View view) {
       // chooseImage();

        requestStoragePermission();
    }
    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                           // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                chooseImage();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public void chooseImage() {

      /*  photoPaths = new ArrayList<>();
        FilePickerBuilder.getInstance()
                .setSelectedFiles(photoPaths)
                .setActivityTitle("Please select media")
                .enableVideoPicker(true)
                .enableCameraSupport(true)
                .showGifs(true)
                .showFolderView(true)
                .enableSelectAll(false)
                .enableImagePicker(true)
                .setCameraPlaceholder(R.drawable.ic_camera)
                .withOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .pickPhoto(this, PICK_IMAGE_REQUEST);*/

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPayoutAcc.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
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
                    AddPayoutAcc.this.verifyMpin(et_mpin.getText().toString().trim());
                    addSenderOTPDialog.dismiss();
                    return;
                }
                et_mpin.setError("Required");
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
                            sendPayoutData();
                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();

                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(AddPayoutAcc.this);
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
                            androidx.appcompat.app.AlertDialog alert11 = builder1.create();
                            alert11.show();
                            // Toast.makeText(AddPayoutAcc.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddPayoutAcc.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void PayoutClick(View view) {

        if(et_accno.getText().toString().trim().equalsIgnoreCase(""))
        {
            et_accno.setError("Required");
        }
        else if(et_acc_name.getText().toString().trim().equalsIgnoreCase(""))
        {
            et_acc_name.setError("Required");
        }
        else if(selectedBankId.equalsIgnoreCase("-2"))
        {
            Toast.makeText(AddPayoutAcc.this, "Select Bank", Toast.LENGTH_SHORT).show();
        }
        else if(et_ifsc.getText().toString().trim().equalsIgnoreCase("") || et_ifsc.getText().toString().trim().equalsIgnoreCase("null"))
        {
            et_ifsc.setError("Required");
        }
        else if(accounttype.equalsIgnoreCase("Select Account"))
        {
            Toast.makeText(AddPayoutAcc.this, "Select Account", Toast.LENGTH_SHORT).show();
        }
        else {
            showMpinDialog();
        }
    }

    private void sendPayoutData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sending Request .....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty("AccountNumber",et_accno.getText().toString());
        jsonObject.addProperty("AccountHolderName",et_acc_name.getText().toString());
        jsonObject.addProperty("IFSCCode",et_ifsc.getText().toString());
        jsonObject.addProperty("BankId",selectedBankId);
        jsonObject.addProperty("AccountType",accounttype);
        jsonObject.addProperty("PassbookCopy","");
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("AddPayoutAccount",et_accno.getText().toString()+"|"+et_acc_name.getText().toString()+"|"+et_ifsc.getText().toString()+"|"+selectedBankId+"|"+accounttype,userid));
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().AddPayoutAccount(jsonObject);
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
                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(AddPayoutAcc.this);
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


                            androidx.appcompat.app.AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                        else
                        {
                            // HideProgress(ctx);
                            progressDialog.dismiss();

                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(AddPayoutAcc.this);
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
                            androidx.appcompat.app.AlertDialog alert11 = builder1.create();
                            alert11.show();
                           // Toast.makeText(AddPayoutAcc.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddPayoutAcc.this, "Due to Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}