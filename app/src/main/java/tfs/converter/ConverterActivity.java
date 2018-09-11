package tfs.converter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConverterActivity extends AppCompatActivity {

    @BindView(R.id.etFrom) EditText etFrom;
    @BindView(R.id.etTo) EditText etTo;
    @BindView(R.id.spFrom) Spinner spFrom;
    @BindView(R.id.spTo) Spinner spTo;
    @BindView(R.id.btnConvert) Button btnConvert;

    private ArrayAdapter<CharSequence> adapterSpFrom;
    private ArrayAdapter<CharSequence> adapterSpTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter_activity);
        ButterKnife.bind(this);
    }
}


// EOF
