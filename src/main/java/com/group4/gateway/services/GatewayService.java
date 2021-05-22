package com.group4.gateway.services;

import com.google.gson.Gson;
import com.group4.gateway.models.MeasurementModel;
import com.group4.gateway.repositories.lorawan.ILoRaWan;
import com.group4.gateway.models.ConfigModel;
import com.group4.gateway.utils.EventTypes;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.beans.PropertyChangeEvent;
import java.io.IOException;

@Component
public class GatewayService {
    private ILoRaWan loRaWan;
    RestTemplate restTemplate = new RestTemplate();
    Gson gson = new Gson();

    @Autowired
    public GatewayService(@Qualifier("LoRaWanImpl") ILoRaWan iLoRaWan) {
        loRaWan = iLoRaWan;


    }

@PostConstruct
    private void initializeListeners() {
        loRaWan.addPropertyChangeListener(EventTypes.RECEIVE_LORA_DATA.toString(), this::onSensorDataReceivedEvent);
    }

    private void onSensorDataReceivedEvent(PropertyChangeEvent event) {
        // TODO
        MeasurementModel measurementModel = null;

        try {
            measurementModel = gson.fromJson(event.getNewValue().toString(), MeasurementModel.class);
            storeMeasurements(measurementModel);
        } catch (Exception e) {

        }

    }

    private void storeMeasurements(MeasurementModel measurementModel) {
        try {
            var measurementModelString = gson.toJson(measurementModel);
            StringEntity entity = new StringEntity(measurementModelString,
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

    private void configurationReceivedEvent(ConfigModel configModel) {

        String hex = String.format("%04X", configModel.tempSetpoint) +
                String.format("%04X", configModel.co2Min) +
                String.format("%04X", configModel.co2Max);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "tx");
            jsonObject.put("EUI", configModel.eui);
            jsonObject.put("port", 2);
            jsonObject.put("data", hex);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loRaWan.sendMessage(jsonObject.toString());
    }
}
