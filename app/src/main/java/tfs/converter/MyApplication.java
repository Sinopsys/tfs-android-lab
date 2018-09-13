package tfs.converter;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by sinopsys on 9/13/18.
 */

public class MyApplication extends Application {

    public static final String URL_BASE = "https://free.currencyconverterapi.com/api/v6/";
    public static final String URL_CURRENCIES = URL_BASE + "currencies";
    public static final String URL_RATE = URL_BASE + "convert?compact=ultra&q=";
    public static final String KEY_POS_FROM = "POS_FROM";
    public static final String KEY_POS_TO = "POS_TO";

    private static RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);
    }

    public static RequestQueue getRequestQueue() {
        return queue;
    }
}


// EOF
