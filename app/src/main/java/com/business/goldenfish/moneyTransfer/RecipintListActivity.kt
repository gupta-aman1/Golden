package com.business.goldenfish.moneyTransfer

import android.app.AlertDialog
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.InputFilter
import android.text.InputFilter.AllCaps
import android.view.View
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.business.goldenfish.BaseActivity
import com.business.goldenfish.Common.CommonApi
import com.business.goldenfish.Common.CommonFun
import com.business.goldenfish.Common.CommonInterface
import com.business.goldenfish.Constants.Constant
import com.business.goldenfish.R
import com.business.goldenfish.Utilities.*
import com.business.goldenfish.databinding.ActivityRecipintListBinding
import com.business.goldenfish.moneyTransfer.adapter.BeniAdapter
import com.business.goldenfish.moneyTransfer.api.ApiInterface
import com.business.goldenfish.moneyTransfer.api.ApiUtilities
import com.business.goldenfish.moneyTransfer.modeldmt.Beneficiary
import com.business.goldenfish.moneyTransfer.respositorydmt.DmtRepository
import com.business.goldenfish.moneyTransfer.viewmodeldmt.RecipintListViewModel
import com.business.goldenfish.moneyTransfer.viewmodeldmt.RemiterDataViewModel
import com.business.goldenfish.moneyTransfer.viewmodeldmt.RemiterViewModelFactory
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_recipint_list.*
import kotlinx.android.synthetic.main.activity_w_t_s__aeps_.*
import java.lang.reflect.Type


class RecipintListActivity : BaseActivity(),CommonInterface, OnDataReceiverListener, GpsInterface {

    var sharedPref: SharedPref? = null
    var userid:String?=null
    var OutletId:String?=null
    var beniAccNo:String?=null
    var beniIfsc:String?=null
    var alertDialogs: AlertDialog? = null
    var holderNameEt: EditText? = null
    private lateinit var viewModel : RecipintListViewModel
    private lateinit var viewModel1 :RemiterDataViewModel
    val gson = Gson()
    private val adapter by lazy {
        BeniAdapter()
    }
    lateinit var binding:ActivityRecipintListBinding
    var RemitterId:String?=null
    var RemitterMobile:String?=null
    var benificiaryId:String?=null
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
    var mutablelistbeni =MutableLiveData<ArrayList<Beneficiary>>()

    val finalmutablelistbeni : LiveData<ArrayList<Beneficiary>>
    get() = mutablelistbeni

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityRecipintListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref.getInstance(this)
        userid = sharedPref?.getStringWithNull(Constant.userId)
        OutletId = sharedPref?.getStringWithNull(Constant.OutletId)

        if(intent.extras !=null)
        {
            RemitterId=  intent.getStringExtra(Constant.RemitterId)
          val RemitterName=  intent.getStringExtra(Constant.RemitterName)
           RemitterMobile=  intent.getStringExtra(Constant.RemitterMobile)
          val RemitterTotalBal=  intent.getStringExtra(Constant.RemitterTotalBal)
          val RemitterAvBal=  intent.getStringExtra(Constant.RemitterAvBal)
          val RemitterBeniList=  intent.getStringExtra(Constant.RemitterBeniList)

            val listType: Type = object : TypeToken<List<Beneficiary?>?>() {}.type

          //   val yourList: ArrayList<Beneficiary> = Gson().fromJson(RemitterBeniList, listType)
            mutablelistbeni.postValue(Gson().fromJson(RemitterBeniList, listType))

            binding.name.text=RemitterName
            binding.montlyLimitTxt.text="₹ "+RemitterTotalBal
            binding.availableLimit.text="₹ "+RemitterAvBal


            val verticalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            beneficieryRecyclerView.layoutManager = verticalLayoutManager
            beneficieryRecyclerView.adapter = adapter



            adapter.listener = { view, item, position ->

                val id =view.id
                when(id){
                    R.id.verifiedBtn ->{
                        val jsonObject = JsonObject()
                jsonObject.addProperty("Userid", userid)
                jsonObject.addProperty("OpId", "89")
                jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetSurchargeUsingOpId","89", userid))
                CommonApi.getSurchargeByOperator(this, jsonObject, this)
                    }
                    R.id.payNow ->{
                      //  Toast.makeText(this,"paynow clicked btn clicked",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, PayNowActivity::class.java)
                            .putExtra(Constant.BankName,item.bank)
                            .putExtra(Constant.BenificiaryIFSC,item.ifsc)
                            .putExtra(Constant.BeniAccountNo,item.account)
                            .putExtra(Constant.BenificiaryId,item.id)
                            .putExtra(Constant.RemitterMobile,RemitterMobile))
                    }
                }
               // Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show()
//                val jsonObject = JsonObject()
//                jsonObject.addProperty("Userid", userid)
//                jsonObject.addProperty("OpId", "0")
//                jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetSurchargeUsingOpId","0", userid))
//                CommonApi.getSurchargeByOperator(this, jsonObject, this)

            }


        }

        val apiInt = ApiUtilities.getInstance().create(ApiInterface::class.java)

        val repo = DmtRepository(apiInt)

        viewModel = ViewModelProvider(this,RemiterViewModelFactory(repo,"RecipintListViewModel")).get(RecipintListViewModel::class.java)

        viewModel1 = ViewModelProvider(this,RemiterViewModelFactory(repo,"RemiterDataViewModel")).get(RemiterDataViewModel::class.java)


        viewModel.ModelRegisterBeni.observe(this,{ ModelRegisterBeni ->
            System.out.println("HELLO "+ModelRegisterBeni)
            hideProgressDialog()
            if(ModelRegisterBeni.Statuscode.equals("TXN"))
            {
                alertDialogs?.dismiss()
                val Data = ModelRegisterBeni.Data.get(0).data
                val jsonObject: JsonObject = gson.toJsonTree(Data).getAsJsonObject()
                var beneficiary = jsonObject.getAsJsonObject("beneficiary")
                if(beneficiary.get("status").asString.equals("0"))
                {
                   benificiaryId= beneficiary.get("id").asString
                    CommonFun.showOTPDialog(this, this)
                }
                else
                {
                    val jsonObject = JsonObject()
                    jsonObject.addProperty("Userid",userid)
                    jsonObject.addProperty("RemitterMobile",RemitterMobile)
                    jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetRemitterDetails",RemitterMobile, userid))
                    viewModel1.getdata(jsonObject)
                }

            }
            else
            {
                Toast.makeText(this,ModelRegisterBeni.Message,Toast.LENGTH_SHORT).show()
            }

        })


        viewModel1.data.observe(this, { ModelRedmitterData ->

            System.out.println("FINAL DATA "+ModelRedmitterData)

            if(ModelRedmitterData.Statuscode.equals("TXN")) {

                val Data = ModelRedmitterData.Data.data

                val jsonObject: JsonObject = gson.toJsonTree(Data).getAsJsonObject()

                var beneficiary_list = jsonObject.getAsJsonArray("beneficiary")

               // val jsonInString = gson.toJson(beneficiary_list)

                  val listType: Type = object : TypeToken<List<Beneficiary?>?>() {}.type

               //  val yourList: ArrayList<String> = Gson().fromJson(beneficiary_list, listType)
                   mutablelistbeni.postValue(Gson().fromJson(beneficiary_list, listType))

            }

            else
            {
                Toast.makeText(this,ModelRedmitterData.Message,Toast.LENGTH_SHORT).show()
            }

        })

        viewModel.ModelValidateOtpBeni.observe(this,{ ModelValidateOtpBeni ->

            if(ModelValidateOtpBeni.Statuscode.equals("TXN"))
            {
                val jsonObject = JsonObject()
                jsonObject.addProperty("Userid",userid)
                jsonObject.addProperty("RemitterMobile",RemitterMobile)
                jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetRemitterDetails",RemitterMobile, userid))
                viewModel1.getdata(jsonObject)
            }
            else
            {
                Toast.makeText(this,ModelValidateOtpBeni.Message,Toast.LENGTH_SHORT).show()
            }
        })


        finalmutablelistbeni.observe(this, { data ->
            adapter.addItems(data)
            hideProgressDialog()
        })

        viewModel.ModelAccValidation.observe(this,{ ModelAccValidation->
            hideProgressDialog()
            System.out.println("FINAL DATA "+ModelAccValidation)

            if(ModelAccValidation.Statuscode.equals("TXN"))
            {
               var name= ModelAccValidation.Data.get(0).BeniName

                holderNameEt?.setText(name)
            }
            else
            {
                Toast.makeText(this,ModelAccValidation.Message,Toast.LENGTH_SHORT).show()
            }
        })

        binding.addBeneficieryBtn.setOnClickListener {
            addBeneficieryDialog()
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
    }

    override fun getMoveToBankSurcharge(CommPer: String?, CommType: String?, ChargePer: String?, ChargeType: String?)
    {
        super.getMoveToBankSurcharge(CommPer, CommType, ChargePer, ChargeType)


        // System.out.println("SURCARGE "+ChargeType+ "|"+ ChargePer);
        val alertDialog = AlertDialog.Builder(this).create()
        val inflater = layoutInflater
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogView = inflater.inflate(R.layout.movetobankdialog, null)

        val head = dialogView.findViewById<View>(R.id.head) as TextView
        val subhead1 = dialogView.findViewById<View>(R.id.subhead1) as TextView
        val subhead2 = dialogView.findViewById<View>(R.id.subhead2) as TextView
        val btn_yes = dialogView.findViewById<View>(R.id.btn_yes) as Button
        val btn_no = dialogView.findViewById<View>(R.id.btn_no) as Button
        head.text = "Are you Sure to Proceed?"
        subhead1.text = "Charges for Transferring Amount :"
        if (ChargeType.equals("false", ignoreCase = true)) {
            // surchargeTxt.setText("Surcharge = ₹ " + resSurcharge);
            subhead2.text = "Charge = ₹ $ChargePer"
        } else {
            subhead2.text = "Surcharge = $ChargePer%"
        }
        btn_no.setOnClickListener { alertDialog.dismiss() }

        btn_yes.setOnClickListener {
            alertDialog.dismiss()
            val jsonObject = JsonObject()
          jsonObject.addProperty("Userid",userid)
          jsonObject.addProperty("WalletType","Wallet 2")
          jsonObject.addProperty("RemitterMobile",RemitterMobile)
          jsonObject.addProperty("BeniAccountNo",beniAccNo)
          jsonObject.addProperty("BenificiaryIFSC",beniIfsc)
          jsonObject.addProperty("OutletId",OutletId)
          jsonObject.addProperty("Latitude",this.latitude)
          jsonObject.addProperty("Longitude",this.longitude)
          jsonObject.addProperty("IpAddress",UtilsIP.getIPAddress(true))
          jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("BenificiaryAccountValidation","Wallet 2"+"|"+RemitterMobile+"|"+beniAccNo+"|"+beniIfsc,userid))
           showProgressDialog("Verifying....")
            viewModel.validateAccBeni(jsonObject,mProgressDialog!!)
        }
        alertDialog.setView(dialogView)
        alertDialog.show()
    }

    override fun getEnteredOTP(otp: String?) {
        super.getEnteredOTP(otp)
        showProgressDialog("Validating...")
        val jsonObject = JsonObject()
        jsonObject.addProperty("Userid",userid)
        jsonObject.addProperty("RemitterId",RemitterId)
        jsonObject.addProperty("BenificiaryId",benificiaryId)
        jsonObject.addProperty("BenificiaryOTP",otp)
        jsonObject.addProperty("OutletId",OutletId)
        jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("BenificiaryValidate",RemitterId+"|"+benificiaryId+"|"+otp+"|"+OutletId, userid))
        viewModel.validateOtpBeni(jsonObject,mProgressDialog!!)

    }

    private fun addBeneficieryDialog()
    {
        alertDialogs = AlertDialog.Builder(this).create()
        val inflater = layoutInflater
        val convertView: View = inflater.inflate(com.business.goldenfish.R.layout.layout_beniadd, null)
        alertDialogs?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val close = convertView.findViewById<ImageView>(com.business.goldenfish.R.id.closebeni)
        val ifscET = convertView.findViewById<EditText>(com.business.goldenfish.R.id.ifscET)
        val addBene = convertView.findViewById<Button>(com.business.goldenfish.R.id.addBene)
         holderNameEt = convertView.findViewById<EditText>(com.business.goldenfish.R.id.holderNameEt)
        val mobileNumberETbeni = convertView.findViewById<EditText>(com.business.goldenfish.R.id.mobileNumberETbeni)
        val accountNo_ET = convertView.findViewById<EditText>(com.business.goldenfish.R.id.accountNo_ET)
        val verify_acc = convertView.findViewById<TextView>(com.business.goldenfish.R.id.verify_acc)
        ifscET.setFilters(arrayOf<InputFilter>(AllCaps()))

        verify_acc.setOnClickListener {
            if(accountNo_ET.text.toString().trim().equals(""))
             {
            accountNo_ET.setError("Required")
              }
             else if(ifscET.text.toString().trim().equals(""))
              {
            ifscET.setError("Required")
             }
            else {

                beniAccNo = accountNo_ET.text.toString().trim()
                beniIfsc = ifscET.text.toString().trim()
                val jsonObject = JsonObject()
                jsonObject.addProperty("Userid", userid)
                jsonObject.addProperty("OpId", "89")
                jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("GetSurchargeUsingOpId", "89", userid))
                CommonApi.getSurchargeByOperator(this, jsonObject, this)
            }
        }

        close.setOnClickListener {
            alertDialogs?.dismiss()
        }

        addBene.setOnClickListener {
            if(holderNameEt?.text.toString().trim().equals(""))
            {
                holderNameEt?.setError("Required")
            }

            else if(mobileNumberETbeni.text.toString().trim().equals("") || mobileNumberETbeni.text.toString().length!=10)
            {
                mobileNumberETbeni.setError("Required")
            }
            else if(accountNo_ET.text.toString().trim().equals(""))
            {
                accountNo_ET.setError("Required")
            }
            else if(ifscET.text.toString().trim().equals(""))
            {
                ifscET.setError("Required")
            }
            else
            {
                showProgressDialog("Registering....")
                val jsonObject = JsonObject()
                jsonObject.addProperty("Userid",userid)
                jsonObject.addProperty("RemitterId",RemitterId)
                jsonObject.addProperty("BenificiaryName",holderNameEt?.text.toString().trim())
                jsonObject.addProperty("BenificiaryMobile",mobileNumberETbeni.text.toString().trim())
                jsonObject.addProperty("BenificiaryAccount",accountNo_ET.text.toString().trim())
                jsonObject.addProperty("BenificiaryIFSC",ifscET.text.toString().trim())
                jsonObject.addProperty("OutletId",OutletId)
                jsonObject.addProperty(Constant.Checksum, MyUtils.encryption("RegisterBenificiary",RemitterId+"|"+holderNameEt?.text.toString().trim()+"|"+mobileNumberETbeni.text.toString().trim()+"|"+accountNo_ET.text.toString().trim()+"|"+ifscET.text.toString().trim()+"|"+OutletId, userid))
                viewModel.getBeniFromApi(jsonObject,mProgressDialog!!)
                //RegisterRemitter(et_mobile.text.toString().trim(),et_namedmt.text.toString().trim(),et_sur_namedmt.text.toString().trim(),et_pinCodedmt.text.toString().trim())
            }
        }
        alertDialogs?.setView(convertView)
        alertDialogs?.show()
        alertDialogs?.setCancelable(false)
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
}