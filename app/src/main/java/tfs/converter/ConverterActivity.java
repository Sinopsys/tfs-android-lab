package tfs.converter;

import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

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

    private ArrayAdapter<CharSequence> adapterSpFrom;
    private ArrayAdapter<CharSequence> adapterSpTo;
    private ConverterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter_activity);
        ButterKnife.bind(this);

        presenter = new ConverterPresenter();
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
        // TODO: set spinner data
        String adsf = "";

//        for (Currency c : currencyList) {
//            adsf += c.getId() + " ";
//        }

        Toast.makeText(getApplicationContext(), adsf, Toast.LENGTH_LONG).show();
    }
}


// EOF
