package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Csiga on 2017. 06. 08..
 */
@IgnoreExtraProperties
public class Sensor {
    public boolean PIRSensor;
    public String subscribe;

    public Sensor(){}

    public Sensor(boolean PIRSensor, String subscribe){
        this.PIRSensor = PIRSensor;
        this.subscribe = subscribe;
    }
}
