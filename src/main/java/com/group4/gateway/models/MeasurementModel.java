package com.group4.gateway.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeasurementModel {
    @SerializedName("rssi")
    @Expose
    public Integer rssi;

    @SerializedName("seqno")
    @Expose
    public Integer seqno;

    @SerializedName("data")
    @Expose
    public String data;

    @SerializedName("toa")
    @Expose
    public Integer toa;

    @SerializedName("freq")
    @Expose
    public Long freq;

    @SerializedName("ack")
    @Expose
    public Boolean ack;

    @SerializedName("fcnt")
    @Expose
    public Integer fcnt;

    @SerializedName("dr")
    @Expose
    public String dr;

    @SerializedName("offline")
    @Expose
    public Boolean offline;

    @SerializedName("bat")
    @Expose
    public Integer bat;

    @SerializedName("port")
    @Expose
    public Integer port;

    @SerializedName("snr")
    @Expose
    public Integer snr;

    @SerializedName("EUI")
    @Expose
    public String EUI;

    @SerializedName("cmd")
    @Expose
    public String cmd;

    @SerializedName("ts")
    @Expose
    public Long ts;
}
