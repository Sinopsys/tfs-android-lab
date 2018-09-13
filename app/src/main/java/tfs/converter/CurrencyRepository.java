package tfs.converter;

/**
 * Created by sinopsys on 9/12/18.
 */

public interface CurrencyRepository {

    void downloadCurrencies();

    void getRate(String from, String to);

    interface OnResultCallback<T> {
        void onSuccess(T data);

        void onError(Throwable e);
    }
}


// EOF
