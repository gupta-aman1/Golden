package com.business.goldenfish.moneyTransfer.viewmodeldmt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.business.goldenfish.moneyTransfer.respositorydmt.DmtRepository

class RecipintListViewModelFactory(private val repo:DmtRepository) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipintListViewModel(repo) as T
    }
}