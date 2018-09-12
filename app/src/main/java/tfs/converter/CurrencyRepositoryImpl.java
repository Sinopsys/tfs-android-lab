package tfs.converter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sinopsys on 9/12/18.
 */

public class CurrencyRepositoryImpl implements CurrencyRepository {

    // private CurrencyService service;


    @Override
    public List<Currency> getCurrencies() {
        // Basic solution: no offline support

        // return service.getCurrencies();

        // FIXME: mock impl
        Currency currency = new Currency("RUB", "RUBLE", "%");
        return Arrays.asList(currency,currency,currency);
    }
}
