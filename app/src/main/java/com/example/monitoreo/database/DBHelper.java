package com.example.monitoreo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // ───────────── BASE DE DATOS ─────────────
    public static final String DB_NAME = "monitor.db";
    public static final int DB_VERSION = 1;

    // ───────────── TABLA SENSOR_DATA ─────────────
    public static final String TABLE_SENSOR_DATA = "sensor_data";
    public static final String COL_ID = "_id";
    public static final String COL_DEVICE_ID = "device_id";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_TIMESTAMP = "timestamp";

    // ───────────── TABLA CREDENTIALS ─────────────
    public static final String TABLE_CREDENTIALS = "credentials";
    public static final String COL_TOKEN = "token";

    private final Context context;

    public Context getContext() {
        return context;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de datos del sensor
        String createSensorDataTable = "CREATE TABLE IF NOT EXISTS " + TABLE_SENSOR_DATA + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DEVICE_ID + " TEXT NOT NULL, " +
                COL_LATITUDE + " REAL NOT NULL, " +
                COL_LONGITUDE + " REAL NOT NULL, " +
                COL_TIMESTAMP + " TEXT NOT NULL" +
                ")";
        db.execSQL(createSensorDataTable);

        // Crear tabla de credenciales
        String createCredentialsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_CREDENTIALS + " (" +
                COL_TOKEN + " TEXT PRIMARY KEY" +
                ")";
        db.execSQL(createCredentialsTable);

        // Insertar un token por defecto
        ContentValues cv = new ContentValues();
        cv.put(COL_TOKEN, "123456TOKEN");
        db.insert(TABLE_CREDENTIALS, null, cv);

        // Insertar un dato GPS de prueba
        ContentValues values = new ContentValues();
        values.put(COL_DEVICE_ID, "DEVICE001");
        values.put(COL_LATITUDE, -0.180653);
        values.put(COL_LONGITUDE, -78.467834);
        values.put(COL_TIMESTAMP, "2025-07-20T19:00:00");
        db.insert(TABLE_SENSOR_DATA, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDENTIALS);
        onCreate(db);
    }
}
