package com.business.goldenfish.AddFund.ModelAddFund

data class ModelAddFund(var Statuscode:String,var Message:String, var Data:List<ModelAccountList>, var UserBankDetails:List<ModelUserAccountList>){
}

data class ModelAccountList(var BankName:String, var AccountName:String,var AccountNo:String,var IFSCode:String)
{

}
data class ModelUserAccountList(var AccountHolderName:String, var AccountNumber:String,var IFSCCode:String,var AccountType:String,var bankname:String)
{

}

data class ModelAddFundCommon(var Statuscode:String,var Message:String){
}