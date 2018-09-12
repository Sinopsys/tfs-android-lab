package tfs.converter;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by sinopsys on 9/13/18.
 */

public interface GetCurrencyCallbacks {
    void onSuccess(@NonNull List<Currency> value);

    void onError(@NonNull Throwable throwable);
}


// EOF
