package com.business.goldenfish.AddFund;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.business.goldenfish.AddFund.ModelAddFund.ModelAccountList;
import com.business.goldenfish.AddFund.ModelAddFund.ModelUserAccountList;
import com.business.goldenfish.Common.CommonApi;
import com.business.goldenfish.Common.CommonFun;
import com.business.goldenfish.Common.CommonInterface;
import com.business.goldenfish.Constants.Constant;
import com.business.goldenfish.MoveToBank.MoveToBankActivity;
import com.business.goldenfish.PayoutAc.AddPayoutAcc;
import com.business.goldenfish.R;
import com.business.goldenfish.Utilities.MyUtils;
import com.business.goldenfish.Utilities.SharedPref;
import com.business.goldenfish.databinding.ActivityAddFundBinding;
import com.business.goldenfish.recharges.SelectOperatorActivity;
import com.business.goldenfish.sidebar.allReports.AllReportsActivity;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public class AddFundActivity extends AppCompatActivity implements CommonInterface {
    final Calendar myCalendar = Calendar.getInstance();
    ActivityAddFundBinding activityAddFundBinding;
    AddFundVM model;
    HashMap<String,ModelAccountList> acc_hashmap= new HashMap<>();
    private String selectedAccountId="",selectedMode="";
    SharedPref sharedPref;
    String userid,userType,Username;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private String PassbookPath;
    ArrayList<ModelAccountList> bankName= new ArrayList<>();

    String alaccName="",alaccNo="",albankName="",alifscCode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fund);
        activityAddFundBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_fund);
        Toolbar toolbar = findViewById(R.id.toolbar_add_fund);
        ImageView back_button=findViewById(R.id.back_button_ledger);
        // toolbar.setTitle("");
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.activity_title_ledger);

        toolbarTitle.setText("Add Fund");
         model = new ViewModelProvider(this).get(AddFundVM.class);
       //  model =  ViewModelProviders.of(this).get(AddFundVM.class);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        activityAddFundBinding.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(AddFundActivity.this).create();
                LayoutInflater inflater = getLayoutInflater();
               // alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                View convertView = inflater.inflate(R.layout.layout_viewdetails,null);

                TextView accno = (TextView)convertView.findViewById(R.id.accno);
                TextView accname = (TextView)convertView.findViewById(R.id.accname);
                TextView albankName1 = (TextView)convertView.findViewById(R.id.albankName);
                TextView ifsc_code = (TextView)convertView.findViewById(R.id.ifsc_code);
                ImageView img_close = (ImageView)convertView.findViewById(R.id.img_close);


                accno.setText(alaccNo);
                accname.setText(alaccName);
                albankName1.setText(albankName);
                ifsc_code.setText(alifscCode);
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });


                alertDialog.setView(convertView);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });
     //   System.out.println("MY DATA "+model.getData1());
       // System.out.println("MY DATA "+MyUtils.encryption("MakeAddFundRequest", "ZBP"+"1000"+"Test", "13598"));

        SpinnerValue();


        sharedPref = SharedPref.getInstance(AddFundActivity.this);
        userid = sharedPref.getStringWithNull(Constant.userId);
        userType = sharedPref.getStringWithNull(Constant.Usertype);
        Username = sharedPref.getStringWithNull(Constant.Username);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Userid",userid);
        jsonObject.addProperty("UserType",userType);
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetAddedFundBankDetails", userType, userid));

       /* model.getfinalAccountList(jsonObject);

        model.getAccData().observe(this,u->{
            System.out.println("HEllo "+u);
        });*/

       /* model.getfinalAccountList(jsonObject);
        model.getAccData().observe(this, users -> {
              System.out.println("MY DATA "+users.getData());
                });*/

        model.getfinalAccountList(jsonObject).observe(this, users -> {
         //   System.out.println("MY DATA "+users.getData());
            // update UI
            if(users.getStatuscode().equalsIgnoreCase("TXN"))
            {
                bankName.clear();
                ArrayList<String> AccountName= new ArrayList<>();
                AccountName.add("Select Account");
                bankName.add(new ModelAccountList("-2","","",""));

                for (int i=0;i<users.getData().size();i++)
                {
                    AccountName.add(users.getData().get(i).getAccountName());
                    bankName.add(new ModelAccountList(users.getData().get(i).getBankName(),users.getData().get(i).getAccountName(),
                            users.getData().get(i).getAccountNo(),users.getData().get(i).getIFSCode()));
                }
                ArrayAdapter dataAdapter = new ArrayAdapter(AddFundActivity.this, R.layout.support_simple_spinner_dropdown_item, AccountName);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                activityAddFundBinding.etAcc.setAdapter(dataAdapter);

                for(int i=0;i<AccountName.size();i++)
                {
                    acc_hashmap.put(AccountName.get(i),bankName.get(i));
                }

              List<ModelUserAccountList> userBankDetails=  users.getUserBankDetails();

              activityAddFundBinding.etBranch.setText(userBankDetails.get(0).getBankname());
              activityAddFundBinding.etAccno.setText(userBankDetails.get(0).getAccountNumber());
              activityAddFundBinding.etIfsc.setText(userBankDetails.get(0).getIFSCCode());
              activityAddFundBinding.etAccName.setText(userBankDetails.get(0).getAccountHolderName());

            }
            else
            {
                Toast.makeText(this, ""+users.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        ArrayList<String> mode = new ArrayList<>();
        mode.add("Select Payment Mode");
        mode.add("NEFT");
        mode.add("IMPS");
        mode.add("RTGS");

        ArrayAdapter dataAdapter1 = new ArrayAdapter(AddFundActivity.this, R.layout.support_simple_spinner_dropdown_item, mode);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityAddFundBinding.etMode.setAdapter(dataAdapter1);

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
                String date = "" + fd + "-" + fm + "-" + year;



                activityAddFundBinding.etDate.setText(date.trim());
            }
        };
        activityAddFundBinding.etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddFundActivity.this, R.style.AlertDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


    }

    private void SpinnerValue()
    {
        activityAddFundBinding.etAcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value= adapterView.getItemAtPosition(i).toString();

                selectedAccountId= acc_hashmap.get(value).getBankName();

                System.out.println("ACCOUNT ID "+selectedAccountId);
                if(!selectedAccountId.equalsIgnoreCase("-2"))
                {
                    activityAddFundBinding.viewDetails.setVisibility(View.VISIBLE);

                    alaccNo=acc_hashmap.get(value).getAccountNo();
                    albankName=acc_hashmap.get(value).getBankName();
                    alifscCode=acc_hashmap.get(value).getIFSCode();
                    alaccName=acc_hashmap.get(value).getAccountName();

//                    activityAddFundBinding.etIfsc.setText(acc_hashmap.get(value).getIFSCode());
//                    activityAddFundBinding.etBranch.setText(acc_hashmap.get(value).getBankName());
//                    activityAddFundBinding.etAccno.setText(acc_hashmap.get(value).getAccountNo());
//                    activityAddFundBinding.etAccName.setText(acc_hashmap.get(value).getAccountName());
                }
                else
                {

                    activityAddFundBinding.viewDetails.setVisibility(View.GONE);
//                    activityAddFundBinding.etIfsc.setText("");
//                    activityAddFundBinding.etBranch.setText("");
//                    activityAddFundBinding.etAccno.setText("");
//                    activityAddFundBinding.etAccName.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        et_wallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedWallet= adapterView.getItemAtPosition(i).toString();
//
//                if(!selectedWallet.equalsIgnoreCase("Select Wallet"))
//                {
//                    JsonObject jsonObject= new JsonObject();
//                    jsonObject.addProperty("Userid",userid);
//                    jsonObject.addProperty("WalletType",selectedWallet);
//                    jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetWalletBalanceWalletWise",selectedWallet,userid));
//                    CommonApi.getBalanceWalletWise(MoveToBankActivity.this,jsonObject,MoveToBankActivity.this);
//                }
//                else
//                {
//                    show_bal.setText("");
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
        activityAddFundBinding.etMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMode= adapterView.getItemAtPosition(i).toString();
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

        photoPaths = new ArrayList<>();
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
                .pickPhoto(this, PICK_IMAGE_REQUEST);

       /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);*/
        //  someActivityResultLauncher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                String path = photoPaths.get(0);
                File file = new File(path);

                CommonApi.serverUpload(file, AddFundActivity.this, new CommonApi.OnResponse() {

                    @Override
                    public void onSuccess(String result) {
                        //   Toast.makeText(Completesignup4.this, "hello", Toast.LENGTH_SHORT).show();

                        PassbookPath=result;
                        activityAddFundBinding.img.setBackgroundResource(R.drawable.checked);
                        // Toast.makeText(AddPayoutAcc.this, ""+result, Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                    }
                });
                //Toast.makeText(AddPayoutAcc.this, "hello baby", Toast.LENGTH_SHORT).show();
                // uploadFileToServer(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (CAMERA_REQUEST == requestCode && resultCode == RESULT_OK) {
            try {
                photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                String path = photoPaths.get(0);
                File file = new File(path);
                //  uploadFileToServer(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFundActivity.this);
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

    public void ProceedAddFund(View view) {
        if(selectedAccountId.equalsIgnoreCase("-2"))
        {
            Toast.makeText(this, "Select Account", Toast.LENGTH_SHORT).show();
        }
        else if(selectedMode.equalsIgnoreCase("Select Payment Mode"))
        {
            Toast.makeText(this, "Select Mode", Toast.LENGTH_SHORT).show();
        }
        else if(activityAddFundBinding.etAmount.getText().toString().trim().equalsIgnoreCase("") ||
                activityAddFundBinding.etAmount.getText().toString().trim().equalsIgnoreCase("0"))
        {
            activityAddFundBinding.etAmount.setError("Required");
        }
        else if(activityAddFundBinding.etDate.getText().toString().trim().equalsIgnoreCase(""))
        {
            activityAddFundBinding.etDate.setError("Required");
        }
        else if(activityAddFundBinding.etRefno.getText().toString().trim().equalsIgnoreCase(""))
        {
            activityAddFundBinding.etRefno.setError("Required");
        }
        else if(PassbookPath==null)
        {
            activityAddFundBinding.img.setBackgroundResource(R.drawable.ic_hotel_failed);
            Toast.makeText(this, "Please Upload Copy of Cash deposit Slip.", Toast.LENGTH_SHORT).show();
        }
        else if(activityAddFundBinding.etAccno.getText().toString().trim().equalsIgnoreCase(""))
        {
            activityAddFundBinding.etAccno.setError("Required");
        }
        else if(activityAddFundBinding.etAccName.getText().toString().trim().equalsIgnoreCase(""))
        {
            activityAddFundBinding.etAccName.setError("Required");
        }
        else if(activityAddFundBinding.etBranch.getText().toString().trim().equalsIgnoreCase(""))
        {
            activityAddFundBinding.etBranch.setError("Required");
        }
        else if(activityAddFundBinding.etIfsc.getText().toString().trim().equalsIgnoreCase(""))
        {
            activityAddFundBinding.etIfsc.setError("Required");
        }
        else
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Sending Request .....");
            progressDialog.setCancelable(false);
            progressDialog.show();
           // {"Userid":"13598","checksum":"XXX","UserType":"Retailer","Amount":"1500","RequestDate":"25-03-2022","PayType":"XXXXX","RefNo":"(Transaction Number)","BankName":"XXXX","TopupFrom":"Golden Fish Digital Seva(selected AccountName)","OriginName":"RT0013596(Login User Name)","ProofDocs":"/Docs/XXXXXXX.png"}
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Userid",userid);
            jsonObject.addProperty("UserType",userType);
            jsonObject.addProperty("Amount",activityAddFundBinding.etAmount.getText().toString().trim());
            jsonObject.addProperty("RequestDate",activityAddFundBinding.etDate.getText().toString().trim());
            jsonObject.addProperty("PayType",selectedMode);
            jsonObject.addProperty("RefNo",activityAddFundBinding.etRefno.getText().toString().trim());
            jsonObject.addProperty("BankName",activityAddFundBinding.etBranch.getText().toString().trim());
            jsonObject.addProperty("TopupFrom",activityAddFundBinding.etAccName.getText().toString().trim());
            jsonObject.addProperty("OriginName",Username);
            jsonObject.addProperty("ProofDocs",PassbookPath);
            jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("MakeAddFundRequest",userType+"|"+activityAddFundBinding.etAmount.getText().toString().trim(), userid));

            model.sendAddFundRequest(jsonObject).observe(this, users -> {

                //Toast.makeText(this, ""+users.getStatuscode(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, ""+users.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
                progressDialog.dismiss();
            });
         //   CommonFun.showMpinDialog(AddFundActivity.this,userid,AddFundActivity.this);
        }
    }

    /*@Override
    public void MpinStatus(boolean status) {
        if(status)
        {
            //SendRechargeRequest();
        }
    }*/
}