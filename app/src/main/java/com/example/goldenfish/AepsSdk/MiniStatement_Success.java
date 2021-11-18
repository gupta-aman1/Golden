package com.example.goldenfish.AepsSdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goldenfish.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MiniStatement_Success extends AppCompatActivity {

    String id,
            txn_type,
            bc_id,
            bank_name,
            op_name,
            order_id,
            txn_id,
            amount,
            comm,
            mobile,
            aadhaar,
            ministmt,
            reason,
            status,
            req_date,
            acc_bal,head1txt, head2txt, head3txt;

TextView bcNameTT,
         order_id_txt,
         txnId_TT,
         stypeTT,
         aadhaarTT,
         bankTT,
         mobileTT,
         availableBalTT,
         head2,
         head3,
         failedReasonTT,
         datetimeTT,
        comm_txt;
    ImageView success_or_fail_IMG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_statement__success);

        order_id_txt = findViewById(R.id.order_id_txt);
        txnId_TT = findViewById(R.id.txnId_TT);
        stypeTT = findViewById(R.id.stypeTT);
        aadhaarTT = findViewById(R.id.aadhaarTT);
        bankTT = findViewById(R.id.bankTT);
        mobileTT = findViewById(R.id.mobileTT);
        availableBalTT = findViewById(R.id.availableBalTT);
        comm_txt = findViewById(R.id.comm_txt);
        head2 = findViewById(R.id.head2);
        head3 = findViewById(R.id.head3);
        failedReasonTT = findViewById(R.id.failedReasonTT);
        datetimeTT=findViewById(R.id.datetimeTT);
        success_or_fail_IMG=findViewById(R.id.success_or_fail_IMG);
        bcNameTT=findViewById(R.id.bcNameTT);


        if (getIntent() != null) {
            String status_code = getIntent().getStringExtra("status_code");
            head1txt = getIntent().getStringExtra("head1");
            head2txt = getIntent().getStringExtra("head2");
            head3txt = getIntent().getStringExtra("head3");
            id = getIntent().getStringExtra("id");
            txn_type = getIntent().getStringExtra("txn_type");
            bc_id = getIntent().getStringExtra("bc_id");
            bank_name = getIntent().getStringExtra("bank_name");
            op_name = getIntent().getStringExtra("op_name");
            order_id = getIntent().getStringExtra("order_id");
            txn_id = getIntent().getStringExtra("txn_id");
            amount = getIntent().getStringExtra("amount");
            comm = getIntent().getStringExtra("comm");
            mobile = getIntent().getStringExtra("mobile");
            aadhaar = getIntent().getStringExtra("aadhaar");
            ministmt = getIntent().getStringExtra("ministmt");
            reason = getIntent().getStringExtra("reason");
            status = getIntent().getStringExtra("status");
            req_date = getIntent().getStringExtra("req_date");
            acc_bal = getIntent().getStringExtra("acc_bal");

            head2.setText(head2txt);
            head3.setText(head3txt);
            order_id_txt.setText(order_id);
            txnId_TT.setText(txn_id);
            aadhaarTT.setText(aadhaar);
            bankTT.setText(bank_name);
            mobileTT.setText(mobile);
            availableBalTT.setText("₹ "+acc_bal);
            comm_txt.setText("₹ "+comm);
            failedReasonTT.setText("Reason : "+reason);

            if(txn_type.equalsIgnoreCase("SAP"))
            {
                stypeTT.setText("Mini Statement");
            }

            try {
                SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                // SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a"); //here 'a' for AM/PM
                SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

                Date date = null;
                try {
                    date = sourceFormat.parse(req_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formattedDate = destFormat.format(date);
                datetimeTT.setText(formattedDate.replace("am", "AM").replace("pm","PM"));
            }
            catch (Exception e)
            {

            }

            if(status_code.equalsIgnoreCase("ERR"))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    success_or_fail_IMG.setImageDrawable(getResources().getDrawable(R.drawable.bg_failed_ic, getApplicationContext().getTheme()));
                } else {
                    success_or_fail_IMG.setImageDrawable(getResources().getDrawable(R.drawable.bg_failed_ic));
                }
            }
        }
        System.out.println("MINI STMT "+ministmt);
    }
}