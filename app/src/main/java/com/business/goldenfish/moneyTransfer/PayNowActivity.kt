package com.business.goldenfish.moneyTransfer

import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import com.business.goldenfish.BaseActivity
import com.business.goldenfish.Common.CommonFun
import com.business.goldenfish.Common.CommonInterface
import com.business.goldenfish.Constants.Constant
import com.business.goldenfish.Constants.ConstantsValue
import com.business.goldenfish.MoveToBank.DetailedData
import com.business.goldenfish.MoveToBank.SuceessScreen
import com.business.goldenfish.Retrofit.RetrofitClient
import com.business.goldenfish.Utilities.*
import com.business.goldenfish.databinding.ActivityPayNowBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_pay_now.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PayNowActivity : BaseActivity(), CommonInterface, OnDataReceiverListener, GpsInterface {

    lateinit var binding : ActivityPayNowBinding
    var sharedPref: SharedPref? = null
    var userid:String?=null
    var OutletId:String?=null
    var MainBal:String?=null
    var locationManager: LocationManager? = null
    var latitude = ""
    var longitude = ""
    var bc_Address = ""
    //var gps: GPSTracker?=null
    var isGPSEnabled:Boolean=false
    var geoLocation: GeoLocation? = null
    private var gpsListener: GpsListener? = null
    private var isGpsOn = false
    private var onecall = false
    private var faslecall = false
    var BankName:String?=null
    var BenificiaryIFSC:String?=null
    var BeniAccountNo:String?=null
    var BenificiaryId:String?=null
    var RemitterMobile:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayNowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref.getInstance(this)
        userid = sharedPref?.getStringWithNull(Constant.userId)

        OutletId = sharedPref?.getStringWithNull(Constant.OutletId)
        MainBal = sharedPref?.getStringWithNull(Constant.AepsBal)

        binding.avBalanceTTDmt.setText("₹ "+MainBal)
        if(intent.extras!=null)
        {
           BankName=  intent.getStringExtra(Constant.BankName)
           BenificiaryIFSC=  intent.getStringExtra(Constant.BenificiaryIFSC)
           BeniAccountNo=  intent.getStringExtra(Constant.BeniAccountNo)
           BenificiaryId=  intent.getStringExtra(Constant.BenificiaryId)
           RemitterMobile=  intent.getStringExtra(Constant.RemitterMobile)
        }


        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        isGPSEnabled = locationManager!!
            .isProviderEnabled(LocationManager.GPS_PROVIDER)
        //  gps = GPSTracker(this)
        geoLocation = GeoLocation(this, this)
        val mfilter = IntentFilter(
            "android.location.PROVIDERS_CHANGED")
        gpsListener = GpsListener(this, this)
        registerReceiver(gpsListener, mfilter)

binding.backPressDmt.setOnClickListener {
    finish()
}
    }

    override fun onResume() {
        super.onResume()
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        else
        {
            geoLocation!!.startLocationButtonClick()

        }
    }


    override fun onDataReceived(myData: String?, latitude: Double?, longitude: Double?, address: String?) {
        this.latitude=latitude.toString()
        this.longitude=longitude.toString()

    }

    override fun onGpsStatusChanged(gpsStatus: Boolean) {
        isGpsOn = gpsStatus
        if (isGpsOn) {
            faslecall = false
            if (onecall == false) {
                onecall = true
                //  Toast.makeText(this, "GPS on", Toast.LENGTH_SHORT).show();
            }
        } else {
            onecall = false
            if (faslecall == false) {
                faslecall = true
                //   Toast.makeText(this, "GPS OFF", Toast.LENGTH_SHORT).show();
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(gpsListener)
    }
    override fun onPause() {
        super.onPause()
        geoLocation!!.stopLocationUpdates()
    }

    fun proceedDmt(view: View) {

        if(et_amount_dmt.text.toString().equals("") || et_amount_dmt.text.toString().equals("0"))
        {
            et_amount_dmt.setError("Required")
        }
        else if(et_amount_dmt.text.toString().toInt() < 100)
        {
            Toast.makeText(this,"Amount should be greater than 100 Rs",Toast.LENGTH_SHORT).show()
        }
        else
        {
            CommonFun.showMpinDialog(this, userid, this)
        }
    }
    override fun MpinStatus(status: Boolean) {
        if (status) {
            //  System.out.println("FINAL RESULT "+status);
            PayDmtApi()
        }
    }


    private fun PayDmtApi() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Transfering Amount .....")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid",userid)
        jsonObject.addProperty("BenificiaryId",BenificiaryId)
        jsonObject.addProperty("BeniAccountNo", BeniAccountNo)
        jsonObject.addProperty("BenificiaryIFSC",BenificiaryIFSC)
        jsonObject.addProperty("BankName",BankName)
        jsonObject.addProperty("RemitterMobile",RemitterMobile)
        jsonObject.addProperty("BankId","1")
        jsonObject.addProperty("WalletType","Wallet 2")
        jsonObject.addProperty("Amount",binding.etAmountDmt.text.toString())
        jsonObject.addProperty("PayMode","IMPS")
        jsonObject.addProperty("OutletId",OutletId)
        jsonObject.addProperty("IpAddress",UtilsIP.getIPAddress(true))
        jsonObject.addProperty("Latitude",this.latitude)
        jsonObject.addProperty("Longitude",this.longitude)
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("MakeDMTTransaction", "Wallet 2" + "|" + RemitterMobile + "|" + BeniAccountNo + "|" + BenificiaryIFSC + "|" + binding.etAmountDmt.text.toString() + "|" + "IMPS",userid))
        val call = RetrofitClient.getInstance().api.MakeDMTTransaction(jsonObject)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss()
                    var fullRes: String? = null
                    try {
                        fullRes = response.body()!!.string()
                      //  System.out.println("FINAL RESULT $fullRes")
                        val jsonObject1 = JSONObject(fullRes)
                        val stCode = jsonObject1.getString(Constant.StatusCode)
                        if (stCode.equals(ConstantsValue.successful, ignoreCase = true)) {
                            val Head1 = jsonObject1.getString("Head1")
                            val Head2 = jsonObject1.getString("Head2")
                            val Head3 = jsonObject1.getString("Head3")
                            val array = ArrayList<DetailedData>()
                            val out_arr = jsonObject1.getJSONArray("Data")
                            for (i in 0 until out_arr.length()) {
                                val inn_obj = out_arr.getJSONObject(i)
                                val key: Iterator<*> = inn_obj.keys()
                                while (key.hasNext()) {
                                    val k = key.next().toString()
                                   /* println("Key : " + k + ", value : "
                                            + inn_obj.getString(k))*/
                                    if (k.equals("ReqDate", ignoreCase = true)) {
                                        val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                        // SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a"); //here 'a' for AM/PM
                                        val destFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a")
                                        var date: Date? = null
                                        try {
                                            date = sourceFormat.parse(inn_obj.getString(k))
                                        } catch (e: ParseException) {
                                            e.printStackTrace()
                                        }
                                        val formattedDate = destFormat.format(date)
                                        array.add(DetailedData(k, formattedDate.replace("am", "AM").replace("pm", "PM")))
                                    } else {
                                        array.add(DetailedData(k, inn_obj.getString(k)))
                                    }
                                    //                                    model.setKey(k);
//                                    model.setValue(inn_obj.getString(k));
//                                        array.add(model);
                                }
                            }
                          //  println("Array Data $array")
                            val intent = Intent(this@PayNowActivity, SuceessScreen::class.java)
                            intent.putExtra("list_data", Gson().toJson(array))
                            intent.putExtra("Head1", Head1)
                            intent.putExtra("Head2", Head2)
                            intent.putExtra("Head3", Head3)
                            intent.putExtra("type", "success")
                            intent.putExtra("service", "")
                            startActivity(intent)

                        } else {
                            // HideProgress(ctx);
                            progressDialog.dismiss()
                            Toast.makeText(this@PayNowActivity, "" + jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: java.lang.Exception) {
                    }
                } else {
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@PayNowActivity, "Due to Internet Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}