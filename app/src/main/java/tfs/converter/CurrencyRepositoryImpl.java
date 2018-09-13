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

/**
 * Implementation of the application repository.
 */
public class CurrencyRepositoryImpl implements CurrencyRepository {

    // Callbacks used in async downloads.
    //
    private OnResultCallback<List<Currency>> currencyCallback;
    private OnResultCallback<Double> rateCallback;

    /**
     * C-tor.
     * @param currencyCallback Has onSuccess() and onError() which are called when currencies are downloaded.
     * @param rateCallback Has onSuccess() and onError() which are called when rates are downloaded.
     */
    public CurrencyRepositoryImpl(OnResultCallback<List<Currency>> currencyCallback,
                                  OnResultCallback<Double> rateCallback) {
        this.currencyCallback = currencyCallback;
        this.rateCallback = rateCallback;
    }

    /**
     * Downloads all currencies available, sends an API request.
     */
    @Override
    public void downloadCurrencies() {

        // Make new Volley JsonObjectRequest.
        //
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, MyApplication.URL_CURRENCIES, null,
                response -> {
                    List<Currency> currencies = new ArrayList<>();
                    try {
                        JSONObject jo = new JSONObject(response.toString()).getJSONObject("results");
                        Iterator<String> iter = jo.keys();

                        // Take first argument using iterator.
                        //
                        while (iter.hasNext()) {
                            String key = iter.next();
                            Currency currency = Currency.fromJSONObject(jo.getJSONObject(key));
                            currencies.add(currency);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Call callback's onSuccess.
                    //
                    currencyCallback.onSuccess(currencies);
                },
                error -> currencyCallback.onError(error)
        );

        // Launch request by adding it to a singleton queue.
        //
        MyApplication.getRequestQueue().add(getRequest);
    }

    /**
     * Gets rate for given currencies, sends an API request.
     * @param from A currency to convert from.
     * @param to A currency to convert to.
     */
    @Override
    public void getRate(String from, String to) {

        // Form a required by API param string.
        //
        StringBuilder builder = new StringBuilder();

        builder.append(from).append("_").append(to)
                .append(",")
                .append(to).append("_").append(to);

        String params = builder.toString();

        // Form a Volley JsonObjectRequest for getting rate.
        //
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

        // Launch request by adding it to a singleton queue.
        //
        MyApplication.getRequestQueue().add(getRequest);
    }
}


// EOF
