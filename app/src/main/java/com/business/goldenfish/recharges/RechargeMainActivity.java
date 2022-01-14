package com.business.goldenfish.recharges;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.goldenfish.Constants.Constant;
import com.business.goldenfish.Constants.ConstantsValue;
import com.business.goldenfish.R;

public class RechargeMainActivity extends AppCompatActivity {
    TextView activity_title,tv_aeps_balance,tv_main_balance,text_head;
    ImageView back_button,partners_banner;
    String service_name, service_id,MainBal, AepsBal;
    EditText et_recharge_number;
    LinearLayout powered_layout;
    View seperator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_main);
        activity_title=findViewById(R.id.activity_title);
        back_button=findViewById(R.id.back_button);
        et_recharge_number=findViewById(R.id.et_recharge_number);
        tv_aeps_balance=findViewById(R.id.tv_aeps_balance);
        tv_main_balance=findViewById(R.id.tv_main_balance);
        text_head=findViewById(R.id.text_head);
        powered_layout=findViewById(R.id.powered_layout);
        seperator=findViewById(R.id.view3);
        partners_banner=findViewById(R.id.partners_banner);
        if(getIntent().getExtras()!=null)
        {
          service_name=  getIntent().getStringExtra(Constant.service_name);
          service_id=  getIntent().getStringExtra(Constant.service_id);
          MainBal=  getIntent().getStringExtra(Constant.MainBal);
          AepsBal=  getIntent().getStringExtra(Constant.AepsBal);
            activity_title.setText(service_name);
            tv_main_balance.setText("₹ " +MainBal);
            tv_aeps_balance.setText("₹ " +AepsBal);
            text_head.setText(service_name+ " Recharge");

            if(service_name.equalsIgnoreCase(ConstantsValue.MobilePrepaid) || service_name.equalsIgnoreCase(ConstantsValue.MobilePostPaid))
            {
                et_recharge_number.setHint("Mobile Number");
            }
            else if(service_name.equalsIgnoreCase(ConstantsValue.DTH))
            {
                et_recharge_number.setHint("Customer ID/VC Number/Subscriber ID");
                seperator.setVisibility(View.VISIBLE);
                et_recharge_number.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dth_small, 0);
                partners_banner.setImageDrawable(ContextCompat.getDrawable(RechargeMainActivity.this,R.drawable.dth_list));
            }
        }
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void ProceedToRecharge(View view) {
        if(et_recharge_number.getText().toString().trim().equalsIgnoreCase("")|| et_recharge_number.getText().length() !=10)
        {
            et_recharge_number.setError("Enter Number");
        }
        else
        {
            startActivity(new Intent(RechargeMainActivity.this,SelectOperatorActivity.class)
            .putExtra(Constant.service_name,service_name)
            .putExtra(Constant.service_id,service_id)
            .putExtra("number",et_recharge_number.getText().toString().trim())
            );
        }
    }
}