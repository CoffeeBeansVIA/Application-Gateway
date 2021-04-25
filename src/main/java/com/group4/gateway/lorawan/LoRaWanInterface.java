package com.group4.gateway.lorawan;

import java.beans.PropertyChangeListener;
import java.net.http.WebSocket;

public interface LoRaWanInterface extends WebSocket.Listener {
    void sendMessage(String json);
    void addPropertyChangeListener(String name, PropertyChangeListener listener);
}