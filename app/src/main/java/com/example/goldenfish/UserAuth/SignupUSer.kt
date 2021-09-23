package com.example.goldenfish.UserAuth

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.goldenfish.Constants.Constant
import com.example.goldenfish.Constants.ConstantsValue
import com.example.goldenfish.R
import com.example.goldenfish.Retrofit.RetrofitClient
import com.example.goldenfish.Utilities.MyUtils
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_signup_user.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class SignupUSer : AppCompatActivity() {

    var states = HashMap<String, String>()
    var cities = HashMap<String, String>()
    var selectedStateId="";
    var selectedcityId="";
    val myCalendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_user)

       // var arr:ArrayList<String>
        System.out.println("TEST USER")
        val gender_list = arrayListOf<String>()
        gender_list.add("Select Gender")
        gender_list.add("Male")
        gender_list.add("Female")
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, gender_list)
        et_gender.adapter = adapter

        getStates()
        spinnervalue()

        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
               /* if (choosedate == "todate") {
                    tv_to_date.setText("$dayOfMonth-$monthOfYear-$year")
                } else {
                    tv_from_date.setText("$dayOfMonth-$monthOfYear-$year")
                }*/
                et_select_dob.setText("$dayOfMonth-$monthOfYear-$year")
            }

        et_select_dob.setOnClickListener {

            val datePickerDialog = DatePickerDialog(
                this@SignupUSer,
                R.style.MyTimePickerDialogTheme,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.show()
        }
    }

    fun ClickLogin(view: View) {

        startActivity(Intent(this,LoginActivity::class.java))
    }

    fun SignupUser(view: View) {

    }

    private fun getStates() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Fetching States.....")
        // progressDialog.setMessage("Please wait while logging in...");
        progressDialog.setCancelable(false)
        progressDialog.show()
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid", "13598")
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetStateListDetails", "", "13598"))
        val call = RetrofitClient.getInstance().api.GetStateListDetails(jsonObject)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss()
                    var fullRes: String? = null
                    val al1 = ArrayList<String>()
                    val al2 = ArrayList<String>()
                    al1.add("Select State")
                    al2.add("-2")
                    try {
                        fullRes = response.body()!!.string()
                        val jsonObject1 = JSONObject(fullRes)
                        val stCode = jsonObject1.getString(Constant.StatusCode)
                        if (stCode.equals(ConstantsValue.successful, ignoreCase = true)) {

                            var json = jsonObject1.getJSONArray("Data")

                            for (i in 0..json.length()) {
                                val jsonObject2 = json.getJSONObject(i)
                                val statename = jsonObject2.getString("StateName")
                                val stateid = jsonObject2.getString("Id")
                                al1.add(statename)
                                al2.add(stateid)
                            }

                        } else {
                            // HideProgress(ctx);
                            progressDialog.dismiss()
                            Toast.makeText(
                                this@SignupUSer,
                                "" + jsonObject1.getString("Message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        chal(al1)

                        bkl(al1, al2)
                    }
                } else {
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@SignupUSer, "Due to Internet Error", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun chal(al: ArrayList<String>?) {
        val dataAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, al!!)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        et_state.setAdapter(dataAdapter)

    }

    fun bkl(al1: ArrayList<String>, al2: ArrayList<String>) {
        for (i in al1.indices) states.put(al1[i]!!, al2[i]!!)
    }

    private fun getCity(state_id:String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Fetching Cities.....")
        // progressDialog.setMessage("Please wait while logging in...");
        progressDialog.setCancelable(false)
        progressDialog.show()
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid", "13598")
        jsonObject.addProperty("StateId", state_id)
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetCityListDetails", state_id, "13598"))
        val call = RetrofitClient.getInstance().api.GetCityListDetails(jsonObject)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss()
                    var fullRes: String? = null
                    val al1 = ArrayList<String>()
                    val al2 = ArrayList<String>()
                    al1.add("Select City")
                    al2.add("-2")
                    try {
                        fullRes = response.body()!!.string()
                        val jsonObject1 = JSONObject(fullRes)
                        val stCode = jsonObject1.getString(Constant.StatusCode)
                        if (stCode.equals(ConstantsValue.successful, ignoreCase = true)) {

                            var json = jsonObject1.getJSONArray("Data")

                            for (i in 0..json.length()) {
                                val jsonObject2 = json.getJSONObject(i)
                                val CityName = jsonObject2.getString("CityName")
                                val stateid = jsonObject2.getString("Id")
                                al1.add(CityName)
                                al2.add(stateid)
                            }

                        } else {
                            // HideProgress(ctx);
                            progressDialog.dismiss()
                            Toast.makeText(
                                this@SignupUSer,
                                "" + jsonObject1.getString("Message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        chal1(al1)

                        bkl1(al1, al2)
                    }
                } else {
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@SignupUSer, "Due to Internet Error", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun chal1(al: ArrayList<String>?) {
        val dataAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, al!!)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        et_city.setAdapter(dataAdapter)

    }

    fun bkl1(al1: ArrayList<String>, al2: ArrayList<String>) {
        for (i in al1.indices) cities.put(al1[i]!!, al2[i]!!)
    }

    fun spinnervalue() {
        et_state.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                if (!isSpinnerTouched)
//                    return;
               var statevalue = parent.getItemAtPosition(position).toString().trim { it <= ' ' }
                val selectedItem = parent.getItemAtPosition(position).toString()
                selectedStateId = states[selectedItem]!! //this is your selected item
                //   Toast.makeText(this@GstDetails, ""+idz, Toast.LENGTH_SHORT).show();
                // mc1()
                if(!selectedStateId.equals("-2")) {
                    et_city.visibility=View.VISIBLE
                    getCity(selectedStateId)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        et_city.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                if (!isSpinnerTouched)
//                    return;
               var  cityvalue = parent.getItemAtPosition(position).toString().trim { it <= ' ' } //this is your selected item
                val selectedItem = parent.getItemAtPosition(position).toString()
                selectedcityId = cities[selectedItem]!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }


}