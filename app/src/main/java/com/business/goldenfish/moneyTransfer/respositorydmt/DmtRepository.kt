package com.business.goldenfish.moneyTransfer.respositorydmt

import android.app.ProgressDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.business.goldenfish.moneyTransfer.api.ApiInterface
import com.business.goldenfish.moneyTransfer.modeldmt.*
import com.google.gson.JsonObject
import java.lang.Exception

class DmtRepository(private val ApiInterface : ApiInterface) {

    private val getRemitterDara = MutableLiveData<ModelRemitterDetails>()

    private val ModelRemitterReg = MutableLiveData<ModelRemitterReg>()

    private val ModelRemitterValidation = MutableLiveData<ModelRemitterValidation>()

    private var regBeni= MutableLiveData<ModelRegisterBeni>()

    private var validateotpBeni= MutableLiveData<ModelValidateOtpBeni>()

    private var validateAccBeni= MutableLiveData<ModelAccValidation>()

    private var deleteAccBeni= MutableLiveData<ModelDeleteBEni>()

    private var deleteAccBeniValidate= MutableLiveData<ModelDeleteBeniValidate>()

    val loading = MutableLiveData<ModelProgress>()

    val finalRemitterData :LiveData<ModelRemitterDetails>
    get() = getRemitterDara

    val finalRemitterReg :LiveData<ModelRemitterReg>
    get() = ModelRemitterReg

    val finalModelRemitterValidation :LiveData<ModelRemitterValidation>
    get() = ModelRemitterValidation

    val finalregBeni :LiveData<ModelRegisterBeni>
    get() = regBeni

    val finalvalidateOtpBeni :LiveData<ModelValidateOtpBeni>
    get() = validateotpBeni

    val finalvalidateAccBeni :LiveData<ModelAccValidation>
    get() = validateAccBeni

    val finaldeleteAccBeni :LiveData<ModelDeleteBEni>
    get() = deleteAccBeni

    val finaldeleteAccBeniValidate :LiveData<ModelDeleteBeniValidate>
    get() = deleteAccBeniValidate

    suspend fun getRemitterFromApi(jsonObject:JsonObject)
    {
        loading.postValue(ModelProgress(true,"Validating...."))
        try {
            val result = ApiInterface.getRemiterDetails(jsonObject)
            if(result.body()!=null)
            {
                getRemitterDara.postValue(result.body())
            }
            else
            {
                loading.postValue(ModelProgress(false,"Unable to Processs !!!"))
            }
        }
      catch (e:Exception)
      {
          System.out.println("FINAL DATA "+e.message)
          loading.postValue(ModelProgress(false,"Something went wrong !!!"))
      }
    }

    suspend fun RegisterRemitterFromApi(jsonObject:JsonObject)
    {
        try {
            loading.postValue(ModelProgress(true,"Registering...."))
            val result = ApiInterface.RegisterRemitter(jsonObject)
            if(result.body()!=null)
            {
                ModelRemitterReg.postValue(result.body())
            }
            else
            {
                loading.postValue(ModelProgress(false,"Unable to Processs !!!"))
            }
        }
        catch (e:Exception)
        {
            System.out.println("FINAL DATA "+e.message)
            loading.postValue(ModelProgress(false,"Something went wrong !!!"))
        }
    }

    suspend fun ValidateRemitterFromApi(jsonObject:JsonObject)
    {
        try {
            loading.postValue(ModelProgress(true,"Validating...."))
            val result = ApiInterface.ValidateRemitter(jsonObject)
            if(result.body()!=null)
            {
                ModelRemitterValidation.postValue(result.body())
            }
            else
            {
                loading.postValue(ModelProgress(false,"Unable to Processs !!!"))
            }
        }
        catch (e:Exception)
        {
            System.out.println("FINAL DATA "+e.message)
            loading.postValue(ModelProgress(false,"Something went wrong !!!"))
        }
    }

    suspend fun addBeniApi(jsonObject: JsonObject,progress: ProgressDialog)
    {
        try {
        val result = ApiInterface.RegisterBenificiary(jsonObject)
        if(result.body()!=null)
        {
            regBeni.postValue(result.body())
        }
        }
        catch (e:Exception)
        {
            progress.dismiss()
            System.out.println("FINAL DATA "+e.message)
        }
    }

    suspend fun validateOtpBeni(jsonObject: JsonObject,progress: ProgressDialog)
    {
        try {
            val result = ApiInterface.BenificiaryValidate(jsonObject)
            if(result.body()!=null)
            {
                validateotpBeni.postValue(result.body())
            }

        }
        catch (e:Exception)
        {
            progress.dismiss()
            System.out.println("FINAL DATA "+e.message)
        }
    }

    suspend fun validateAccBeni(jsonObject: JsonObject,progress: ProgressDialog)
    {
        try {
            loading.postValue(ModelProgress(true,"Validating...."))
            val result = ApiInterface.BenificiaryAccountValidation(jsonObject)
            if(result.body()!=null)
            {
                validateAccBeni.postValue(result.body())
            }
            else
            {
                loading.postValue(ModelProgress(false,"Unable to Processs !!!"))
            }
        }
        catch (e:Exception)
        {
            progress.dismiss()
            System.out.println("FINAL DATA "+e.message)
            loading.postValue(ModelProgress(false,"Something went wrong !!!"))
        }
    }

    suspend fun deleteAccBeni(jsonObject: JsonObject,progress: ProgressDialog)
    {
        try {
            loading.postValue(ModelProgress(true,"Validating...."))
            val result = ApiInterface.BenificiaryRemove(jsonObject)
            if(result.body()!=null)
            {
                deleteAccBeni.postValue(result.body())
            }
            else
            {
                loading.postValue(ModelProgress(false,"Unable to Processs !!!"))
            }
        }
        catch (e:Exception)
        {
            progress.dismiss()
            System.out.println("FINAL DATA "+e.message)
            loading.postValue(ModelProgress(false,"Something went wrong !!!"))
        }
    }

    suspend fun deleteAccBeniValidate(jsonObject: JsonObject,progress: ProgressDialog)
    {
        try {
            loading.postValue(ModelProgress(true,"Validating...."))
            val result = ApiInterface.BenificiaryRemoveValidate(jsonObject)
            if(result.body()!=null)
            {
                deleteAccBeniValidate.postValue(result.body())
            }
            else
            {
                loading.postValue(ModelProgress(false,"Unable to Processs !!!"))
            }
        }
        catch (e:Exception)
        {
            progress.dismiss()
            System.out.println("FINAL DATA "+e.message)
            loading.postValue(ModelProgress(false,"Something went wrong !!!"))
        }
    }
}