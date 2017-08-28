package com.kszajgapp.mozgaserzekelo.mozgaserzekelo.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Csiga on 2017. 06. 08..
 */

public class Sensor {
    /*private String sensorName;
    private String sensorValue;
    private List<Log> sensorLogList;

    public Sensor() {
    }

    public Sensor(String sensorName, String sensorValue, List<Log> sensorLogList) {
        this.sensorName = sensorName;
        this.sensorValue = sensorValue;
        this.sensorLogList = sensorLogList;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
    }

    public List<Log> getSensorLogList() {
        return sensorLogList;
    }

    public void setSensorLogList(List<Log> sensorLogList) {
        this.sensorLogList = sensorLogList;
    }*/

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