package tfs.converter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sinopsys on 9/12/18.
 */

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private CurrencyService service;
    private List<Currency> currencies;
    private GetCurrencyCallbacks callbacks;

    public CurrencyRepositoryImpl(GetCurrencyCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void downloadCurrencies() {
        // Basic solution: no offline support.

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CurrencyService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(CurrencyService.class);

        Call<CurrencyList> call = service.currencyList();

        call.enqueue(new Callback<CurrencyList>() {
            @Override
            public void onResponse(Call<CurrencyList> call, Response<CurrencyList> response) {
                Log.d("SSSSSSSSS", "onResponse:" + response.body());
                callbacks.onSuccess(response.body().getCurrencies());
            }

            @Override
            public void onFailure(Call<CurrencyList> call, Throwable t) {

                callbacks.onError(t);
                Log.d("SDFDSLFDSFDSLFK", "onFailure: DKSJFLDSJFLDSKJFLDSJFSFF" + t.getMessage());
            }
        });
    }
}


// EOF
