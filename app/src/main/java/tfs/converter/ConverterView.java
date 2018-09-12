package tfs.converter;

import java.util.List;

import tfs.converter.base.BaseView;

/**
 * Created by sinopsys on 9/12/18.
 */

interface ConverterView extends BaseView {

    void showProgress();

    void hideProgress();

    void setData(String s);

    void setCurrencies(List<Currency> currencyList);
}


// EOF
