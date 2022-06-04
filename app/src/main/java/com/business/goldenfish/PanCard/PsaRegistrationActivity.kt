package com.business.goldenfish.PanCard

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.business.goldenfish.Common.CommonFun
import com.business.goldenfish.Common.CommonInterface
import com.business.goldenfish.Constants.Constant
import com.business.goldenfish.Constants.ConstantsValue
import com.business.goldenfish.MoveToBank.DetailedData
import com.business.goldenfish.MoveToBank.SuceessScreen
import com.business.goldenfish.R
import com.business.goldenfish.Retrofit.RetrofitClient
import com.business.goldenfish.Utilities.MyUtils
import com.business.goldenfish.Utilities.SharedPref
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_psa_registration.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PsaRegistrationActivity : AppCompatActivity(),CommonInterface {
    var states = HashMap<String, String>()
    var cities = HashMap<String, String>()
    var selectedStateId="";
    var selectedcityId="";
    var sharedPref: SharedPref? = null
    var userid: String? = null
    var userName: String? = null
    var VLEId:kotlin.String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psa_registration)
        sharedPref = SharedPref.getInstance(this@PsaRegistrationActivity)
        userid = sharedPref!!.getStringWithNull(Constant.userId)
        userName = sharedPref!!.getStringWithNull(Constant.Username)
       var ownerName = sharedPref!!.getStringWithNull(Constant.OwnerName)
        var FirmName = sharedPref!!.getStringWithNull(Constant.FirmName)
        var state = sharedPref!!.getStringWithNull(Constant.state)
        var city = sharedPref!!.getStringWithNull(Constant.city)
        var Address = sharedPref!!.getStringWithNull(Constant.Address)
        var MobileNo1 = sharedPref!!.getStringWithNull(Constant.MobileNo1)
        var EmailId = sharedPref!!.getStringWithNull(Constant.EmailId)
        var PANCard = sharedPref!!.getStringWithNull(Constant.PANCard)
        var Area = sharedPref!!.getStringWithNull(Constant.Area)
        et_ownerName_pan.setText(ownerName)
        et_vleName_pan.setText(FirmName)
        et_state_pan.setText(state)
        et_city_pan.setText(city)
        et_address_pan.setText(Address)
        et_mobile_pan.setText(MobileNo1)
        et_email_pan.setText(EmailId)
        et_panno_pan.setText(PANCard)
        et_picode_pan.setText(Area)

        // GetVLEIdForPanCard();
        if (intent.extras != null) {
            VLEId = intent.getStringExtra("vle_id")
            et_vleid.setText("VLE id -$VLEId")
        }
        //GetVLEIdForPanCard()
        //CommonApi.getState(this@PsaRegistrationActivity, this@PsaRegistrationActivity)
       // spinnervalue()
    }

    fun ProceedToPsaReg(view: View)
    {
            if(et_vleid.text.toString().trim().equals(""))
            {
                et_vleid.setError("Required")
            }
        else if(et_ownerName_pan.text.toString().trim().equals(""))
            {
            et_ownerName_pan.setError("Required")
        }
        else if(et_vleName_pan.text.toString().trim().equals(""))
            {
            et_vleName_pan.setError("Required")
        }
        else if(selectedStateId.equals("-2"))
            {
            Toast.makeText(this@PsaRegistrationActivity, "Select State", Toast.LENGTH_SHORT).show()
        }
            else if(selectedcityId.equals("-2"))
            {
                Toast.makeText(this@PsaRegistrationActivity, "Select City", Toast.LENGTH_SHORT).show()
            }
            else if(et_address_pan.text.toString().trim().equals(""))
            {
                et_address_pan.setError("Required")
            }
            else if(et_picode_pan.text.toString().trim().equals("") || et_picode_pan.text.toString().length !=6)
            {
                et_picode_pan.setError("Required")
            }
            else if(et_mobile_pan.text.toString().trim().equals("") || et_mobile_pan.text.toString().length !=10)
            {
                et_mobile_pan.setError("Required")
            }
            else if(et_email_pan.text.toString().trim().equals(""))
            {
                et_email_pan.setError("Required")
            }
            else if(et_panno_pan.text.toString().trim().equals("") || et_panno_pan.text.toString().length !=10)
            {
                et_panno_pan.setError("Required")
            }
        else if(!check_cond_pan.isChecked)
            {
                Toast.makeText(this@PsaRegistrationActivity, "Please check Condition", Toast.LENGTH_SHORT).show()
        }
        else
            {
                CommonFun.showMpinDialog(this@PsaRegistrationActivity, userid, this@PsaRegistrationActivity)
            }
    }

   /* override fun getStates(name: ArrayList<String>?, id: ArrayList<String>?) {

        val dataAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, name!!)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        et_state_pan.setAdapter(dataAdapter)
        bkl(name, id!!)
    }

    override fun getCity(name: ArrayList<String>?, id: ArrayList<String>?) {
        val dataAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, name!!)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        et_city_pan.setAdapter(dataAdapter)
        bklcity(name, id!!)
    }*/

    fun bkl(al1: ArrayList<String>, al2: ArrayList<String>) {
        for (i in al1.indices) states.put(al1[i]!!, al2[i]!!)
    }
    fun bklcity(al1: ArrayList<String>, al2: ArrayList<String>) {
        for (i in al1.indices) cities.put(al1[i]!!, al2[i]!!)
    }

    /*fun spinnervalue() {
        et_state_pan.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                if (!isSpinnerTouched)
//                    return;
                stateValue = parent.getItemAtPosition(position).toString().trim { it <= ' ' }
                val selectedItem = parent.getItemAtPosition(position).toString()
                selectedStateId = states[selectedItem]!! //this is your selected item
                //   Toast.makeText(this@GstDetails, ""+idz, Toast.LENGTH_SHORT).show();
                // mc1()
                if (!selectedStateId.equals("-2")) {
                    et_city_pan.visibility = View.VISIBLE
                    CommonApi.getCity(this@PsaRegistrationActivity, this@PsaRegistrationActivity, selectedStateId)
                } else {
                    et_city_pan.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        et_city_pan.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                if (!isSpinnerTouched)
//                    return;
                cityValue = parent.getItemAtPosition(position).toString().trim { it <= ' ' } //this is your selected item
                val selectedItem = parent.getItemAtPosition(position).toString()
                selectedcityId = cities[selectedItem]!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }*/

    private fun GetVLEIdForPanCard() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Fetching....")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid", userid)
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetVLEIdForPanCard", "", userid))
        val call = RetrofitClient.getInstance().api.GetVLEIdForPanCard(jsonObject)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss()
                    var st: String? = null
                    try {
                        st = response.body()!!.string()
                        val jsonObject1 = JSONObject(st)
                        val stCode = jsonObject1.getString(Constant.StatusCode)
                        if (stCode.equals(ConstantsValue.successful, ignoreCase = true)) {
                            val jsonArray = jsonObject1.getJSONArray("Data")
                            VLEId = jsonArray.getJSONObject(0).getString("VLEId")
                            et_vleid.setText("VLE id -$VLEId")
                        } else {
                            progressDialog.dismiss()
                            Toast.makeText(this@PsaRegistrationActivity, "" + jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        progressDialog.dismiss()
                        e.printStackTrace()
                    }
                } else {
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@PsaRegistrationActivity, "Due to Internet Error" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun MpinStatus(status: Boolean) {
        if (status) {
            //  System.out.println("FINAL RESULT "+status);
            PsaRegApi()
        }
    }
    private fun PsaRegApi() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading .....")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid", userid)
        jsonObject.addProperty("UserName", userName)
        jsonObject.addProperty("VLEId", VLEId)
        jsonObject.addProperty("VLEName", et_vleName_pan.text.toString().trim())
        jsonObject.addProperty("OwnerName", et_ownerName_pan.text.toString().trim())
        jsonObject.addProperty("Mobile", et_mobile_pan.text.toString().trim())
        jsonObject.addProperty("EmailId", et_email_pan.text.toString().trim())
        jsonObject.addProperty("PancardNo", et_panno_pan.text.toString().trim())
        jsonObject.addProperty("Address", et_address_pan.text.toString().trim())
        jsonObject.addProperty("Pincode", et_picode_pan.text.toString().trim())
        jsonObject.addProperty("City", et_city_pan.text.toString().trim())
        jsonObject.addProperty("State", et_state_pan.text.toString().trim())
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("CreatePSAForUsers", VLEId + "|" + userName + "|" + et_vleName_pan.getText().toString().trim() + "|" + et_mobile_pan.text.toString().trim(), userid))
        val call = RetrofitClient.getInstance().api.CreatePSAForUsers(jsonObject)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss()
                    var fullRes: String? = null
                    try {
                        fullRes = response.body()!!.string()
                     //   System.out.println("FINAL RESULT $fullRes")
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
//                                    println("Key : " + k + ", value : "
//                                            + inn_obj.getString(k))
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
                           // println("Array Data $array")
                            val intent = Intent(this@PsaRegistrationActivity, SuceessScreen::class.java)
                            intent.putExtra("list_data", Gson().toJson(array))
                            intent.putExtra("Head1", Head1)
                            intent.putExtra("Head2", Head2)
                            intent.putExtra("Head3", Head3)
                            intent.putExtra("type", "success")
                            intent.putExtra("service", "")
                            startActivity(intent)
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
                        } else {
                            // HideProgress(ctx);
                            progressDialog.dismiss()
                            Toast.makeText(this@PsaRegistrationActivity, "" + jsonObject1.getString("Message"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: java.lang.Exception) {
                    }
                } else {
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@PsaRegistrationActivity, "Due to Internet Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}