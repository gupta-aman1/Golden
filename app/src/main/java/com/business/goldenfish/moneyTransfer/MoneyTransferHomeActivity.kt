package com.business.goldenfish.moneyTransfer

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.business.goldenfish.BaseActivity
import com.business.goldenfish.Common.CommonFun
import com.business.goldenfish.Common.CommonInterface
import com.business.goldenfish.Constants.Constant
import com.business.goldenfish.R
import com.business.goldenfish.Utilities.MyUtils
import com.business.goldenfish.Utilities.SharedPref
import com.business.goldenfish.databinding.ActivityMoneyTransferHomeBinding
import com.business.goldenfish.moneyTransfer.api.ApiInterface
import com.business.goldenfish.moneyTransfer.api.ApiUtilities
import com.business.goldenfish.moneyTransfer.respositorydmt.DmtRepository
import com.business.goldenfish.moneyTransfer.viewmodeldmt.RemiterDataViewModel
import com.business.goldenfish.moneyTransfer.viewmodeldmt.RemiterViewModelFactory
import com.google.gson.Gson
import com.google.gson.JsonObject


class MoneyTransferHomeActivity : BaseActivity(),CommonInterface {

    private lateinit var viewModel :RemiterDataViewModel
    var sharedPref: SharedPref? = null
    var userid:String?=null
    var remitterId:String?=null
    var remitterName:String?=null
    var OutletId:String?=null
    var alertDialogs: AlertDialog? = null
    lateinit var binding:ActivityMoneyTransferHomeBinding
    val gson = Gson()
     var REQUEST_CONTACT=1
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMoneyTransferHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolBarContainer12.activityTitle.setText("Money Transfer")

        binding.toolBarContainer12.backButton.setOnClickListener {
            finish()
        }

        binding.etRechargeNumber.onRightDrawableClicked {

            CommonFun.requestStoragePermission(this, this)
        }


            val apiInt = ApiUtilities.getInstance().create(ApiInterface::class.java)

        val repo = DmtRepository(apiInt)

        viewModel = ViewModelProvider(this,RemiterViewModelFactory(repo,"RemiterDataViewModel")).get(RemiterDataViewModel::class.java)

        sharedPref = SharedPref.getInstance(this@MoneyTransferHomeActivity)
        userid = sharedPref?.getStringWithNull(Constant.userId)
        OutletId = sharedPref?.getStringWithNull(Constant.OutletId)


        repo.loading.observe(this,{
            val it1= it.progress
            when(it1)
            {
                true -> {
                    showProgressDialog(it.msg)
                }
                false -> {
                    hideProgressDialog()
                }
            }
        })
        viewModel.data.observe(this, { ModelRedmitterData ->

            System.out.println("FINAL DATA "+ModelRedmitterData)
                hideProgressDialog()

            if( ModelRedmitterData.Statuscode.equals("ERR") &&  ModelRedmitterData.Data.statuscode.equals("RNF"))
            {
                alertDialog()

            }
            else if(ModelRedmitterData.Statuscode.equals("TXN") &&  ModelRedmitterData.Data.statuscode.equals("TXN") && ModelRedmitterData.Data.status.equals("OTP sent successfully"))
            {
                CommonFun.showOTPDialog(this, this)

            }
           else if(ModelRedmitterData.Statuscode.equals("TXN")) {

                val Data = ModelRedmitterData.Data.data

                val jsonObject: JsonObject = gson.toJsonTree(Data).getAsJsonObject()

                var beneficiary_list = jsonObject.getAsJsonArray("beneficiary")

                var remitter_limit = jsonObject.getAsJsonArray("remitter_limit")

                var remitterJson= jsonObject.getAsJsonObject("remitter")

                remitterId=  remitterJson.get("id").asString

                remitterName=  remitterJson.get("name").asString

                var limitJson= remitter_limit.get(0).asJsonObject

                var limit= limitJson.getAsJsonObject("limit")

                var total= limit.get("total").asString

                var remaining= limit.get("remaining").asString

                //System.out.println("TOTAL "+total)

                val jsonInString = gson.toJson(beneficiary_list)


                startActivity(Intent(this, RecipintListActivity::class.java)
                    .putExtra(Constant.RemitterId,remitterId)
                    .putExtra(Constant.RemitterName,remitterName)
                    .putExtra(Constant.RemitterTotalBal,total)
                    .putExtra(Constant.RemitterAvBal,remaining)
                    .putExtra(Constant.RemitterBeniList,jsonInString)
                    .putExtra(Constant.RemitterMobile,binding.etRechargeNumber.text.toString()))

                //  val listType: Type = object : TypeToken<List<Beneficiary?>?>() {}.type

                // val yourList: ArrayList<String> = Gson().fromJson(beneficiary_list, listType)

            }

            else
            {
                Toast.makeText(this,ModelRedmitterData.Message,Toast.LENGTH_SHORT).show()
            }

        })

        viewModel.ModelRemitterReg.observe(this,{ ModelRegRemitter ->

            System.out.println("FINAL DATA "+ModelRegRemitter)
            hideProgressDialog()
            if(ModelRegRemitter.Statuscode.equals("TXN") && ModelRegRemitter.Data.get(0).statuscode.equals("TXN"))
            {
                    if(ModelRegRemitter.Data.get(0).data.remitter.is_verified == 0)
                    {
                        alertDialogs?.dismiss()
                        remitterId = ModelRegRemitter.Data.get(0).data.remitter.id
                        CommonFun.showOTPDialog(this, this)
                    }
                    else
                    {
                       // startActivity(Intent(this, MoneyTransferHomeActivity::class.java)
                       //     .putExtra(Constant.RemitterId,remitterId))

                        val jsonObject = JsonObject()
                        jsonObject.addProperty("Userid",userid)
                        jsonObject.addProperty("RemitterMobile",binding.etRechargeNumber.text.toString())
                        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetRemitterDetails",binding.etRechargeNumber.text.toString(), userid))

                        viewModel.getdata(jsonObject)
                    }
            }
            else
            {
                Toast.makeText(this,ModelRegRemitter.Message,Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.ModelRemitterValidation.observe(this,{ RemitterValidation ->

            System.out.println("FINAL DATA "+RemitterValidation)
            hideProgressDialog()
            if(RemitterValidation.Statuscode.equals("TXN"))
            {

                val Data = RemitterValidation.Data.get(0).data

                val jsonObject: JsonObject = gson.toJsonTree(Data).getAsJsonObject()

                val remitter= jsonObject.getAsJsonObject("remitter")

               remitterId = remitter.get("id").asString

             //   startActivity(Intent(this, MoneyTransferHomeActivity::class.java)
            //        .putExtra(Constant.RemitterId,remitterId))

                val jsonObject1 = JsonObject()
                jsonObject1.addProperty("Userid",userid)
                jsonObject1.addProperty("RemitterMobile",binding.etRechargeNumber.text.toString())
                jsonObject1.addProperty(Constant.Checksum, MyUtils.encryption("GetRemitterDetails",binding.etRechargeNumber.text.toString(), userid))

                viewModel.getdata(jsonObject1)
            }
            else
            {
                Toast.makeText(this,RemitterValidation.Message,Toast.LENGTH_SHORT).show()
            }

        })


        binding.btnProceed.setOnClickListener {

            if(binding.etRechargeNumber.text.toString().trim().equals("") || binding.etRechargeNumber.text.toString().length!=10)
            {
                binding.etRechargeNumber.setError("Required !!!")
            }
            else
            {
                val jsonObject = JsonObject()
                jsonObject.addProperty("Userid",userid)
                jsonObject.addProperty("RemitterMobile",binding.etRechargeNumber.text.toString())
                jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetRemitterDetails",binding.etRechargeNumber.text.toString(), userid))

                viewModel.getdata(jsonObject)
            }
        }


    }


    override fun requestPermission(status: Boolean) {
        super.requestPermission(status)
        if(status)
        {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, REQUEST_CONTACT)
        }
        else
        {
            CommonFun.showSettingsDialog(this)
        }
    }
    override fun getEnteredOTP(otp: String?) {
        super.getEnteredOTP(otp)
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid",userid)
        jsonObject.addProperty("RemitterId",remitterId)
        jsonObject.addProperty("RemitterMobile",binding.etRechargeNumber.text.toString())
        jsonObject.addProperty("RemitterOTP",otp)
        jsonObject.addProperty("OutletId",OutletId)
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("ValidateRemitter",remitterId+"|"+binding.etRechargeNumber.text.toString()+"|"+otp+"|"+OutletId, userid))

        viewModel.getRemitterValidation(jsonObject)

    }

    private fun alertDialog()
    {
        alertDialogs = AlertDialog.Builder(this).create()
        val inflater = layoutInflater
        val convertView: View = inflater.inflate(R.layout.layout_agentkyc, null)
        alertDialogs?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val close = convertView.findViewById<ImageView>(R.id.closedmt)
        val et_mobile = convertView.findViewById<EditText>(R.id.et_mobiledmt)
        val proceed_Btndmt = convertView.findViewById<Button>(R.id.proceed_Btndmt)
        val et_namedmt = convertView.findViewById<EditText>(R.id.et_namedmt)
        val et_sur_namedmt = convertView.findViewById<EditText>(R.id.et_sur_namedmt)
        val et_pinCodedmt = convertView.findViewById<EditText>(R.id.et_pinCodedmt)
        et_mobile.setText(binding.etRechargeNumber.text.toString())

        close.setOnClickListener {
            alertDialogs?.dismiss()
        }
        proceed_Btndmt.setOnClickListener {
            if(et_namedmt.text.toString().trim().equals(""))
            {
                et_namedmt.setError("Required")
            }
            else if(et_sur_namedmt.text.toString().trim().equals(""))
            {
                et_sur_namedmt.setError("Required")
            }
            else if(et_mobile.text.toString().trim().equals("") || et_mobile.text.toString().length!=10)
            {
                et_mobile.setError("Required")
            }
            else if(et_pinCodedmt.text.toString().trim().equals("") || et_pinCodedmt.text.toString().length!=6)
            {
                et_pinCodedmt.setError("Required")
            }
            else
            {
                RegisterRemitter(et_mobile.text.toString().trim(),et_namedmt.text.toString().trim(),et_sur_namedmt.text.toString().trim(),et_pinCodedmt.text.toString().trim())
            }
        }
        alertDialogs?.setView(convertView)
        alertDialogs?.show()
        alertDialogs?.setCancelable(false)
    }

    private fun RegisterRemitter(mobile:String,name:String,surname:String,pincode:String)
    {
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid",userid)
        jsonObject.addProperty("RemitterMobile",mobile)
        jsonObject.addProperty("RemitterName",name)
        jsonObject.addProperty("RemitterSurName",surname)
        jsonObject.addProperty("RemitterPincode",pincode)
        jsonObject.addProperty("OutletId",OutletId)
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("RegisterRemitter",mobile+"|"+name+"|"+surname+"|"+pincode+"|"+OutletId, userid))

        viewModel.getRegisterRemitter(jsonObject)
    }

    fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
        this.setOnTouchListener { v, event ->
            var hasConsumed = false
            if (v is EditText) {
                if (event.x >= v.width - v.totalPaddingRight) {
                    if (event.action == MotionEvent.ACTION_UP) {
                        onClicked(this)
                    }
                    hasConsumed = true
                }
            }
            hasConsumed
        }
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == REQUEST_CONTACT && data?.data != null) {
                val contactUri = data.data;
                val crContacts = contentResolver.query(contactUri!!, null, null, null, null);
                crContacts?.moveToFirst()
                val id = crContacts?.getString(crContacts.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(crContacts?.getString(crContacts?.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    val crPhones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = ?", arrayOf(id), null)
                    crPhones?.moveToFirst()
                    var number = crPhones?.getString(
                        crPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    var name = crPhones?.getString(
                        crPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    crPhones?.close()

                    if (number!!.contains("+")){
                        number = number.substring(3, number.length).trim();
                        number = number.replace(" ", "");
                        binding.etRechargeNumber.setText(number.toString())
                       // binding.etCustName.setText(name.toString())
                    }else {
                        var finalNumber:String=""
                        number = number.replace(" ", "");
                        number = number.trim();

                        if (number.length > 10)
                        {
                            finalNumber = number.substring(number.length - 10);
                        }
                        else
                        {
                            finalNumber = number;
                        }
                        binding.etRechargeNumber.setText(finalNumber.toString())
                       // binding.etCustName.setText(name.toString())
                    }

                }
                crContacts?.close()
            }
        }
        catch (e: Exception)
        {
            Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()
        }

    }
}