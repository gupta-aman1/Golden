package com.example.goldenfish.AddUser

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.goldenfish.Constants.Constant
import com.example.goldenfish.Constants.ConstantsValue
import com.example.goldenfish.R
import com.example.goldenfish.Retrofit.RetrofitClient
import com.example.goldenfish.UserAuth.LoginActivity
import com.example.goldenfish.Utilities.MyUtils
import com.example.goldenfish.Utilities.SharedPref
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.activity_add_user.et_Aadhaar_no
import kotlinx.android.synthetic.main.activity_add_user.et_add
import kotlinx.android.synthetic.main.activity_add_user.et_city
import kotlinx.android.synthetic.main.activity_add_user.et_email
import kotlinx.android.synthetic.main.activity_add_user.et_gender
import kotlinx.android.synthetic.main.activity_add_user.et_mobile
import kotlinx.android.synthetic.main.activity_add_user.et_pan_no
import kotlinx.android.synthetic.main.activity_add_user.et_pass
import kotlinx.android.synthetic.main.activity_add_user.et_pincode
import kotlinx.android.synthetic.main.activity_add_user.et_select_dob
import kotlinx.android.synthetic.main.activity_add_user.et_shop_name
import kotlinx.android.synthetic.main.activity_add_user.et_state
import kotlinx.android.synthetic.main.activity_add_user.et_user_name
import kotlinx.android.synthetic.main.activity_signup_user.*

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddUserActivity : AppCompatActivity() {
    var states = HashMap<String, String>()
    var cities = HashMap<String, String>()
    var scheme = HashMap<String, String>()
    var selectedStateId="";
    var selectedcityId="";
    var selectedschemeId="";
    var stateValue="";
    var cityValue="";
    var userid="";
    var userValue="";
    val myCalendar = Calendar.getInstance()
    var SharedPref:SharedPref?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        SharedPref = com.example.goldenfish.Utilities.SharedPref.getInstance(this@AddUserActivity)
        userid = SharedPref!!.getStringWithNull(Constant.userId)
        val gender_list = arrayListOf<String>()
        gender_list.add("Select Gender")
        gender_list.add("Male")
        gender_list.add("Female")
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, gender_list)
        et_gender.adapter = adapter

        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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
                this@AddUserActivity,
                R.style.MyTimePickerDialogTheme,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.show()
        }

        getStates()
        spinnervalue()
        getUserType()
    }

    private fun getUserType() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Fetching States.....")
        // progressDialog.setMessage("Please wait while logging in...");
        progressDialog.setCancelable(false)
        progressDialog.show()
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid", userid)
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetChildsUserType", "", userid))
        val call = RetrofitClient.getInstance().api.GetChildsUserType(jsonObject)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss()
                    var fullRes: String? = null
                    val al1 = ArrayList<String>()
                    val al2 = ArrayList<String>()
                    al1.add("Select User Type")
                    al2.add("-2")
                    try {
                        fullRes = response.body()!!.string()
                        val jsonObject1 = JSONObject(fullRes)
                        val stCode = jsonObject1.getString(Constant.StatusCode)
                        if (stCode.equals(ConstantsValue.successful, ignoreCase = true)) {

                            var json = jsonObject1.getJSONArray("Data")

                            for (i in 0..json.length()-1) {
                                val jsonObject2 = json.getJSONObject(i)
                                val UserType = jsonObject2.getString("UserType")
                              //  val stateid = jsonObject2.getString("Id")
                                al1.add(UserType)
                                //al2.add(stateid)
                            }
                            val adapter = ArrayAdapter(this@AddUserActivity,
                                android.R.layout.simple_spinner_dropdown_item, al1)
                            et_userType.adapter = adapter
                        } else {
                            // HideProgress(ctx);
                            progressDialog.dismiss()
                            Toast.makeText(
                                this@AddUserActivity,
                                "" + jsonObject1.getString("Message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {

                        System.out.println("EXCEPTION "+e)
                       // chal(al1)

                        //bkl(al1, al2)
                    }
                } else {
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@AddUserActivity, "Due to Internet Error", Toast.LENGTH_SHORT)
                    .show()
            }
        })
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
                                this@AddUserActivity,
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
                Toast.makeText(this@AddUserActivity, "Due to Internet Error", Toast.LENGTH_SHORT)
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
                                this@AddUserActivity,
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
                Toast.makeText(this@AddUserActivity, "Due to Internet Error", Toast.LENGTH_SHORT)
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
                stateValue = parent.getItemAtPosition(position).toString().trim { it <= ' ' }
                val selectedItem = parent.getItemAtPosition(position).toString()
                selectedStateId = states[selectedItem]!! //this is your selected item
                //   Toast.makeText(this@GstDetails, ""+idz, Toast.LENGTH_SHORT).show();
                // mc1()
                if(!selectedStateId.equals("-2")) {
                    et_city.visibility= View.VISIBLE
                    getCity(selectedStateId)
                }
                else
                {
                    et_city.visibility= View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        et_city.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                if (!isSpinnerTouched)
//                    return;
                cityValue = parent.getItemAtPosition(position).toString().trim { it <= ' ' } //this is your selected item
                val selectedItem = parent.getItemAtPosition(position).toString()
                selectedcityId = cities[selectedItem]!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


        et_userType.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                if (!isSpinnerTouched)
//                    return;
                 userValue = parent.getItemAtPosition(position).toString().trim { it <= ' ' } //this is your selected item
                //val selectedItem = parent.getItemAtPosition(position).toString()
               // selectedcityId = cities[selectedItem]!!
               // Toast.makeText(this@AddUserActivity,userValue,Toast.LENGTH_SHORT).show()


                if(!userValue.equals("Select User Type")) {
                    et_scheme.visibility= View.VISIBLE
                    getScheme(userValue)
                }
                else
                {
                    et_scheme.visibility= View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        et_scheme.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                var schemeValue = parent.getItemAtPosition(position).toString().trim { it <= ' ' } //this is your selected item
                val selectedItem = parent.getItemAtPosition(position).toString()
                selectedschemeId = scheme[selectedItem]!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    private fun getScheme(uservalue:String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Fetching Scheme.....")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid",userid)
        jsonObject.addProperty("UserType",uservalue)
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetChildUserSchemeDetails",uservalue,userid))
        val call = RetrofitClient.getInstance().api.GetChildUserSchemeDetails(jsonObject)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss()
                    var fullRes: String? = null
                    val al1 = ArrayList<String>()
                    val al2 = ArrayList<String>()
                    al1.add("Select Scheme")
                    al2.add("-2")
                    try {
                        fullRes = response.body()!!.string()
                        val jsonObject1 = JSONObject(fullRes)
                        val stCode = jsonObject1.getString(Constant.StatusCode)
                        if (stCode.equals(ConstantsValue.successful, ignoreCase = true)) {

                            var json = jsonObject1.getJSONArray("Data")

                            for (i in 0..json.length()-1) {
                                val jsonObject2 = json.getJSONObject(i)
                                val SchemeName = jsonObject2.getString("SchemeName")
                                val id = jsonObject2.getString("Id")
                                al1.add(SchemeName)
                                al2.add(id)
                            }
                            chal2(al1)

                            bkl2(al1, al2)
                        } else {
                            // HideProgress(ctx);
                            progressDialog.dismiss()
                            Toast.makeText(
                                this@AddUserActivity,
                                "" + jsonObject1.getString("Message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {

                    }
                } else {
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@AddUserActivity, "Due to Internet Error", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun chal2(al: ArrayList<String>?) {
        val dataAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, al!!)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        et_scheme.setAdapter(dataAdapter)

    }

    fun bkl2(al1: ArrayList<String>, al2: ArrayList<String>) {
        for (i in al1.indices) scheme.put(al1[i]!!, al2[i]!!)
    }

    fun AddUserClick(view: View) {

        if(et_user_name.text.toString().trim().equals(""))
        {
            et_user_name.setError("Required")
        }
        else if(et_owner_name_child.text.toString().trim().equals(""))
        {
            et_owner_name_child.setError("Required")
        }
        else if(et_shop_name.text.toString().trim().equals(""))
        {
            et_shop_name.setError("Required")
        }
        else if(et_mobile.text.toString().trim().equals("") || et_mobile.text.toString().length !=10)
        {
            et_mobile.setError("Required")
        }
        else if(et_email.text.toString().trim().equals(""))
        {
            et_email.setError("Required")
        }
        else if(et_pass.text.toString().trim().equals(""))
        {
            et_pass.setError("Required")
        }
        else if(et_select_dob.text.toString().trim().equals(""))
        {
            et_select_dob.setError("Required")
        }
        else if(et_gender.getSelectedItem().toString().equals("Select Gender"))
        {
            Toast.makeText(this@AddUserActivity,"Select Gender",Toast.LENGTH_SHORT).show()
            //et_shop_name.setError("Required")
        }
        else if(et_pan_no.text.toString().trim().equals("") || et_pan_no.text.toString().length!=10)
        {
            et_pan_no.setError("Required")
        }
        else if(et_Aadhaar_no.text.toString().trim().equals("") || et_Aadhaar_no.text.toString().length!=12)
        {
            et_Aadhaar_no.setError("Required")
        }
        else if(et_add.text.toString().trim().equals(""))
        {
            et_add.setError("Required")
        }
        else if(selectedStateId.equals("-2"))
        {
            Toast.makeText(this@AddUserActivity,"Select State",Toast.LENGTH_SHORT).show()
        }
        else if(selectedcityId.equals("-2"))
        {
            Toast.makeText(this@AddUserActivity,"Select City",Toast.LENGTH_SHORT).show()
        }
        else if(et_pincode.text.toString().trim().equals("") || et_pincode.text.toString().length!=6)
        {
            et_pincode.setError("Required")
        }
        else if(et_userType.getSelectedItem().toString().equals("Select User Type"))
        {
            Toast.makeText(this@AddUserActivity,"Select User",Toast.LENGTH_SHORT).show()
        }
        else if(selectedschemeId.equals("-2"))
        {
            Toast.makeText(this@AddUserActivity,"Select Scheme",Toast.LENGTH_SHORT).show()
        }
        else
        {
            finalApiForAddUser()
        }

    }

    private fun finalApiForAddUser() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading.....")
        // progressDialog.setMessage("Please wait while logging in...");
        progressDialog.setCancelable(false)
        progressDialog.show()
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid",userid)
        jsonObject.addProperty("UserName", et_user_name.text.toString())
        jsonObject.addProperty("OwnerName", et_owner_name_child.text.toString())
        jsonObject.addProperty("Password", et_pass.text.toString())
        jsonObject.addProperty("ShopName", et_shop_name.text.toString())
        jsonObject.addProperty("Mobile", et_mobile.text.toString())
        jsonObject.addProperty("EmailId", et_email.text.toString())
        jsonObject.addProperty("Gender", et_gender.getSelectedItem().toString())
        jsonObject.addProperty("PanNo", et_pan_no.text.toString())
        jsonObject.addProperty("AadhaarNo", et_Aadhaar_no.text.toString())
        jsonObject.addProperty("Address", et_add.text.toString())
        jsonObject.addProperty("StateId", selectedStateId)
        jsonObject.addProperty("StateName", stateValue)
        jsonObject.addProperty("CityId", selectedcityId)
        jsonObject.addProperty("CityName", cityValue)
        jsonObject.addProperty("Pincode", et_pincode.text.toString())
        jsonObject.addProperty("UserType",et_userType.getSelectedItem().toString())
        jsonObject.addProperty("SchemeId",selectedschemeId)
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("AddChildUser", et_user_name.text.toString()+"|"+et_shop_name.text.toString()+"|"+et_mobile.text.toString(),userid))

       // System.out.println("ADD CHILD "+jsonObject)
        val call = RetrofitClient.getInstance().api.AddChildUser(jsonObject)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.body() != null) {
                    // HideProgress(ctx);
                    progressDialog.dismiss()
                    var fullRes: String? = null

                    try {
                        fullRes = response.body()!!.string()
                        val jsonObject1 = JSONObject(fullRes)

                        val stCode = jsonObject1.getString(Constant.StatusCode)
                        if(stCode.equals("TXN"))
                        {
                            val builder = AlertDialog.Builder(this@AddUserActivity)
                            //set title for alert dialog
                            builder.setTitle("Message")
                            //set message for alert dialog
                            builder.setMessage(jsonObject1.getString("Message"))

                            builder.setPositiveButton("OK"){dialogInterface, which ->
                             finish()
                                // Toast.makeText(applicationContext,"",Toast.LENGTH_LONG).show()
                            }
                            val alertDialog: AlertDialog = builder.create()
                            // Set other dialog properties
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        }
                        else

                        {
                            Toast.makeText(applicationContext,jsonObject1.getString("Message"),Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                    }
                } else {
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@AddUserActivity, "Due to Internet Error", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}