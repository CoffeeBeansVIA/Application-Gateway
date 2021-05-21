package com.group4.gateway.repositories.lorawan;

import com.google.gson.Gson;
import com.group4.gateway.models.MeasurementModel;
import com.group4.gateway.utils.ApplicationProperties;
import com.group4.gateway.utils.EventTypes;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.*;

public class LoRaWan implements ILoRaWan {
    @Autowired
    private ApplicationProperties applicationProperties;

    private final Gson g = new Gson();
    private final PropertyChangeSupport support;
    private final ScheduledExecutorService executorService;
    private WebSocket server;
    private String data = "";

    @PostConstruct
    public void init() {
        new LoRaWan();
    }

    public LoRaWan() {
        support = new PropertyChangeSupport(this);
        executorService = Executors.newScheduledThreadPool(1);
        createConnection();
    }

    private void createConnection() {
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
                //todo change uri
                .buildAsync(URI.create(applicationProperties.getLoRaWanURL()), this);
        server = ws.join();
        try {
            onOpen(ws.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    // Send down-link message to device
    // Must be in Json format according to https://github.com/ihavn/IoT_Semester_project/blob/master/LORA_NETWORK_SERVER.md
    @Override
    public void sendMessage(String json) {
        server.sendText(json, true);

        System.out.println("sendDownLink executed");
    }

    //onOpen()
    public void onOpen(WebSocket webSocket) {
        // This WebSocket will invoke onText, onBinary, onPing, onPong or onClose methods on the associated listener (i.e. receive methods) up to n more times
        webSocket.request(1);

        System.out.println("WebSocket Listener has been opened for requests.");
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", "rx");
            json.put("EUI", "0004A30B0021B92F");
            json.put("ts", 1621165894);
            json.put("ack", false);
            json.put("port", 2);
            json.put("fcnt", 1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //keep connection alive
        executorService.scheduleAtFixedRate(() -> {
//                    String data = "Ping";
//                    ByteBuffer payload = ByteBuffer.wrap(data.getBytes());
//                    server.sendPing(payload);
                    sendMessage(json.toString());
                },
                1, 1, TimeUnit.SECONDS);
    }

    //onError()
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("A " + error.getCause() + " exception was thrown.");
        System.out.println("Message: " + error.getLocalizedMessage());
        webSocket.abort();
    }

    //onClose()
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed!");
        System.out.println("Status:" + statusCode + " Reason: " + reason);
        return new CompletableFuture().completedFuture("onClose() completed.").thenAccept(System.out::println);
    }

    //onPing()
    public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Ping: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Ping completed.").thenAccept(System.out::println);
    }

    //onPong()
    public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Pong: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Pong completed.").thenAccept(System.out::println);
    }

    //onText()
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        try {
             this.data +=  (new JSONObject(data.toString())).toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // As sequence received can be send on multiple occasions, method collects until boolean last is true, then proceeds with the data.
        if (last) {
            support.firePropertyChange(EventTypes.RECEIVE_LORA_DATA.toString(), "", this.data);
            this.data = "";
        }
        webSocket.request(1);

        return CompletableFuture.completedFuture("onText() completed.");

//        System.out.println("onText()");
//        String indented = null;
//
//        try {
//            indented = (new JSONObject(data.toString())).toString(4);
//            MeasurementModel measurementModel = g.fromJson(indented, MeasurementModel.class);
//        } catch (JSONException e) {
//            System.out.println("onText error");
//            e.printStackTrace();
//        }
//        System.out.println(indented);
//
//        webSocket.request(1);
        // return new CompletableFuture().completedFuture("onText() completed.").thenAccept(System.out::println);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        if (name == null) {
            support.addPropertyChangeListener(listener);
        } else {
            support.addPropertyChangeListener(name, listener);
        }
    }
}
