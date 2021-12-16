package com.business.goldenfish.Utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

public class GpsListener extends BroadcastReceiver {

   GpsInterface gpsInterface = null;
    private Context context;
    public GpsListener(){}

    public GpsListener(Context ctx, GpsInterface gpsInterface){
        this.gpsInterface = gpsInterface;
        this.context = ctx;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            try {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    gpsInterface.onGpsStatusChanged(true);
                }else{
                    gpsInterface.onGpsStatusChanged(false);
                }
            }
            catch (Exception e)
            {
                Toast.makeText(context, "7 "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

    }
}