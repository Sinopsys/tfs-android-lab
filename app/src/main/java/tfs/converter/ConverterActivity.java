package tfs.converter;

import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tfs.converter.base.BaseView;

public class ConverterActivity extends AppCompatActivity implements ConverterView {

    @BindView(R.id.etFrom) EditText etFrom;
    @BindView(R.id.etTo) EditText etTo;
    @BindView(R.id.spFrom) Spinner spFrom;
    @BindView(R.id.spTo) Spinner spTo;
    @BindView(R.id.btnConvert) Button btnConvert;

    private ConverterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter_activity);
        ButterKnife.bind(this);

        presenter = new ConverterPresenter(new WeakReference<>(this.getApplicationContext()));
        presenter.attachView(this);
        presenter.getCurrencies();
        btnConvert.setOnClickListener(v -> {
            presenter.convert(null, null, 1);
        });
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

    }

    @Override
    public void hideProgress() {

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
}


// EOF
