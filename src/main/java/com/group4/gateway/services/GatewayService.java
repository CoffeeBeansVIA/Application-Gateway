package com.group4.gateway.services;

import com.group4.gateway.lorawan.ILoRaWan;
import com.group4.gateway.models.ConfigModel;
import com.group4.gateway.models.TeracomModel;
import com.group4.gateway.utils.EventTypes;
import com.group4.gateway.utils.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
        try {
            String payload = "{\"time\": \"2\", \"date\": \"2\", \"value\": 111, \"sensorId\": 3}";
            StringEntity entity = new StringEntity(payload,
                    ContentType.APPLICATION_JSON);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://localhost:5000/api/sensors/3/measurements");
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configurationReceivedEvent(PropertyChangeEvent event) {
        ConfigModel config = (ConfigModel) event.getNewValue();

        String hex = String.format("%04X", config.tempSetpoint) +
                String.format("%04X", config.co2Min) +
                String.format("%04X", config.co2Max);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "tx");
            jsonObject.put("EUI", config.eui);
            jsonObject.put("port", 2);
            jsonObject.put("data", hex);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loRaWan.sendMessage(jsonObject.toString());
    }
}
