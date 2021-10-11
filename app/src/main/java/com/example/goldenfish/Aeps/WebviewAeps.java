package com.example.goldenfish.Aeps;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.goldenfish.R;

public class WebviewAeps extends AppCompatActivity {
    WebView finalpaymentWebView;
    ProgressDialog progressDialog;
    String loadUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_aeps);
        finalpaymentWebView = findViewById(R.id.finalpaymentWebView);

        if(getIntent().getExtras()!=null)
        {
            loadUrl= getIntent().getStringExtra("url");
            System.out.println("Webview url11 "+loadUrl);

        }
        try {
            finalpaymentWebView.getSettings().setLoadsImagesAutomatically(true);
            finalpaymentWebView.getSettings().setJavaScriptEnabled(true);
            WebSettings webSettings = finalpaymentWebView.getSettings();
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            finalpaymentWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            //finalpaymentWebView.loadUrl(loadUrl);
            progressDialog = new ProgressDialog(WebviewAeps.this, R.style.MyTheme);
            progressDialog.setTitle("Loading....");
            progressDialog.setMessage("Please wait while preparing..");
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
            progressDialog.setCancelable(false);
            progressDialog.show();
            finalpaymentWebView.setWebViewClient(new MyWebViewClient(this));

            //WebSettings webSettings = finalpaymentWebView.getSettings();
            //webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

           /* finalpaymentWebView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    view.loadUrl(url);
                    return true;
                }
            });*/
            //webSettings.setJavaScriptEnabled(true);
            webSettings.setGeolocationEnabled(true);

            finalpaymentWebView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onGeolocationPermissionsShowPrompt(String origin,
                                                               GeolocationPermissions.Callback callback) {

                    callback.invoke(origin, true, false);
                }
            });

            finalpaymentWebView.loadUrl(loadUrl);
        }
        catch (Exception e)
        {
            System.out.println("Webview url1 "+e.getMessage());

            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    class MyWebViewClient extends WebViewClient {
        private Context context;

        public MyWebViewClient(Context context) {
            this.context = context;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //  System.out.println("PAYMENT PAGE "+url);
            super.onPageFinished(view, url);
            try {
                progressDialog.dismiss();

            }catch (Exception e){

            }
        }
    }
}