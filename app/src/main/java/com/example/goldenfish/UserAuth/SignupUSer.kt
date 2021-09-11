package com.example.goldenfish.UserAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.goldenfish.R
import kotlinx.android.synthetic.main.activity_signup_user.*

class SignupUSer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_user)

       // var arr:ArrayList<String>
        val gender_list = arrayListOf<String>()
        gender_list.add("Select Gender")
        gender_list.add("Male")
        gender_list.add("Female")
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, gender_list)
        et_gender.adapter = adapter
    }

    fun ClickLogin(view: View) {

        startActivity(Intent(this,LoginActivity::class.java))
    }
}