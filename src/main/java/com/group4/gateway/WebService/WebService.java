package com.group4.gateway.WebService;

import java.beans.PropertyChangeListener;
import java.sql.Timestamp;

public class WebService implements WebServiceInterface {
    @Override
    public void insert(String deviceEUI, int hum, int temp, int co2, int servo, Timestamp time) {

    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {

    }
}
