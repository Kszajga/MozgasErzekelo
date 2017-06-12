package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Csiga on 2017. 06. 08..
 */
@IgnoreExtraProperties
public class Sensor {
    public boolean PIRSensor;
    public String subscribe;
    public Map<String, String> logs = new HashMap<>();


    public Sensor() {
    }

    public Sensor(boolean PIRSensor, String subscribe, Map<String, String> logs) {
        this.PIRSensor = PIRSensor;
        this.subscribe = subscribe;
        this.logs = logs;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("logs", logs);
        return result;
    }
}