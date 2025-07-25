package com.example.monitoreo.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;

<<<<<<< HEAD

=======
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
public class NetworkUtils {

    public static String getLocalIpAddress(Context context) {
        String wifiIp = getWifiIp(context);
        if (wifiIp != null) {
            return wifiIp;
        }

        // Si no está conectado por WiFi, prueba Ethernet o datos
        return getMobileOrEthernetIp();
    }

    private static String getWifiIp(Context context) {
        try {
            WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wm != null && wm.isWifiEnabled()) {
                int ipAddress = wm.getConnectionInfo().getIpAddress();
                return Formatter.formatIpAddress(ipAddress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getMobileOrEthernetIp() {
        try {
            for (NetworkInterface intf : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (!intf.isUp() || intf.isLoopback()) continue;

                for (InetAddress addr : Collections.list(intf.getInetAddresses())) {
                    if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "IP no disponible";
    }
}
