package com.group4.gateway.mock;

import com.group4.gateway.lorawan.LoRaWanInterface;

import java.beans.PropertyChangeListener;
import java.net.http.WebSocket;
import java.util.ArrayDeque;

public class LoraWanMock implements WebSocket.Listener, Runnable, LoRaWanInterface {
    private final ArrayDeque<String> queue;
    private final static long DELAY_MIN = 5000L; //24000L

    public LoraWanMock(String url) {
        queue = new ArrayDeque<>();
        Thread t = new Thread(this::run);
        t.start();
    }

    @Override
    public void run() {
        System.out.println("WebSocket Listener has been opened for requests.");
        queue.add(getCacheResponse());
        while (true) {
            try {
                queue.add(getSingleResponse());
                Thread.sleep(DELAY_MIN);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessage(String json) {

    }

    private String getSingleResponse() {
        return
                "{\n" +
                        "    \"gws\": [\n" +
                        "        {\n" +
                        "            \"rssi\": -102,\n" +
                        "            \"snr\": -12,\n" +
                        "            \"ant\": 0,\n" +
                        "            \"tmms\": 50000,\n" +
                        "            \"gweui\": \"7076FFFFFF019BCE\",\n" +
                        "            \"lon\": 9.623305999999957,\n" +
                        "            \"time\": \"2020-11-16T08:28:38.322826961Z\",\n" +
                        "            \"lat\": 55.809815,\n" +
                        "            \"ts\": 1605515319457\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"rssi\": -102,\n" +
                        "            \"snr\": -12,\n" +
                        "            \"ant\": 1,\n" +
                        "            \"tmms\": 50000,\n" +
                        "            \"gweui\": \"7076FFFFFF019BCE\",\n" +
                        "            \"lon\": 9.623305999999957,\n" +
                        "            \"time\": \"2020-11-16T08:28:38.322826961Z\",\n" +
                        "            \"lat\": 55.809815,\n" +
                        "            \"ts\": 1605515319457\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"rssi\": -115,\n" +
                        "            \"snr\": -19,\n" +
                        "            \"ant\": 0,\n" +
                        "            \"tmms\": 50000,\n" +
                        "            \"gweui\": \"7076FFFFFF019DE0\",\n" +
                        "            \"lon\": 9.886778,\n" +
                        "            \"time\": \"2020-11-16T08:28:39.322777045Z\",\n" +
                        "            \"lat\": 55.870655,\n" +
                        "            \"ts\": 1605515319661\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"seqno\": 16,\n" +
                        "    \"data\": \"00340012028c\",\n" +
                        "    \"toa\": 0,\n" +
                        "    \"freq\": 868100000,\n" +
                        "    \"ack\": false,\n" +
                        "    \"fcnt\": 1,\n" +
                        "    \"dr\": \"SF12 BW125 4/5\",\n" +
                        "    \"bat\": 255,\n" +
                        "    \"port\": 2,\n" +
                        "    \"EUI\": \"0004A30B00219CAC\",\n" +
                        "    \"cmd\": \"gw\",\n" +
                        "    \"ts\": 1605515319457\n" +
                        "}";
    }

    private String getCacheResponse() {
        return "{\"cmd\":\"cq\",\"page\":1,\"perPage\":10,\"total\":155,\"cache\":[{\"cmd\":\"gw\",\"seqno\":500,\"EUI\":\"0004A30B00219CAC\",\"ts\":1605859148274,\"fcnt\":162,\"port\":2,\"freq\":867300000,\"toa\":0,\"dr\":\"SF12 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-111,\"snr\":-1,\"ts\":1605859148288,\"tmms\":50000,\"time\":\"2020-11-20T07:59:07.169364976Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-112,\"snr\":-17,\"ts\":1605859148288,\"tmms\":50000,\"time\":\"2020-11-20T07:59:07.169364976Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":1,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-114,\"snr\":-19,\"ts\":1605859148274,\"tmms\":50000,\"time\":\"2020-11-20T07:59:07.169369073Z\",\"gweui\":\"7076FFFFFF019F64\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-114,\"snr\":-24,\"ts\":1605859148274,\"tmms\":50000,\"time\":\"2020-11-20T07:59:07.169369073Z\",\"gweui\":\"7076FFFFFF019F64\",\"ant\":1,\"lat\":55.809815,\"lon\":9.623305999999957}],\"sessionKeyId\":null,\"bat\":255,\"data\":\"0034001203d7\"},{\"cmd\":\"gw\",\"seqno\":499,\"EUI\":\"0004A30B00219CAC\",\"ts\":1605858536431,\"fcnt\":160,\"port\":2,\"freq\":868500000,\"toa\":0,\"dr\":\"SF11 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-110,\"snr\":-11.2,\"ts\":1605858536431,\"tmms\":50000,\"time\":\"2020-11-20T07:48:55.283810959Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-116,\"snr\":-17,\"ts\":1605858536506,\"tmms\":50000,\"time\":\"2020-11-20T07:48:56.283766047Z\",\"gweui\":\"7076FFFFFF019DE0\",\"ant\":0,\"lat\":55.870655,\"lon\":9.886778}],\"sessionKeyId\":null,\"bat\":255,\"data\":\"00340012033d\"},{\"cmd\":\"gw\",\"seqno\":498,\"EUI\":\"0004A30B00219CAC\",\"ts\":1605858230210,\"fcnt\":159,\"port\":2,\"freq\":867900000,\"toa\":0,\"dr\":\"SF11 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-112,\"snr\":-3,\"ts\":1605858230252,\"tmms\":50000,\"time\":\"2020-11-20T07:43:50.120322999Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-114,\"snr\":-17,\"ts\":1605858230252,\"tmms\":50000,\"time\":\"2020-11-20T07:43:50.120322999Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":1,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-115,\"snr\":-19,\"ts\":1605858230210,\"tmms\":50000,\"time\":\"2020-11-20T07:43:50.120319196Z\",\"gweui\":\"7076FFFFFF019F64\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-115,\"snr\":-19,\"ts\":1605858230210,\"tmms\":50000,\"time\":\"2020-11-20T07:43:50.120319196Z\",\"gweui\":\"7076FFFFFF019F64\",\"ant\":1,\"lat\":55.809815,\"lon\":9.623305999999957}],\"sessionKeyId\":null,\"bat\":255,\"data\":\"003400120311\"},{\"cmd\":\"gw\",\"seqno\":497,\"EUI\":\"0004A30B00219CAC\",\"ts\":1605857924023,\"fcnt\":158,\"port\":2,\"freq\":868300000,\"toa\":0,\"dr\":\"SF11 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-97,\"snr\":-17,\"ts\":1605857924023,\"tmms\":50000,\"time\":\"2020-11-20T07:38:43.935432941Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-116,\"snr\":-12,\"ts\":1605857924154,\"tmms\":50000,\"time\":\"2020-11-20T07:38:43.935376117Z\",\"gweui\":\"7076FFFFFF019DE0\",\"ant\":0,\"lat\":55.870655,\"lon\":9.886778}],\"sessionKeyId\":null,\"bat\":255,\"data\":\"0034001202e2\"},{\"cmd\":\"gw\",\"seqno\":496,\"EUI\":\"0004A30B00219CAC\",\"ts\":1605857617888,\"fcnt\":157,\"port\":2,\"freq\":868100000,\"toa\":0,\"dr\":\"SF11 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-102,\"snr\":-12,\"ts\":1605857617888,\"tmms\":50000,\"time\":\"2020-11-20T07:33:37.802287714Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-115,\"snr\":-14,\"ts\":1605857618140,\"tmms\":50000,\"time\":\"2020-11-20T07:33:37.802242137Z\",\"gweui\":\"7076FFFFFF019DE0\",\"ant\":0,\"lat\":55.870655,\"lon\":9.886778}],\"sessionKeyId\":null,\"bat\":255,\"data\":\"0034001202f6\"},{\"cmd\":\"gw\",\"seqno\":495,\"EUI\":\"0004A30B00219CAC\",\"ts\":1605857005637,\"fcnt\":155,\"port\":2,\"freq\":868500000,\"toa\":0,\"dr\":\"SF11 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-99,\"snr\":-16,\"ts\":1605857005637,\"tmms\":50000,\"time\":\"2020-11-20T07:23:24.496428910Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-116,\"snr\":-15,\"ts\":1605857005756,\"tmms\":50000,\"time\":\"2020-11-20T07:23:25.496372066Z\",\"gweui\":\"7076FFFFFF019DE0\",\"ant\":0,\"lat\":55.870655,\"lon\":9.886778}],\"sessionKeyId\":null,\"bat\":255,\"data\":\"003400120328\"},{\"cmd\":\"gw\",\"seqno\":494,\"EUI\":\"0004A30B00219CAC\",\"ts\":1605856699474,\"fcnt\":154,\"port\":2,\"freq\":868300000,\"toa\":0,\"dr\":\"SF11 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-99,\"snr\":-17,\"ts\":1605856699474,\"tmms\":50000,\"time\":\"2020-11-20T07:18:18.370451941Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-116,\"snr\":-15,\"ts\":1605856699564,\"tmms\":50000,\"time\":\"2020-11-20T07:18:19.370398066Z\",\"gweui\":\"7076FFFFFF019DE0\",\"ant\":0,\"lat\":55.870655,\"lon\":9.886778}],\"sessionKeyId\":null,\"bat\":255,\"data\":\"0034001202b9\"},{\"cmd\":\"gw\",\"seqno\":493,\"EUI\":\"0004A30B00219CAC\",\"ts\":1605856393480,\"fcnt\":153,\"port\":2,\"freq\":868500000,\"toa\":0,\"dr\":\"SF11 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-116,\"snr\":-20,\"ts\":1605856393480,\"tmms\":50000,\"time\":\"2020-11-20T07:13:13.228095036Z\",\"gweui\":\"7076FFFFFF019DE0\",\"ant\":0,\"lat\":55.870655,\"lon\":9.886778}],\"sessionKeyId\":null,\"bat\":255,\"data\":\"003400120294\"},{\"cmd\":\"gw\",\"seqno\":492,\"EUI\":\"0004A30B00219CAC\",\"ts\":1605855781123,\"fcnt\":151,\"port\":2,\"freq\":868500000,\"toa\":0,\"dr\":\"SF11 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-98,\"snr\":-17,\"ts\":1605855781123,\"tmms\":50000,\"time\":\"2020-11-20T07:02:59.979431003Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-116,\"snr\":-17,\"ts\":1605855781251,\"tmms\":50000,\"time\":\"2020-11-20T07:03:00.979377122Z\",\"gweui\":\"7076FFFFFF019DE0\",\"ant\":0,\"lat\":55.870655,\"lon\":9.886778}],\"sessionKeyId\":null,\"bat\":255,\"data\":\"00340012029a\"},{\"cmd\":\"gw\",\"seqno\":491,\"EUI\":\"0004A30B00219CAC\",\"ts\":1605855474932,\"fcnt\":150,\"port\":2,\"freq\":867700000,\"toa\":0,\"dr\":\"SF11 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-110,\"snr\":-4,\"ts\":1605855474949,\"tmms\":50000,\"time\":\"2020-11-20T06:57:54.811510000Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-111,\"snr\":-20,\"ts\":1605855474949,\"tmms\":50000,\"time\":\"2020-11-20T06:57:54.811510000Z\",\"gweui\":\"7076FFFFFF019BCE\",\"ant\":1,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-114,\"snr\":-19,\"ts\":1605855474932,\"tmms\":50000,\"time\":\"2020-11-20T06:57:54.811563851Z\",\"gweui\":\"7076FFFFFF019BF7\",\"ant\":0,\"lat\":56.098958,\"lon\":10.21659909999994},{\"rssi\":-115,\"snr\":-17,\"ts\":1605855474934,\"tmms\":50000,\"time\":\"2020-11-20T06:57:54.811509361Z\",\"gweui\":\"7076FFFFFF019F64\",\"ant\":0,\"lat\":55.809815,\"lon\":9.623305999999957},{\"rssi\":-115,\"snr\":-18,\"ts\":1605855474934,\"tmms\":50000,\"time\":\"2020-11-20T06:57:54.811509361Z\",\"gweui\":\"7076FFFFFF019F64\",\"ant\":1,\"lat\":55.809815,\"lon\":9.623305999999957}],\"sessionKeyId\":null,\"bat\":255,\"data\":\"0034001202a0\"}]}";
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {

    }
}
