package com.group4.gateway.mock;

import com.group4.gateway.WebService.WebServiceInterface;

import java.beans.PropertyChangeListener;
import java.sql.Timestamp;

public class WebServiceMock implements WebServiceInterface {
    @Override
    public void insert(String deviceEUI, int hum, int temp, int co2, int servo, Timestamp time) {
        System.out.printf("DB INSERT: eui: %s\thum: %d\ttemp: %d\tco2: %d\tservo:%d\ttimestamp: %s\n",
                deviceEUI,
                hum,
                temp,
                co2,
                servo,
                time);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {

    }
}
