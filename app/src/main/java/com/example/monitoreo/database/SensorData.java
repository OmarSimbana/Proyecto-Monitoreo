package com.example.monitoreo.database;

import java.io.Serializable;

/**
 * Clase que representa los datos del sensor.
 */
public class SensorData implements Serializable {

    private String deviceId;
    private double latitude;
    private double longitude;
    private String timestamp;

    // Constructor vacío (opcional)
    public SensorData() {
    }

    /**
     * Constructor completo.
     *
     * @param deviceId  ID del dispositivo
     * @param latitude  Latitud
     * @param longitude Longitud
     * @param timestamp Marca de tiempo
     */
    public SensorData(String deviceId, double latitude, double longitude, String timestamp) {
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    // Getters
    public String getDeviceId() {
        return deviceId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    // Para logs y depuración
    @Override
    public String toString() {
        return "SensorData{" +
                "deviceId='" + deviceId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
