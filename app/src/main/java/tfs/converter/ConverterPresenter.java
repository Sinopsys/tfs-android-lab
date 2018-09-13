package tfs.converter;

import android.text.TextUtils;

import java.util.List;

import tfs.converter.base.BasePresenter;

/**
 * Created by sinopsys on 9/11/18.
 */

public class ConverterPresenter extends BasePresenter<ConverterView> {

    //    private ConverterRepository repository;
    private CurrencyRepository currencyRepository;
    private List<Currency> currencies;
    private double currentRate;

    public ConverterPresenter() {
        currencyRepository = new CurrencyRepositoryImpl(new OnCurrenciesDownload(),
                new OnRateDownload());
    }

    public void getCurrencies() {
        getView().showProgress();
        currencyRepository.downloadCurrencies();
    }

    public void convert(Currency from, Currency to, String amount) {
        // Show initial progress
        getView().showProgress();

        if (TextUtils.isEmpty(amount)) {
            getView().showInputError();
            getView().hideProgress();
            return;
        }

        currencyRepository.getRate(from.toString(), to.toString());
    }

    private final class OnCurrenciesDownload implements CurrencyRepository.OnResultCallback<List<Currency>> {
        @Override
        public void onSuccess(List<Currency> data) {
            currencies = data;
            getView().setCurrencies(currencies);
            getView().hideProgress();
        }

        @Override
        public void onError(Throwable e) {
            // FIXME does nothing
        }
    }

    private final class OnRateDownload implements CurrencyRepository.OnResultCallback<Double> {
        @Override
        public void onSuccess(Double data) {
            currentRate = data;
            double res = Double.parseDouble(getView().getFromText()) * currentRate;
            getView().setData(Double.toString(res));
            getView().hideProgress();
        }

        @Override
        public void onError(Throwable e) {
            // FIXME does nothing
        }
    }
}


// EOF
