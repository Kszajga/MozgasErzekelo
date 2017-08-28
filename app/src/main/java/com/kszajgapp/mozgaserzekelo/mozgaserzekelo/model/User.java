package com.kszajgapp.mozgaserzekelo.mozgaserzekelo.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Csiga on 2017. 08. 27..
 */

@IgnoreExtraProperties
public class User {

    public String user;
    public String email;
    public String notificationtoken;
    public Map<String, String> devices = new HashMap<>();

    public User() {
    }

    public User(String user, String email, String notificationtoken, Map<String, String> devices) {
        this.user = user;
        this.email = email;
        this.notificationtoken = notificationtoken;
        this.devices = devices;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("notificationtoken", notificationtoken);
        result.put("devices", devices);

        return result;
    }
}
