package tfs.converter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Created by sinopsys on 9/12/18.
 */

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private GetCurrencyCallbacks callbacks;
    private WeakReference<Context> context;

    public CurrencyRepositoryImpl(GetCurrencyCallbacks callbacks, WeakReference<Context> context) {
        this.callbacks = callbacks;
        this.context = context;
    }

    @Override
    public void downloadCurrencies() {
        // Basic solution: no offline support.

        RequestQueue queue = Volley.newRequestQueue(context.get());
        final String url = "https://free.currencyconverterapi.com/api/v6/currencies";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    List<Currency> currencies = new ArrayList<>();
                    try {
                        JSONObject jo = new JSONObject(response.toString()).getJSONObject("results");
                        Iterator<String> iter = jo.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            Currency currency = Currency.fromJSONObject(jo.getJSONObject(key));
                            currencies.add(currency);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callbacks.onSuccess(currencies);
                },
                error -> callbacks.onError(error)
        );

        queue.add(getRequest);
    }
}


// EOF
