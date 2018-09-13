package tfs.converter;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tfs.converter.base.BasePresenter;

/**
 * Created by sinopsys on 9/11/18.
 */

/**
 * Main presenter. Pattern: singleton.
 */
public class ConverterPresenter extends BasePresenter<ConverterView> {

    // All needed fields.
    //
    private static ConverterPresenter instance;
    private CurrencyRepository currencyRepository;
    private List<Currency> currencies;
    private double currentRate;
    private boolean downloaded, internetErrorShown;

    /**
     * Singleton static factory method.
     *
     * @return instance of the presentor.
     */
    public static ConverterPresenter getInstance() {
        if (instance == null) {
            instance = new ConverterPresenter();
            return instance;
        } else {
            return instance;
        }
    }

    /**
     * Private constructor for disabling remote creation of the presenter.
     */
    private ConverterPresenter() {
    }

    /**
     * Method to tell the repository to download all available currencies.
     */
    public void getCurrencies() {

        // If there is no network available, load currencies from Database.
        // Else, download them using API.
        //
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

            // Sort IDs of Currencies for easier lookup.
            //
            Collections.sort(currencies, (p1, p2) -> p1.getId().compareTo(p2.getId()));
            getView().setCurrencies(currencies);

        } else {
            currencyRepository = new CurrencyRepositoryImpl(new OnCurrenciesDownload(),
                    new OnRateDownload());

            // Download only once per life cycle.
            //
            if (!downloaded) {
                getView().showProgress();
                currencyRepository.downloadCurrencies();
            } else {
                getView().setCurrencies(currencies);
            }
        }
    }

    /**
     * A method to request exchange 'from' to 'to' in 'amount' from the repository using API.
     *
     * @param from   Currency to convert from.
     * @param to     Currency to convert to.
     * @param amount Amount to convert.
     */
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

        // A call of repository method.
        //
        if (currencyRepository == null) {
            currencyRepository = new CurrencyRepositoryImpl(new OnCurrenciesDownload(),
                    new OnRateDownload());
        }
        currencyRepository.getRate(from.toString(), to.toString());
    }

    /**
     * A callback implementation for making actions upon currencies download.
     */
    private final class OnCurrenciesDownload implements CurrencyRepository.OnResultCallback<List<Currency>> {
        @Override
        public void onSuccess(List<Currency> data) {
            Collections.sort(data, (p1, p2) -> p1.getId().compareTo(p2.getId()));
            currencies = data;

            // Put currencies in Database.
            //
            ArrayList<Object> toDb = new ArrayList<>();
            toDb.addAll(currencies);
            MyApplication.getDb().putListObject(MyApplication.KEY_CURRENCIES, toDb);

            // Ask view to update UI.
            //
            getView().setCurrencies(currencies);
            getView().hideProgress();
            ConverterPresenter.this.downloaded = true;
        }

        @Override
        public void onError(Throwable e) {
            getView().showErrorToast(e.getMessage());
        }
    }

    /**
     * A callback implementation for making actions upon rate download.
     */
    private final class OnRateDownload implements CurrencyRepository.OnResultCallback<Double> {
        @Override
        public void onSuccess(Double data) {
            currentRate = data;

            // Actual convertion.
            //
            double res = Double.parseDouble(getView().getFromText()) * currentRate;

            // Ask view to update UI.
            getView().setData(Double.toString(res));
            getView().hideProgress();
        }

        @Override
        public void onError(Throwable e) {
            getView().showErrorToast(e.getMessage());
        }
    }
}


// EOF
