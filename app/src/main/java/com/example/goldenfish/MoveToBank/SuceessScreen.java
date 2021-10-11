package com.example.goldenfish.MoveToBank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.goldenfish.Common.CommonFun;
import com.example.goldenfish.Common.CommonInterface;
import com.example.goldenfish.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SuceessScreen extends AppCompatActivity implements CommonInterface {
RecyclerView list;
TextView h2,h3,txn_id;
LottieAnimationView animation_view;
ImageView shareReceipt;
NestedScrollView scroll;
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
        shareReceipt=findViewById(R.id.shareReceipt);
        scroll=findViewById(R.id.scroll);
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

    public void share(View view) {

        CommonFun.requestStoragePermission(SuceessScreen.this,SuceessScreen.this);

    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
        shareReceipt.setVisibility(View.GONE);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    private void savetoGallery(Bitmap bitmap) {
      //  done.setVisibility(View.VISIBLE);
        shareReceipt.setVisibility(View.VISIBLE);
        String root = Environment.getExternalStorageDirectory().getPath() + "/Golden/";
        File myDir = new File(root);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fname = "Transaction" + System.currentTimeMillis() + ".jpg";
        File myfile = new File(myDir, fname);

        try {
            FileOutputStream out = new FileOutputStream(myfile);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Log.e("jjh", String.valueOf(myfile));
        MediaScannerConnection.scanFile(this, new String[]{myfile.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        //Log.i("ExternalStorage", "Scanned " + path + ":");
                        //Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

        try {

            ContentValues contentValues = new ContentValues();

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, String.valueOf(System.currentTimeMillis()), null);
            Uri imageUri = Uri.parse(path);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, "Share link!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestPermission(boolean status) {
        if(status)
        {
            shareReceipt.setVisibility(View.GONE);
            Bitmap bitmap = getBitmapFromView(scroll, scroll.getChildAt(0).getHeight(), scroll.getChildAt(0).getWidth());
            savetoGallery(bitmap);
        }
        else
        {
            CommonFun.showSettingsDialog(SuceessScreen.this);
        }
    }
}