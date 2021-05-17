package com.group4.gateway.repositories.webService;

import java.beans.PropertyChangeListener;
import java.sql.Timestamp;

public interface WebServiceInterface {
    void insert(String deviceEUI, int hum, int temp, int co2, int servo, Timestamp time);
    void addPropertyChangeListener(String name, PropertyChangeListener listener);
}
