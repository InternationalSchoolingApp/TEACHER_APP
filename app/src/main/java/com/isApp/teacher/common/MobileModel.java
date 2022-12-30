package com.isApp.teacher.common;

import android.os.Build;

public class MobileModel {
    public String getDeviceName(){
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String buildId = Build.ID;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())){
            return capitalize(model);
        }
        else {
            return capitalize(manufacturer) + " " + model + " " + buildId;
        }
    }
    private String capitalize(String value){
        if (value == null || value.length()==0){
            return "";
        }
        char first = value.charAt(0);
        if(Character.isUpperCase(first)){
            return value;
        }else{
            return Character.toUpperCase(first)+value.substring(1);
        }
    }
}
