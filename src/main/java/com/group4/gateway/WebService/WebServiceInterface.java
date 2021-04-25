package com.group4.gateway.WebService;

import java.beans.PropertyChangeListener;

public interface WebServiceInterface {
    void insert(String deviceEUI, int hum, int temp, int co2, int servo, Timestamp time);
    void addPropertyChangeListener(String name, PropertyChangeListener listener);
}
