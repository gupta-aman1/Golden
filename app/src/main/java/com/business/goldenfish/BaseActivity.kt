package com.business.goldenfish

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity :AppCompatActivity(){
    protected var mProgressDialog: ProgressDialog? = null

    fun showProgressDialog(message: String) {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.setMessage(message.toString())
        mProgressDialog?.show()
        mProgressDialog?.setCancelable(false)
        //   mProgressDialog = ProgressDialog.show(this,message.toString())
    }

    protected fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
    }
}