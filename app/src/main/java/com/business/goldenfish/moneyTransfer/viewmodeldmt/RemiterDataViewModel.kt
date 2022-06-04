package com.business.goldenfish.moneyTransfer.viewmodeldmt

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.business.goldenfish.moneyTransfer.modeldmt.ModelRemitterDetails
import com.business.goldenfish.moneyTransfer.modeldmt.ModelRemitterReg
import com.business.goldenfish.moneyTransfer.modeldmt.ModelRemitterValidation
import com.business.goldenfish.moneyTransfer.respositorydmt.DmtRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class RemiterDataViewModel(private val remiterRepo : DmtRepository) : ViewModel() {

    init {


    }

    fun getdata(jsonObject: JsonObject)
    {
        viewModelScope.launch(Dispatchers.IO) {
            remiterRepo.getRemitterFromApi(jsonObject)
        }
    }

    fun getRegisterRemitter(jsonObject: JsonObject)
    {
        viewModelScope.launch(Dispatchers.IO) {
            remiterRepo.RegisterRemitterFromApi(jsonObject)
        }
    }

    fun getRemitterValidation(jsonObject: JsonObject)
    {
        viewModelScope.launch(Dispatchers.IO) {
            remiterRepo.ValidateRemitterFromApi(jsonObject)
        }
    }

    val data : LiveData<ModelRemitterDetails>
    get() =remiterRepo.finalRemitterData

    val ModelRemitterReg : LiveData<ModelRemitterReg>
    get() =remiterRepo.finalRemitterReg

    val ModelRemitterValidation : LiveData<ModelRemitterValidation>
    get() =remiterRepo.finalModelRemitterValidation
}