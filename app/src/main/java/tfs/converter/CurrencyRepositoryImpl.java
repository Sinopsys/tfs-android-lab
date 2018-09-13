package tfs.converter;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by sinopsys on 9/12/18.
 */

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private OnResultCallback<List<Currency>> currencyCallback;
    private OnResultCallback<Double> rateCallback;

    public CurrencyRepositoryImpl(OnResultCallback<List<Currency>> currencyCallback,
                                  OnResultCallback<Double> rateCallback) {
        this.currencyCallback = currencyCallback;
        this.rateCallback = rateCallback;
    }

    @Override
    public void downloadCurrencies() {
        // Basic solution: no offline support.

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, MyApplication.URL_CURRENCIES, null,
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
                    currencyCallback.onSuccess(currencies);
                },
                error -> currencyCallback.onError(error)
        );

        MyApplication.getRequestQueue().add(getRequest);
    }

    @Override
    public void getRate(String from, String to) {

        StringBuilder builder = new StringBuilder();

        builder.append(from).append("_").append(to)
                .append(",")
                .append(to).append("_").append(to);

        String params = builder.toString();


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, MyApplication.URL_RATE + params, null,
                response -> {
                    double value = 0.;
                    try {
                        JSONObject jo = new JSONObject(response.toString());
                        Iterator<String> keys = jo.keys();
                        String str_Name = keys.next();
                        value = Double.parseDouble(jo.optString(str_Name));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rateCallback.onSuccess(value);
                },
                error -> rateCallback.onError(error)
        );

        MyApplication.getRequestQueue().add(getRequest);
    }
}


// EOF
