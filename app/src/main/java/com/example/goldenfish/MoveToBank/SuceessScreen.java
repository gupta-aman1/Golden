package com.example.goldenfish.MoveToBank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.goldenfish.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SuceessScreen extends AppCompatActivity {
RecyclerView list;
TextView h2,h3,txn_id;
LottieAnimationView animation_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_suceess_screen);
        list=findViewById(R.id.list);
        h2=findViewById(R.id.head2);
        h3=findViewById(R.id.head3);
        txn_id=findViewById(R.id.txn_id);
        animation_view=findViewById(R.id.animation_view);
        if(getIntent().getExtras()!=null)
        {

           String Head1= getIntent().getStringExtra("Head1");
            String Head2= getIntent().getStringExtra("Head2");
            String Head3= getIntent().getStringExtra("Head3");
            String type= getIntent().getStringExtra("type");

            setTitle(Head1);
            h2.setText(Head2);
            h3.setText(Head3);

            if(type.equalsIgnoreCase("success"))
            {
                animation_view.setAnimation("sucess_json.json");
                h2.setTextColor(ContextCompat.getColor(this, R.color.txt_green));
            }
            else
            {
                animation_view.setAnimation("fail_txn.json");
                h2.setTextColor(ContextCompat.getColor(this, R.color.txt_red));
            }

            ArrayList<DetailedData> listPrivate = new ArrayList<>();
            Type t1 = new TypeToken<List<DetailedData>>() {}.getType();
            listPrivate = new Gson().fromJson(getIntent().getStringExtra("list_data"), t1);
            SucessAdapter adapter = new SucessAdapter(listPrivate, SuceessScreen.this);
            list.setLayoutManager(new LinearLayoutManager(SuceessScreen.this, RecyclerView.VERTICAL, false));
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            for (int i=0; i<listPrivate.size();i++)
            {
                if(listPrivate.get(i).getKey().equalsIgnoreCase("OrderId"))
                {
                    txn_id.setText("Transaction Number : "+listPrivate.get(i).getValue());
                }
            }
        }
        else
        {
            Toast.makeText(this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();
        }



    }
}