package luh.energiesparen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class VerbrauchActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText editText1;
    private EditText editText2;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbrauch);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroupLayout);
        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton:
                        editor.putString("wert1a", charSequence.toString());
                        break;
                    case R.id.radioButton2:
                        editor.putString("wert2a", charSequence.toString());
                        break;
                    case R.id.radioButton3:
                        editor.putString("wert3a", charSequence.toString());
                        break;
                }
                editor.commit();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton:
                        editor.putString("wert1b", charSequence.toString());
                        break;
                    case R.id.radioButton2:
                        editor.putString("wert2b", charSequence.toString());
                        break;
                    case R.id.radioButton3:
                        editor.putString("wert3b", charSequence.toString());
                        break;
                }
                editor.commit();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String[] einheiten = {"kWh", "m^3", "m^3"};
        TextView einheit1 = (TextView) findViewById(R.id.textView_einheit1);
        TextView einheit2 = (TextView) findViewById(R.id.textView_einheit2);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switch (view.getId()) {
            case R.id.radioButton:
                if (checked) {
//                    Toast.makeText(VerbrauchActivity.this, "button1", Toast.LENGTH_SHORT).show();
                    einheit1.setText(einheiten[0]);
                    einheit2.setText(einheiten[0]);
                    editText1.setText(sharedPref.getString("wert1a", ""));
                    editText2.setText(sharedPref.getString("wert1b", ""));
                }
                break;
            case R.id.radioButton2:
                if (checked) {
//                    Toast.makeText(VerbrauchActivity.this, "button2", Toast.LENGTH_SHORT).show();
                    einheit1.setText(einheiten[1]);
                    einheit2.setText(einheiten[1]);
                    editText1.setText(sharedPref.getString("wert2a", ""));
                    editText2.setText(sharedPref.getString("wert2b", ""));
                }
                break;
            case R.id.radioButton3:
                if (checked) {
//                    Toast.makeText(VerbrauchActivity.this, "button3", Toast.LENGTH_SHORT).show();
                    einheit1.setText(einheiten[2]);
                    einheit2.setText(einheiten[2]);
                    editText1.setText(sharedPref.getString("wert3a", ""));
                    editText2.setText(sharedPref.getString("wert3b", ""));
                }
                break;
        }
    }
}
