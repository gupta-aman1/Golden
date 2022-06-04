package com.business.goldenfish.moneyTransfer.viewmodeldmt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.business.goldenfish.moneyTransfer.respositorydmt.DmtRepository

class RemiterViewModelFactory(private val repo :DmtRepository,private val s:String) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(s.equals("RemiterDataViewModel"))
        {
            return RemiterDataViewModel(repo) as T
        }
        else if(s.equals("RecipintListViewModel"))
        {
            return RecipintListViewModel(repo) as T
        }
        return RemiterDataViewModel(repo) as T   ;
    }

}