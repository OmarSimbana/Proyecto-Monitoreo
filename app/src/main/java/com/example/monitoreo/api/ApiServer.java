package com.example.monitoreo.api;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.monitoreo.database.DBHelper;
import com.example.monitoreo.services.DeviceStatusService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class ApiServer extends NanoHTTPD {

    private final DBHelper dbHelper;
    private final Context context;

    public ApiServer(Context context) {
        super("0.0.0.0", 8080); // Escuchar en todas las interfaces
        this.context = context.getApplicationContext();
        this.dbHelper = new DBHelper(this.context);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> headers = session.getHeaders();

        // 🔧 Normalizar URI: eliminar barra final si existe
        if (uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }

        // 🔒 Verificación de token
        String authHeader = getAuthorizationHeader(headers);
        if (!isAuthorized(authHeader)) {
            return newFixedLengthResponse(Response.Status.UNAUTHORIZED, "text/plain", "Unauthorized");
        }

        // 🚦 Rutas
        switch (uri) {
            case "/api/sensor_data":
                return getSensorData(session.getParms());

            case "/api/device_status":
                return getDeviceStatus();

            default:
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not Found");
        }
    }

    // ✅ Leer Authorization sin importar capitalización
    private String getAuthorizationHeader(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if ("authorization".equalsIgnoreCase(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    // ✅ Validar token contra tabla credentials
    private boolean isAuthorized(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return false;
        String token = authHeader.substring(7); // quitar "Bearer "

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT token FROM credentials WHERE token = ?", new String[]{token});
        boolean valid = cursor.moveToFirst();
        cursor.close();
        return valid;
    }

    // ✅ Obtener datos del sensor entre dos timestamps
    private Response getSensorData(Map<String, String> params) {
        String startTime = params.get("start_time");
        String endTime = params.get("end_time");

        if (startTime == null || endTime == null) {
            return newFixedLengthResponse(Response.Status.BAD_REQUEST, "text/plain", "Missing start_time or end_time");
        }

        JSONArray result = new JSONArray();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT device_id, latitude, longitude, timestamp FROM sensor_data WHERE timestamp BETWEEN ? AND ?",
                new String[]{startTime, endTime}
        );

        int idxDeviceId = cursor.getColumnIndex("device_id");
        int idxLatitude = cursor.getColumnIndex("latitude");
        int idxLongitude = cursor.getColumnIndex("longitude");
        int idxTimestamp = cursor.getColumnIndex("timestamp");

        while (cursor.moveToNext()) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("device_id", cursor.getString(idxDeviceId));
                obj.put("latitude", cursor.getDouble(idxLatitude));
                obj.put("longitude", cursor.getDouble(idxLongitude));
                obj.put("timestamp", cursor.getString(idxTimestamp));
                result.put(obj);
            } catch (Exception ignored) {
            }
        }

        cursor.close();
        return newFixedLengthResponse(Response.Status.OK, "application/json", result.toString());
    }

    // ✅ Obtener estado del dispositivo (batería, red, etc.)
    private Response getDeviceStatus() {
        JSONObject obj = new DeviceStatusService().getStatus(context);
        return newFixedLengthResponse(Response.Status.OK, "application/json", obj.toString());
    }
}
