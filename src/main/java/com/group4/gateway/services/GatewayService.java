package com.group4.gateway.services;

import com.google.gson.Gson;
import com.group4.gateway.models.MeasurementModel;
import com.group4.gateway.models.MeasurementToStore;
import com.group4.gateway.models.SensorSettingsModel;
import com.group4.gateway.lorawan.ILoRaWan;
import com.group4.gateway.utils.ApplicationProperties;
import com.group4.gateway.utils.EventTypes;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Component
public class GatewayService {
    @Autowired
    private ApplicationProperties applicationProperties;
    private ILoRaWan loRaWan;
    private Gson gson = new Gson();

    @Autowired
    public GatewayService(@Qualifier("LoRaWanImpl") ILoRaWan iLoRaWan) {
        loRaWan = iLoRaWan;


    }

    @PostConstruct
    private void initializeListeners() {
        loRaWan.addPropertyChangeListener(EventTypes.RECEIVE_LORA_DATA.toString(), this::onSensorDataReceivedEvent);
        for (int i = 0; i < 5; i++) {
            receiveConfiguration();
        }

    }

    private void onSensorDataReceivedEvent(PropertyChangeEvent event) {
        MeasurementModel measurementModel = null;

        try {
            measurementModel = gson.fromJson(event.getNewValue().toString(), MeasurementModel.class);
            if(measurementModel.port!=null&&measurementModel.port==2){
                convertMeasurement(measurementModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void convertMeasurement(MeasurementModel measurementModel) {


            MeasurementToStore measurementToStore = new MeasurementToStore();
            var senorId = parseMeasurementSensorId(measurementModel.port, measurementModel.data);
            double value = Integer.parseInt(measurementModel.data, 16);
       //    var s= new Date(measurementModel.ts);

            measurementToStore.dateTime=
            Instant.ofEpochMilli(measurementModel.ts)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime().toString();

            measurementToStore.value = (int)value;
            measurementToStore.sensorId = senorId;

        storeMeasurements(measurementToStore);


    }

    /**
     * Parse UNIX Timestamp to date(DD/MM/YYYY) and time(HH:MM:SS)
     *
     * @param ts timestamp
     * @return String array
     * pos[0] = date
     * pos[1] = time
     */
    private String[] parseUNIXTimestampToDateAndTime(Long ts) {
        if (ts == null) {
            ts = Calendar.getInstance().getTimeInMillis();
        }
        var dateAndTime = new String[2];
        Date tempDate = new Date(ts);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tempDate);
        String date = calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.YEAR);
        String time = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        dateAndTime[0] = date;
        dateAndTime[1] = time;
        return dateAndTime;
    }

    /**
     * Parse the {@data} parameter to the sensor id only if port number==2
     *
     * @param port uplink messages must come on port 2
     * @param data measurement data
     * @return sensor id
     * 1 = T1 (Temperature)
     * 2 = H1 (Humidity)
     * 3 = C1 (CO2)
     */
    private int parseMeasurementSensorId(Integer port, String data) {
        if (port != null && port == 2) {
            if (data.length() <= 4) {
                return 3;
            }
        } if(port != null &&port==3){
            receiveConfiguration();

        }        return -1;
    }

    private void storeMeasurements(MeasurementToStore measurementModel) {
        try {
            var measurementModelString = gson.toJson(measurementModel);
            StringEntity entity = new StringEntity(measurementModelString,
                    ContentType.APPLICATION_JSON);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(applicationProperties.getWebApiURL() + "api/farms/1/sensors/" + measurementModel.sensorId + "/measurements");
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);
            System.out.println("Store Measurements " + response.getStatusLine().getStatusCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveConfiguration() {
        var fetchedSettings = requestConfigurations();
        if (fetchedSettings == null) {
            return;
        }
        //todo DAN
        if (fetchedSettings.desiredValue == 0 && fetchedSettings.deviationValue == 0) {
        }

        String hex = String.format("%04X", (int) fetchedSettings.desiredValue) +
                String.format("%04X", (int) fetchedSettings.deviationValue);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "tx");
            jsonObject.put("EUI", applicationProperties.getEUI());
            jsonObject.put("port", 3);
            jsonObject.put("data", hex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(hex);
        loRaWan.sendMessage(jsonObject.toString());
    }

    private SensorSettingsModel requestConfigurations() {
        SensorSettingsModel fetchedSettings = null;
        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(applicationProperties.getWebApiURL() + "api/farms/1/sensors/1/settings");
            HttpResponse response = null;
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 204) {
                return new SensorSettingsModel(0, 0);
            }
            String responseBody = EntityUtils.toString(response.getEntity());

            fetchedSettings = gson.fromJson(responseBody, SensorSettingsModel.class);
            System.out.println("WEB API " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fetchedSettings;
    }
}
