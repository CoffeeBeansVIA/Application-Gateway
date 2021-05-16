package com.group4.gateway.utils;

import com.google.gson.Gson;

public class JSON {
    public static <T> T toObject(String json, Class<T> type) {
        try {
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            System.out.println("[ERROR] When trying to convert json into object");
            return null;
        }
    }
}
