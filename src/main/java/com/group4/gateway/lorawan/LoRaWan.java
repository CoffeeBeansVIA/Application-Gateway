package com.group4.gateway.lorawan;

import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.*;

public class LoRaWan implements ILoRaWan {
    private final PropertyChangeSupport support;
    private final ScheduledExecutorService executorService;
    private WebSocket server = null;
    private final String URL = "wss://iotnet.cibicom.dk/app?token=vnoTwwAAABFpb3RuZXQuY2liaWNvbS5ka8Jer376b_vS6G62ZSL3pMU=";
    private final String TOKEN = "0004A30B0021B92F";

    public LoRaWan() {
        support = new PropertyChangeSupport(this);
        executorService = Executors.newScheduledThreadPool(1);

        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
                .buildAsync(URI.create(URL), this);

        server = ws.join();
    }

    // Send down-link message to device
    // Must be in Json format according to https://github.com/ihavn/IoT_Semester_project/blob/master/LORA_NETWORK_SERVER.md
    @Override
    public void sendMessage(String json) {
        server.sendText(json,true);
        System.out.println("sendDownLink executed");
    }

    //onOpen()
    public void onOpen(WebSocket webSocket) {
        // This WebSocket will invoke onText, onBinary, onPing, onPong or onClose methods on the associated listener (i.e. receive methods) up to n more times
        webSocket.request(1);

        //keep connection alive
        executorService.scheduleAtFixedRate(() -> {
                    String data = "Ping";
                    ByteBuffer payload = ByteBuffer.wrap(data.getBytes());
                    server.sendPing(payload);
                },
                1, 1, TimeUnit.SECONDS);

        System.out.println("WebSocket Listener has been opened for requests.");
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
        String indented = null;
        try {
            indented = (new JSONObject(data.toString())).toString(4);
        } catch (JSONException e) {
            System.out.println("onText error");
            e.printStackTrace();
        }
        System.out.println(indented);
        webSocket.request(1);
        return new CompletableFuture().completedFuture("onText() completed.").thenAccept(System.out::println);
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
