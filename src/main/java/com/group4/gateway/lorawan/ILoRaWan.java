package com.group4.gateway.lorawan;

import org.springframework.stereotype.Component;

import java.beans.PropertyChangeListener;
import java.net.http.WebSocket;
public interface ILoRaWan extends WebSocket.Listener {
    void sendMessage(String json);
    void addPropertyChangeListener(String name, PropertyChangeListener listener);
}