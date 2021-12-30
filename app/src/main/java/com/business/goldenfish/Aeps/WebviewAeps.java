package com.business.goldenfish.Aeps;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
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

import com.business.goldenfish.Constants.ConstantsValue;
import com.business.goldenfish.Dashboard.HomeDashboardActivity;
import com.business.goldenfish.R;

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
           // System.out.println("Webview url111 "+loadUrl);
            HomeDashboardActivity bal = new HomeDashboardActivity();
            bal.recivedSms(ConstantsValue.CallApiBal);
        }
        try {
            finalpaymentWebView.getSettings().setLoadsImagesAutomatically(true);
            finalpaymentWebView.getSettings().setJavaScriptEnabled(true);
            WebSettings webSettings = finalpaymentWebView.getSettings();
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            finalpaymentWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
           // finalpaymentWebView.getSettings().setPluginState(true);
           // finalpaymentWebView.getSettings().setAllowFileAccess(true);
           // webSettings.setDomStorageEnabled(true);
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

                /*@Override
                public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                    return super.onJsAlert(view, url, message, result);
                }*/
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
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
          //  System.out.println("LOADING "+url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //handler.proceed(); // Ignore SSL certificate errors

            final AlertDialog.Builder builder = new AlertDialog.Builder(WebviewAeps.this);
            String message = "SSL Certificate error.";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = "The certificate is not yet valid.";
                    break;
            }
            message += " Do you want to continue anyway?";

            builder.setTitle("SSL Certificate Error");
            builder.setMessage(message);
            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
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