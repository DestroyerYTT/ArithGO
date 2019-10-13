package co.edu.icesi.arithgo.app;

import android.app.Application;
import android.content.Context;

public class ArithGoApp extends Application {


    private static Context context;

    public void onCreate(){
        super.onCreate();
        ArithGoApp.context = getApplicationContext();
    }

    public static  Context getAppContext(){
        return context;
    }
}
