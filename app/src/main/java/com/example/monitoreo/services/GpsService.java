package com.example.monitoreo.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class GpsService extends Service {

    private static final int NOTIFICATION_ID = 1001;
    private static final String CHANNEL_ID = "gps_channel";
    private static final String CHANNEL_NAME = "Monitoreo GPS";

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    public void onCreate() {
        super.onCreate();
        startForegroundServiceNotification();
        startLocationUpdates();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
<<<<<<< HEAD
        startLocationUpdates();
=======
        startLocationUpdates(); // asegura que se llame al iniciar el servicio
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startForegroundServiceNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Monitoreo activado")
                .setContentText("Recolectando ubicación del dispositivo")
                .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                .setOngoing(true)
                .build();

        startForeground(NOTIFICATION_ID, notification);
    }

    private void startLocationUpdates() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                long timestamp = System.currentTimeMillis();

<<<<<<< HEAD
                // Log para depuración (puedes eliminarlo en producción)
                Log.d("GpsService", "Ubicación actualizada: lat=" + lat + " lon=" + lon + " ts=" + timestamp);

                // Enviar la intención a MainActivity
=======
                // Enviar ubicación a MainActivity
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
                Intent intent = new Intent("com.example.monitoreo.LOCATION_UPDATE");
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                intent.putExtra("timestamp", timestamp);
                sendBroadcast(intent);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
<<<<<<< HEAD
            Log.w("GpsService", "Permisos de ubicación no concedidos");
=======
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
<<<<<<< HEAD
                30000, // cada 30 segundos
                5,     // o cada 5 metros de cambio
=======
                3000, // 3 segundos
                5,    // 5 metros
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
                locationListener
        );
    }
}
