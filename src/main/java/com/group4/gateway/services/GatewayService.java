package com.group4.gateway.services;

import com.group4.gateway.lorawan.ILoRaWan;
import com.group4.gateway.models.TeracomModel;
import com.group4.gateway.utils.EventTypes;
import com.group4.gateway.utils.JSON;

import java.beans.PropertyChangeEvent;
import java.sql.Timestamp;
import java.time.Instant;

public class GatewayService {
    private final ILoRaWan loRaWan;

    public GatewayService(ILoRaWan iLoRaWan) {
        loRaWan = iLoRaWan;
    }

    private void initializeSubscription() {
        loRaWan.addPropertyChangeListener(EventTypes.RECEIVE_LORA_DATA.toString(), this::onSensorDataReceivedEvent);
    }

    private void onSensorDataReceivedEvent(PropertyChangeEvent event) {
        String json = (String) event.getNewValue();

        var model = JSON.toObject(json, TeracomModel.class);

        if (model != null) {
            // What is model cmd ?
            if (model.cmd.equals("rx")) {
                storeMeasurements(model.eUI, model.data, Timestamp.from(Instant.now()));
            }
        }
    }

    private void storeMeasurements(String deviceId, String hexString, Timestamp timestamp) {
        // TODO call the web api and store the retrieved data there
    }
}
