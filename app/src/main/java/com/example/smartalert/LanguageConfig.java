package com.example.smartalert;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LanguageConfig {
    public static boolean localeGr = false;
    public static ContextWrapper changeLanguage(Context context,String languageCode){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale systemLocale;
        systemLocale = configuration.getLocales().get(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            systemLocale = configuration.getLocales().get(0);
        }else{
            //systemLocale = configuration.locale;
            systemLocale = configuration.locale;
        }
        if(!languageCode.equals("") && !systemLocale.getLanguage().equals(languageCode)){
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(locale);
            }else{
                //configuration.locale = locale;
                configuration.setLocale(locale);
            }
        }
        return new ContextWrapper(context);
    }
}