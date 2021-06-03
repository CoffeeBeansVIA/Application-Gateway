package com.group4.gateway.models;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class MeasurementToStore {

    @SerializedName("sensorId")
    public int sensorId;
    @SerializedName("time")
    public String dateTime;
    @SerializedName("value")
    public int value;

}
