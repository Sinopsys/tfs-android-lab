package tfs.converter;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by sinopsys on 9/13/18.
 */

/**
 * Class with life cycle of the application.
 */
public class MyApplication extends Application {

    // Constant fields.
    //
    public static final String URL_BASE = "https://free.currencyconverterapi.com/api/v6/";
    public static final String URL_CURRENCIES = URL_BASE + "currencies";
    public static final String URL_RATE = URL_BASE + "convert?compact=ultra&q=";
    public static final String KEY_POS_FROM = "POS_FROM";
    public static final String KEY_POS_TO = "POS_TO";
    public static final String KEY_CURRENCIES = "CURRENCIES";

    // 2 Singleton objects: a Volley queue and a TinyDB database.
    //
    private static RequestQueue queue;
    private static TinyDB db;

    /**
     * Use onCreate as a singleton factory.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);
        db = new TinyDB(this);
    }

    /**
     * @return An existing instance of Volley queue.
     */
    public static RequestQueue getRequestQueue() {
        return queue;
    }

    /**
     * @return An existing instance of TinyDB.
     */
    public static TinyDB getDb() {
        return db;
    }
}


// EOF
