package com.serhii.shutyi.controller.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class LabelManager {
    private static final Locale localeEnUs;
    private static final Locale localeRuRu;
    private static final ResourceBundle resourceBundleEnUs;
    private static final ResourceBundle resourceBundleRuRu;

    private static ResourceBundle resourceBundle;

    static {
        localeEnUs = new Locale("en", "US");
        localeRuRu = new Locale("ru", "RU");
        resourceBundleEnUs = ResourceBundle.getBundle("Labels", localeEnUs);
        resourceBundleRuRu = ResourceBundle.getBundle("Labels", localeRuRu);
        resourceBundle = resourceBundleEnUs;
    }

    private LabelManager(){}

    public static String getProperty(String key, String locale){
        setLocale(locale);
        return resourceBundle.getString(key);
    }

    private static void setLocale(String locale) {
        switch (locale) {
            case "en_US" : resourceBundle = resourceBundleEnUs;
            break;
            case "ru_RU" : resourceBundle = resourceBundleRuRu;
            break;
        }
    }
}
