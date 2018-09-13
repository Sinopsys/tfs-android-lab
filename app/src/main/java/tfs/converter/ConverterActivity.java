package tfs.converter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

//  KUPRIYANOV KIRILL; kupriyanovkirill@gmail.com
//
//
//  What is done:
//  * A stable and working application
//  * Screen rotation is supported; spinners positions are retained
//  * A notification pop-up when device is not connected to a network
//  * Highlight EditText element when it is empty to indicate that input is required
//  * Saving currencies list to a database (implemented using SharedPrefs) so that
//  * they are available offline
//  * TODO no internet when convert is clicked
//

/**
 * Main activity class.
 */
public class ConverterActivity extends AppCompatActivity implements ConverterView {

    // View, String and helper fields.
    //
    @BindView(R.id.etFrom) EditText etFrom;
    @BindView(R.id.etTo) EditText etTo;
    @BindView(R.id.spFrom) Spinner spFrom;
    @BindView(R.id.spTo) Spinner spTo;
    @BindView(R.id.btnConvert) Button btnConvert;
    @BindView(R.id.progressBar) ProgressBar progress;
    @BindString(R.string.inputError) String inputError;

    private ConverterPresenter presenter;
    private int posFrom;
    private int posTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter_activity);
        // Binding fields.
        //
        ButterKnife.bind(this);

        // Create a singleton instance of presenter, attach view;
        // Download list of all currencies and set button click listener.
        //
        presenter = ConverterPresenter.getInstance();
        presenter.attachView(this);
        presenter.getCurrencies();
        btnConvert.setOnClickListener(convertClickListener);
    }

    /**
     * A method to retain spinners positions. Saves their states by saving index.
     * @param outState Bundle with parameters.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MyApplication.KEY_POS_FROM, (int) spFrom.getSelectedItemId());
        outState.putInt(MyApplication.KEY_POS_TO, (int) spTo.getSelectedItemId());
    }

    /**
     * A method to retain spinners positions. Restores state.
     * @param savedInstanceState Bundle with saved parameters.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        posFrom = savedInstanceState.getInt(MyApplication.KEY_POS_FROM, 0);
        posTo = savedInstanceState.getInt(MyApplication.KEY_POS_TO, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.detatchView();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setData(String s) {
        etTo.setText(s);
    }

    @Override
    public void setCurrencies(List<Currency> currencyList) {
        ArrayAdapter<Currency> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, currencyList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spFrom.setAdapter(adapter);
        spTo.setAdapter(adapter);
        spFrom.setSelection(posFrom);
        spTo.setSelection(posTo);
    }

    @Override
    public void showInputError() {
        etFrom.setError(inputError);
    }

    View.OnClickListener convertClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String amount = etFrom.getText().toString();
            Currency from = (Currency) spFrom.getAdapter().getItem((int) spFrom.getSelectedItemId());
            Currency to = (Currency) spTo.getAdapter().getItem((int) spTo.getSelectedItemId());

            presenter.convert(from, to, amount);
        }
    };

    @Override
    public String getFromText() {
        return etFrom.getText().toString();
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void showNoInternetNotification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.internetError)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    //do things
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}


// EOF
