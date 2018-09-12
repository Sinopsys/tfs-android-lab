package tfs.converter;

import java.util.List;

import tfs.converter.base.BasePresenter;

/**
 * Created by sinopsys on 9/11/18.
 */

public class ConverterPresenter extends BasePresenter<ConverterView> {

    //    private ConverterRepository repository;
    private CurrencyRepository currencyRepository;

    public ConverterPresenter() {
//        repository = new impl
    }

    public void getCurrencies() {
        List<Currency> currencyList = currencyRepository.getCurrencies();
        getView().setCurrencies(currencyList);
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
