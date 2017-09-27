package com.serhii.shutyi.controller.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class LabelManager {
    private static final Locale localeEnUs = new Locale("en", "US");
    private static final Locale localeRuRu = new Locale("ru", "RU");
    private static final ResourceBundle resourceBundleEnUs =
            ResourceBundle.getBundle("Labels", localeEnUs);
    private static final ResourceBundle resourceBundleRuRu =
            ResourceBundle.getBundle("Labels", localeRuRu);

    private static ResourceBundle resourceBundle = resourceBundleEnUs;

    private LabelManager(){}

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }

    public static void setLocale(String locale) {
        switch (locale) {
            case "en_US" : resourceBundle = resourceBundleEnUs;
            break;
            case "ru_RU" : resourceBundle = resourceBundleRuRu;
            break;
        }
    }
}
