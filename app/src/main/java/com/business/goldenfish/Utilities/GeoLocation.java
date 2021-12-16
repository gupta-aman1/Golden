package com.business.goldenfish.Utilities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.business.goldenfish.BuildConfig;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoLocation extends AppCompatActivity {
    private Context mContext;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    private static final int REQUEST_CHECK_SETTINGS = 100;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates=false;
    private  OnDataReceiverListener onDataReceiveListener;

    public GeoLocation(Context context, OnDataReceiverListener _listener)
    {
        this.mContext = context;
        onDataReceiveListener = _listener;
        init();
    }

    private void init() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
            mSettingsClient = LocationServices.getSettingsClient(mContext);
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            mLocationSettingsRequest = builder.build();

            try {
                mLocationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        // location is received
                        mCurrentLocation = locationResult.getLastLocation();
                        // mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                        updateLocationUI();

                    }
                };
            }
            catch (Exception e)
            {
                Toast.makeText(mContext, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e)
        {
            Toast.makeText(mContext, "10 "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //  mRequestingLocationUpdates = false;
    }

    public void updateLocationUI() {
        try {
            if (mCurrentLocation != null) {
                // System.out.println("CHECKSTATUS "+"nullVAlue");
           /* txtLocationResult.setText(
                    "Lat: " + mCurrentLocation.getLatitude() + ", " +
                            "Lng: " + mCurrentLocation.getLongitude());*/

                //System.out.println("CHEKDATA"+mCurrentLocation);
                // giving a blink animation on TextView
                // txtLocationResult.setAlpha(0);
                //  txtLocationResult.animate().alpha(1).setDuration(300);

                //System.out.println("CHECKSTATUS "+"NOT NULL");
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(mContext, Locale.getDefault());
                try {
                    // addresses1 = geocoder.getFromLocation(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),1);
                    addresses = geocoder.getFromLocation(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String pincode = addresses.get(0).getPostalCode();
                    String subadmin = addresses.get(0).getSubAdminArea();
                    String knownName = addresses.get(0).getFeatureName();
                    try {
                        onDataReceiveListener.onDataReceived(pincode,mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),address+" "+subadmin+ " "+knownName+" ,"+city);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(mContext, "6 "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    //  txtLocationResult.setAlpha(0);
                    // txtLocationResult.animate().alpha(1).setDuration(300);

               /* txtLocationResult.setText("Latitude -"+String.valueOf(mCurrentLocation.getLatitude())+"\n\n"
                        +"Longitutde -"+String.valueOf(mCurrentLocation.getLatitude())+"\n\n"
                        +"Altitude - "+String.valueOf(mCurrentLocation.getAltitude())+"\n\n"
                        +"Location Time -"+DateFormat.getTimeInstance().format(new Date())+"\n\n"
                        +"Country -"+country+"\n\n"
                        +"State -"+state+"\n\n"
                        +"City -"+city+"\n\n"
                        +"Full Address -"+address+"\n\n"
                        +"Postal Code -"+postalCode+"\n\n"
                        +"Sub Admin Area -"+subadmin+"\n\n"
                        +"Known Name -"+knownName+"\n\n"
                        +"DEVICE NAME -"+ Build.MANUFACTURER+"\n\n"
                        +"DEVICE MODEL -"+Build.MODEL);*/
                } catch (IOException e) {

                    Toast.makeText(mContext, "8 "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(mContext, "9 "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

      //  System.out.println("CHEKDATA"+mCurrentLocation);
    }

    private void startLocationUpdates() {
        try {
            mSettingsClient
                    .checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener((Activity) mContext, new OnSuccessListener<LocationSettingsResponse>() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                            //Log.i("GEOLOCATE", "All location settings are satisfied.");

                            //  Toast.makeText(mContext, "Started location updates!", Toast.LENGTH_SHORT).show();

                            //noinspection MissingPermission
                            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                    mLocationCallback, mContext.getMainLooper());

                            updateLocationUI();
                        }
                    })
                    .addOnFailureListener((Activity) mContext, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            int statusCode = ((ApiException) e).getStatusCode();
                            switch (statusCode) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    //Log.i("GEOLOCATE", "Location settings are not satisfied. Attempting to upgrade " +
//                                            "location settings ")/
                                    try {
                                        // Show the dialog by calling startResolutionForResult(), and check the
                                        // result in onActivityResult().
                                        ResolvableApiException rae = (ResolvableApiException) e;
                                        rae.startResolutionForResult((Activity) mContext, REQUEST_CHECK_SETTINGS);
                                    } catch (IntentSender.SendIntentException sie) {
                                        //Log.i("GEOLOCATE", "PendingIntent unable to execute request.");
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    String errorMessage = "Location settings are inadequate, and cannot be " +
                                            "fixed here. Fix in Settings.";
                                    ////Log.e(TAG, errorMessage);

                                    Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
                            }

                            updateLocationUI();
                        }
                    });
        }
        catch (Exception e)
        {
            Toast.makeText(mContext, "14 "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        try {
            Dexter.withActivity((Activity) mContext)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            //     Toast.makeText(MainActivity.this,"location",Toast.LENGTH_LONG).show();
                            mRequestingLocationUpdates = true;

                            startLocationUpdates();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if (response.isPermanentlyDenied()) {
                                // open device settings when the permission is
                                // denied permanently


                                openSettings();

                                //showSettingsDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        }
       catch (Exception e)
       {
           Toast.makeText(mContext, "12 "+e.getMessage(), Toast.LENGTH_SHORT).show();
       }



    }


    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      //   Toast.makeText(mContext, "Location updates stopped!", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                         ////Log.e("GEOLOCATE", "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                         ////Log.e("GEOLOCATE", "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }




  /*  @Override
    protected void onResume() {
        super.onResume();
        startLocationButtonClick();
    }
    @Override
    protected void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();

        }
    }*/

    private void openSettings() {
try {
    Intent intent = new Intent();
    intent.setAction(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    Uri uri = Uri.fromParts("package",
            BuildConfig.APPLICATION_ID, null);
    intent.setData(uri);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mContext.startActivity(intent);
}
catch (Exception e)
{
    Toast.makeText(mContext, "3 "+e.getMessage(), Toast.LENGTH_SHORT).show();
}




    }


}
