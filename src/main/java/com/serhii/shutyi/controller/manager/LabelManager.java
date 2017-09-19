package com.serhii.shutyi.controller.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class LabelManager {
    public static final ResourceBundle resourceBundle =
            ResourceBundle.getBundle("Labels", new Locale("en", "US"));

    private LabelManager(){}

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
