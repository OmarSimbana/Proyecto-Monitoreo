package com.example.monitoreo.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;

public class DeviceStatusService {

    public JSONObject getStatus(Context context) {
        JSONObject status = new JSONObject();

        try {
            // Nivel de batería
            BatteryManager bm = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            int batteryLevel = (bm != null) ? bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) : -1;
            status.put("battery_level", batteryLevel);

            // Estado de red
            boolean isConnected = false;
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Network network = cm.getActiveNetwork();
                    NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
                    isConnected = capabilities != null &&
                            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
                } else {
                    // Código antiguo por compatibilidad
                    isConnected = cm.getActiveNetworkInfo() != null &&
                            cm.getActiveNetworkInfo().isConnected();
                }
            }

            status.put("network_connected", isConnected);

            // Espacio de almacenamiento disponible
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            long availableBytes = availableBlocks * blockSize;
            status.put("storage_available", availableBytes);

        } catch (Exception e) {
            Log.e("DeviceStatusService", "Error obteniendo estado del dispositivo", e);
        }

        return status;
    }
}
