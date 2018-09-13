package tfs.converter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.lang.ref.WeakReference;
import java.util.List;

import tfs.converter.base.BasePresenter;

/**
 * Created by sinopsys on 9/11/18.
 */

public class ConverterPresenter extends BasePresenter<ConverterView> {

    //    private ConverterRepository repository;
    private CurrencyRepository currencyRepository;
    private List<Currency> currencies;
    private WeakReference<Context> context;
    private RequestQueue queue;

    public ConverterPresenter(WeakReference<Context> context) {
        this.context = context;

        currencyRepository = new CurrencyRepositoryImpl(new GetCurrencyCallbacks() {
            @Override
            public void onSuccess(@NonNull List<Currency> value) {
                currencies = value;
                getView().setCurrencies(currencies);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                // fixme does nothing at all.
            }
        }, context);
    }

    public void getCurrencies() {
        currencyRepository.downloadCurrencies();
    }

    public void convert(Currency from, Currency to, double amount) {
        // Show initial progress
        getView().showProgress();

        // TODO: compute
//        repository.convert();

        // OnSuccessCallback
        getView().hideProgress();
        getView().setData("55.32");
    }
}


// EOF
