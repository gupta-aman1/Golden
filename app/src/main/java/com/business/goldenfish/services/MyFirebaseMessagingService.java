package com.business.goldenfish.services;

/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static com.business.goldenfish.Retrofit.RetrofitClient.IMAGE_URL;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.business.goldenfish.AddFund.AddFundActivity;
import com.business.goldenfish.Constants.Constant;
import com.business.goldenfish.Constants.ConstantsValue;
import com.business.goldenfish.Dashboard.HomeDashboardActivity;
import com.business.goldenfish.MoveToBank.MoveToBankActivity;
import com.business.goldenfish.PanCard.PsaRegistrationActivity;
import com.business.goldenfish.PanCard.PurchaseCouponActivity;
import com.business.goldenfish.PayoutAc.AddPayoutAcc;
import com.business.goldenfish.R;
import com.business.goldenfish.Retrofit.RetrofitClient;
import com.business.goldenfish.UserAuth.LoginActivity;
import com.business.goldenfish.Utilities.MyUtils;
import com.business.goldenfish.Utilities.SharedPref;
import com.business.goldenfish.ledgerreopt.LedgerReportActivity;
import com.business.goldenfish.recharges.RechargeMainActivity;
import com.business.goldenfish.sidebar.allReports.GetServices;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * NOTE: There can only be one service in each app that receives FCM messages. If multiple
 * are declared in the Manifest then the first one will be chosen.
 *
 * In order to make this Java sample functional, you must remove the following from the Kotlin messaging
 * service in the AndroidManifest.xml:
 *
 * <intent-filter>
 *   <action android:name="com.google.firebase.MESSAGING_EVENT" />
 * </intent-filter>
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Intent intent;
    int BUDGE_COUNT;
    String myURL;
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        SharedPref sharedPref = SharedPref.getInstance(this);
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
              //  scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        try {
            Map data = remoteMessage.getData();
            //   System.out.println("remoteMessage == " + data);
            String body;
            JSONObject jsonObject = new JSONObject(remoteMessage.getData());
            String title = jsonObject.optString("title");
            body = jsonObject.optString("body");
            String target = jsonObject.optString("Target");
            String imge = jsonObject.optString("Img");
//            String notificationType = jsonObject.optString("N");

            String date = new SimpleDateFormat("dd MMM yyyy").format(new Date());

            if (target.equalsIgnoreCase("Custom URL")) {
                String[] datearr11 = body.split("__");
                body = (datearr11[0]);
                myURL = datearr11[1];
            }

            // Log.e("mj5", "Message Notification Body: " + target);
            String userid = sharedPref.getString(Constant.userId, "");
            String mobile = sharedPref.getString(Constant.MobileNo1, "");
            String mallname = sharedPref.getString(Constant.Username, "");
            String MainBal = sharedPref.getString(Constant.MainBal, "");
            String AepsBal = sharedPref.getString(Constant.AepsBal, "");

            if (!mobile.equals("") && !userid.equals("") && !mallname.equals("")) {
                switch (target) {

                    case "PANPurchaseNoti":
                       // checkUtiStatus(name, this, title, body, imge);
                        startCustomActivity(HomeDashboardActivity.class, this, title, body, imge);
                        break;
//                    startCustomActivity(maindrawer.class, this, title, body, imge);
                    case "PANRegNoti":
                        startCustomActivity(HomeDashboardActivity.class, this, title, body, imge);
                        break;

                    case "MobilePrepaidNoti":
                        startCustomActivityWithIntentData(RechargeMainActivity.class,
                                this,
                                4,
                                new String[]{Constant.service_name, Constant.service_id,Constant.MainBal,Constant.AepsBal},
                                new String[]{ConstantsValue.MobilePrepaid,ConstantsValue.MobilePrepaidId,MainBal,AepsBal}, title, body, imge);
                        break;
                    case "MobilePostPaidNoti":
                        startCustomActivityWithIntentData(RechargeMainActivity.class,
                                this,
                                4,
                                new String[]{Constant.service_name, Constant.service_id,Constant.MainBal,Constant.AepsBal},
                                new String[]{ConstantsValue.MobilePostPaid,ConstantsValue.MobilePostPaidId,MainBal,AepsBal}, title, body, imge);
                        break;
                    case "DTHRechargeNoti":
                        startCustomActivityWithIntentData(RechargeMainActivity.class,
                                this,
                                4,
                                new String[]{Constant.service_name, Constant.service_id,Constant.MainBal,Constant.AepsBal},
                                new String[]{ConstantsValue.DTH,ConstantsValue.DTHId,MainBal,AepsBal}, title, body, imge);
                        break;

                    case "MoveToBankNoti":
                        startCustomActivity(MoveToBankActivity.class, this, title, body, imge);
                        break;


                    case "AadhaarAtmNoti":
                        startCustomActivity(HomeDashboardActivity.class, this, title, body, imge);
                        break;

                    case "AddPayoutNoti":
                        startCustomActivity(AddPayoutAcc.class, this, title, body, imge);
                        break;

                    case "StatementNoti":
                        startCustomActivity(LedgerReportActivity.class, this, title, body, imge);
                        break;

                    case "AddFundNoti":
                        startCustomActivity(AddFundActivity.class, this, title, body, imge);
                        break;

                    case "AllReportNoti":
                        startCustomActivity(GetServices.class, this, title, body, imge);
                        break;

                    case "Custom URL":
                        intent = new Intent("android.intent.action.VIEW", Uri.parse(myURL));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        sendNotification(intent, title, body, imge);
                        break;
                }
            } else {
                startCustomActivity(LoginActivity.class, this, title, body, imge);
            }


        } catch (Exception e) {

        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]
    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        SharedPrefHelper sharedPrefHelper = new SharedPrefHelper(MyFirebaseMessagingService.this);
        sharedPrefHelper.setString("token", token);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
  /*  private void scheduleJob() {
        // [START dispatch_job]
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance(this).beginWith(work).enqueue();
        // [END dispatch_job]
    }*/

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any
     * server-side account maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
   /* private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */
    //, intent,
              /*  PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */
    //,
            //notificationBuilder.build());
    //}*/


    public void startCustomActivityWithIntentData(Class<?> otherActivityClass, Context context, int intentArraySize,
                                                  String[] arrayKey, String[] arrayValue, String title, String body, String image) {
        if (arrayKey.length == arrayValue.length) {
            Intent intent = new Intent(context, otherActivityClass);
            for (int i = 0; i < intentArraySize; i++) {
                intent.putExtra(arrayKey[i], arrayValue[i]);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            sendNotification(intent, title, body, image);
//            startActivity(intent);
        } else {
            Toast.makeText(context, "array for intent is not equal", Toast.LENGTH_SHORT).show();
        }


    }

    private void sendNotification(Intent intent, String title, String body, String image) {

        NotificationCompat.BigPictureStyle bpStyle = null;

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);
        String channelId = "1";

        if (!image.equalsIgnoreCase("No")) {

            String Img = IMAGE_URL + image;
            Bitmap remote_picture = null;
            try {
                URL url = new URL(Img);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream in = connection.getInputStream();
                remote_picture = BitmapFactory.decodeStream(in);
            } catch (IOException e) {

            }
            bpStyle = new NotificationCompat.BigPictureStyle();
            bpStyle.bigPicture(remote_picture)
                    .bigLargeIcon(null).build();
        }

        // Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       /* Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.redmilgrp);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), sound);
        r.play();*/
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(MyFirebaseMessagingService.this, channelId)
                        .setContentTitle(title)
                        .setContentText(Html.fromHtml(body))
                        .setAutoCancel(true)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                       // .setSound(sound)
                        .setContentIntent(pendingIntent)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setPriority(Notification.PRIORITY_MAX).setStyle(bpStyle)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        // MediaPlayer ring = MediaPlayer.create(MyFirebaseMessagingService.this, R.raw.redmilgrp); ring.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            notificationBuilder.setSmallIcon(R.drawable.logo_new);
            notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
            notificationBuilder.setColor(Color.argb(0, 51, 138, 208));
            notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationBuilder.setLights(Color.RED, 3000, 3000);
            notificationBuilder.setNumber(7);

        } else {
            notificationBuilder.setSmallIcon(R.drawable.logo_new);
            notificationBuilder.setColor(Color.argb(0, 51, 138, 208));
            notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationBuilder.setLights(Color.RED, 3000, 3000);
            notificationBuilder.setNumber(7);

        }
        NotificationManager notificationManager =
                (NotificationManager) MyFirebaseMessagingService.this.getSystemService(Context.NOTIFICATION_SERVICE);

// Since android Oreo notification channel is needed.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(true);
            channel.canShowBadge();


            notificationManager.createNotificationChannel(channel);
        }
        int num = (int) System.currentTimeMillis();
        notificationManager.notify(num, notificationBuilder.build());
        //notificationManager.notify(Integer.parseInt(String.valueOf(System.currentTimeMillis())), notificationBuilder.build());
//        setNotification();
    }

    public void startCustomActivity(Class<?> otherActivityClass, Context context, String title, String body, String image) {
        Intent intent = new Intent(context, otherActivityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sendNotification(intent, title, body, image);
//        startActivity(intent);
    }



}