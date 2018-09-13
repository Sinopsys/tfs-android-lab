package tfs.converter;

import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import tfs.converter.base.BaseView;

public class ConverterActivity extends AppCompatActivity implements ConverterView {

    @BindView(R.id.etFrom)
    EditText etFrom;
    @BindView(R.id.etTo)
    EditText etTo;
    @BindView(R.id.spFrom)
    Spinner spFrom;
    @BindView(R.id.spTo)
    Spinner spTo;
    @BindView(R.id.btnConvert)
    Button btnConvert;
    @BindView(R.id.progressBar)
    ProgressBar progress;
    @BindString(R.string.inputError)
    String inputError;

    private ConverterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter_activity);
        ButterKnife.bind(this);

        presenter = new ConverterPresenter();
        presenter.attachView(this);
        presenter.getCurrencies();
        btnConvert.setOnClickListener(convertClickListener);
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
}


// EOF
