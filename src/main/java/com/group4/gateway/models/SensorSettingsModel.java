package com.group4.gateway.models;

import com.google.gson.annotations.SerializedName;

public class SensorSettingsModel {
    @SerializedName("desiredValue")
    public double desiredValue;
    @SerializedName("deviationValue")
    public double deviationValue;

    public SensorSettingsModel(double desiredValue, double deviationValue) {
        this.desiredValue = desiredValue;
        this.deviationValue = deviationValue;
    }


}
