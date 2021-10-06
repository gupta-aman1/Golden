package com.example.goldenfish.MoveToBank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.goldenfish.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SuceessScreen extends AppCompatActivity {
RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_suceess_screen);
        list=findViewById(R.id.list);
        if(getIntent().getExtras()!=null)
        {
            ArrayList<DetailedData> listPrivate = new ArrayList<>();

            Type type = new TypeToken<List<DetailedData>>() {}.getType();
            listPrivate = new Gson().fromJson(getIntent().getStringExtra("list_data"), type);
            SucessAdapter adapter = new SucessAdapter(listPrivate, SuceessScreen.this);
            list.setLayoutManager(new LinearLayoutManager(SuceessScreen.this, RecyclerView.VERTICAL, false));
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();
        }



    }
}