package tfs.converter;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tfs.converter.base.BasePresenter;

/**
 * Created by sinopsys on 9/11/18.
 */

public class ConverterPresenter extends BasePresenter<ConverterView> {

    private static ConverterPresenter instance;
    private CurrencyRepository currencyRepository;
    private List<Currency> currencies;
    private double currentRate;
    private boolean downloaded, internetErrorShown;

    public static ConverterPresenter getInstance() {
        if (instance == null) {
            instance = new ConverterPresenter();
            return instance;
        } else {
            return instance;
        }
    }

    private ConverterPresenter() {
    }

    public void getCurrencies() {
        if (!getView().isNetworkAvailable()) {
            if (!internetErrorShown) {
                getView().showNoInternetNotification();
            }
            internetErrorShown = true;

            List<Object> currs = MyApplication.getDb().getListObject(MyApplication.KEY_CURRENCIES, Currency.class);
            currencies = new ArrayList<>();
            for (int i = 0; i < currs.size(); ++i) {
                currencies.add((Currency) currs.get(i));
            }
            Collections.sort(currencies, (p1, p2) -> p1.getId().compareTo(p2.getId()));
            getView().setCurrencies(currencies);

        } else {
            currencyRepository = new CurrencyRepositoryImpl(new OnCurrenciesDownload(),
                    new OnRateDownload());
            if (!downloaded) {
                getView().showProgress();
                currencyRepository.downloadCurrencies();
            } else {
                getView().setCurrencies(currencies);
            }
        }
    }

    public void convert(Currency from, Currency to, String amount) {
        // Show initial progress
        if (!getView().isNetworkAvailable()) {
            getView().showNoInternetNotification();
            return;
        }
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
            Collections.sort(data, (p1, p2) -> p1.getId().compareTo(p2.getId()));
            currencies = data;
            // put in DB
            ArrayList<Object> toDb = new ArrayList<>();
            toDb.addAll(currencies);
            MyApplication.getDb().putListObject(MyApplication.KEY_CURRENCIES, toDb);
            // get all to all relations rates.
            getView().setCurrencies(currencies);
            getView().hideProgress();
            ConverterPresenter.this.downloaded = true;
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
