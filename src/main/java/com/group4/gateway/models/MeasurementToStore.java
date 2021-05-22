package com.group4.gateway.models;

import com.google.gson.annotations.SerializedName;

public class MeasurementToStore {
    @SerializedName("time")
    public String time;
    @SerializedName("date")
    public String date;
    @SerializedName("value")
    public double value;
    @SerializedName("sensorId")
    public int sensorId;
}
