package com.example.monitoreo;

import android.Manifest;
<<<<<<< HEAD
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
=======
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
<<<<<<< HEAD
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.monitoreo.services.GpsService;
import com.example.monitoreo.utils.NetworkUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private TextView tvBattery, tvNetwork, tvStorage, tvOsVersion, tvModel, tvCoordinates, tvLastUpdate, tvIpAddress;
    private Button btnToggle;
    private boolean isMonitoring = false;

    private final BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.monitoreo.LOCATION_UPDATE".equals(intent.getAction())) {
                double lat = intent.getDoubleExtra("latitude", 0.0);
                double lon = intent.getDoubleExtra("longitude", 0.0);
                long timestamp = intent.getLongExtra("timestamp", 0);

                runOnUiThread(() -> {
                    tvCoordinates.setText(String.format(Locale.getDefault(), "Coordenadas: %.6f, %.6f", lat, lon));
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    String formattedTime = sdf.format(new Date(timestamp));
                    tvLastUpdate.setText("Última actualización: " + formattedTime);
                });
            }
        }
    };
=======
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.monitoreo.api.ApiServer;
import com.example.monitoreo.services.GpsService;
import com.example.monitoreo.utils.NetworkUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus, tvIpAddress, tvBattery, tvNetwork, tvStorage, tvOsVersion, tvModel;
    private Button btnToggle;
    private boolean isMonitoring = false;
    private ApiServer server;

    private static final int PERMISSION_REQUEST_CODE = 123;
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        initializeViews();
        updateDeviceInfo();

        btnToggle.setOnClickListener(v -> toggleMonitoring());
    }

    private void initializeViews() {
=======
        tvStatus = findViewById(R.id.tvStatus);
        tvIpAddress = findViewById(R.id.tvIpAddress);
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
        tvBattery = findViewById(R.id.tvBattery);
        tvNetwork = findViewById(R.id.tvNetwork);
        tvStorage = findViewById(R.id.tvStorage);
        tvOsVersion = findViewById(R.id.tvOsVersion);
        tvModel = findViewById(R.id.tvModel);
<<<<<<< HEAD
        tvCoordinates = findViewById(R.id.tvCoordinates);
        tvLastUpdate = findViewById(R.id.tvLastUpdate);
        tvIpAddress = findViewById(R.id.tvIpAddress);
        btnToggle = findViewById(R.id.btnToggle);
    }

    private void toggleMonitoring() {
        if (!checkPermissions()) {
            requestPermissions();
            return;
        }

        if (isMonitoring) {
            stopMonitoring();
        } else {
            startMonitoring();
        }
    }

    private void startMonitoring() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startService(new Intent(this, GpsService.class));
            btnToggle.setText("Detener monitoreo");
            isMonitoring = true;
            Toast.makeText(this, "Monitoreo iniciado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Se necesitan permisos de ubicación", Toast.LENGTH_SHORT).show();
        }
=======
        btnToggle = findViewById(R.id.btnToggle);

        if (checkPermissions()) {
            startServerAndFillInfo();
        } else {
            requestPermissions();
        }

        btnToggle.setOnClickListener(v -> {
            if (isMonitoring) {
                stopMonitoring();
            } else {
                startMonitoring();
            }
        });
    }

    private void startMonitoring() {
        startService(new Intent(this, GpsService.class));
        tvStatus.setText("Estado: Recolección activa");
        btnToggle.setText("Detener monitoreo");
        isMonitoring = true;
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
    }

    private void stopMonitoring() {
        stopService(new Intent(this, GpsService.class));
<<<<<<< HEAD
        btnToggle.setText("Iniciar monitoreo");
        isMonitoring = false;
        Toast.makeText(this, "Monitoreo detenido", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    private void updateDeviceInfo() {
        try {
            tvIpAddress.setText("IP Local: " + NetworkUtils.getLocalIpAddress(this));

            BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
            int batteryLevel = bm != null ? bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) : -1;
            tvBattery.setText("Batería: " + batteryLevel + "%");

            tvNetwork.setText("Conectividad: " + getNetworkType());
            tvOsVersion.setText("Versión Android: " + Build.VERSION.RELEASE);
            tvModel.setText("Modelo del dispositivo: " + Build.MODEL);

            long available = getFilesDir().getFreeSpace() / (1024 * 1024);
            tvStorage.setText("Almacenamiento libre: " + available + " MB");
        } catch (Exception e) {
            Log.e(TAG, "Error al actualizar información del dispositivo", e);
        }
    }

    private String getNetworkType() {
        try {
            android.net.ConnectivityManager cm = (android.net.ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                android.net.NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    if (activeNetwork.getType() == android.net.ConnectivityManager.TYPE_WIFI) {
                        return "WiFi";
                    } else if (activeNetwork.getType() == android.net.ConnectivityManager.TYPE_MOBILE) {
                        return "Datos móviles";
                    }
                    return "Otro";
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al obtener tipo de red", e);
        }
        return "Sin conexión";
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso denegado - La funcionalidad de ubicación estará limitada", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("UnprotectedBroadcastReceiver")
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("com.example.monitoreo.LOCATION_UPDATE");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(locationReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(locationReceiver, filter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(locationReceiver);
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "Receptor no registrado");
=======
        tvStatus.setText("Estado: Recolección detenida");
        btnToggle.setText("Iniciar monitoreo");
        isMonitoring = false;
    }

    private void startServerAndFillInfo() {
        try {
            server = new ApiServer(this);
            server.start();

            String ip = NetworkUtils.getLocalIpAddress(this);
            if (ip == null || ip.isEmpty() || ip.equals("IP no disponible")) {
                tvIpAddress.setText("IP Local: No disponible");
            } else {
                tvIpAddress.setText("IP Local: http://" + ip + ":8080");
            }

        } catch (Exception e) {
            tvIpAddress.setText("Error iniciando servidor: " + e.getMessage());
        }

        fillDeviceStatus();
    }

    private void fillDeviceStatus() {
        tvBattery.setText("Batería: " + getBatteryLevel() + "%");
        tvNetwork.setText("Conectividad: " + getNetworkType());
        tvStorage.setText("Almacenamiento libre: " + getAvailableStorageMB() + " MB");
        tvOsVersion.setText("Versión Android: " + Build.VERSION.RELEASE);
        tvModel.setText("Modelo del dispositivo: " + Build.MODEL);
    }

    private int getBatteryLevel() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, filter);
        if (batteryStatus == null) return -1;

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        return (int) ((level / (float) scale) * 100);
    }

    private String getNetworkType() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return "Desconocida";

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnected()) return "No conectado";

        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
            return "Wi-Fi";
        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            return "Datos móviles";
        } else {
            return "Otro";
        }
    }

    private long getAvailableStorageMB() {
        File path = Environment.getDataDirectory();
        long freeBytes = path.getFreeSpace();
        return freeBytes / (1024 * 1024);
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            startServerAndFillInfo();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
        }
    }

    @Override
    protected void onDestroy() {
<<<<<<< HEAD
        super.onDestroy();
        if (isMonitoring) {
            stopService(new Intent(this, GpsService.class));
        }
=======
        if (server != null) {
            server.stop();
        }
        super.onDestroy();
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
    }
}
