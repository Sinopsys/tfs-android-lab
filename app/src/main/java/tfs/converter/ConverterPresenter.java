package tfs.converter;

import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.List;

import tfs.converter.base.BasePresenter;

/**
 * Created by sinopsys on 9/11/18.
 */

public class ConverterPresenter extends BasePresenter<ConverterView> {

    //    private ConverterRepository repository;
    private CurrencyRepository currencyRepository;
    private List<Currency> currencies;

    public ConverterPresenter() {
        currencyRepository = new CurrencyRepositoryImpl(new GetCurrencyCallbacks() {
            @Override
            public void onSuccess(@NonNull List<Currency> value) {
                currencies = value;
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                // fixme does nothing at all.
            }
        });
    }

    public void getCurrencies() {
        currencyRepository.downloadCurrencies();
        getView().setCurrencies(currencies);
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
