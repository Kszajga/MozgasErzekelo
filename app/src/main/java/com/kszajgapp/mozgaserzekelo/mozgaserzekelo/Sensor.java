package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Csiga on 2017. 06. 08..
 */
@IgnoreExtraProperties
public class Sensor {
    public boolean PIRSensor;

    public Sensor(){}

    public Sensor(boolean PIRSensor){
        this.PIRSensor = PIRSensor;
    }
}
