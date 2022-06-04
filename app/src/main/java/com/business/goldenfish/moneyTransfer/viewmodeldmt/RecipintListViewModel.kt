package com.business.goldenfish.moneyTransfer.viewmodeldmt

import android.app.ProgressDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.business.goldenfish.moneyTransfer.modeldmt.ModelAccValidation
import com.business.goldenfish.moneyTransfer.modeldmt.ModelRegisterBeni
import com.business.goldenfish.moneyTransfer.modeldmt.ModelValidateOtpBeni
import com.business.goldenfish.moneyTransfer.respositorydmt.DmtRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipintListViewModel(private val repo:DmtRepository) :ViewModel() {



     fun getBeniFromApi(jsonObject: JsonObject,mProgressDialog: ProgressDialog)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addBeniApi(jsonObject,mProgressDialog)
        }

    }

    fun validateOtpBeni(jsonObject: JsonObject,mProgressDialog: ProgressDialog)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repo.validateOtpBeni(jsonObject,mProgressDialog)
        }

    }

    fun validateAccBeni(jsonObject: JsonObject,mProgressDialog: ProgressDialog)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repo.validateAccBeni(jsonObject,mProgressDialog)
        }

    }

    val ModelRegisterBeni : LiveData<ModelRegisterBeni>
    get() = repo.finalregBeni

    val ModelValidateOtpBeni : LiveData<ModelValidateOtpBeni>
    get() = repo.finalvalidateOtpBeni

    val ModelAccValidation : LiveData<ModelAccValidation>
    get() = repo.finalvalidateAccBeni
}