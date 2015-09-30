package luh.energiesparen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
    private Button calc;
    private TextView ergField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbrauch);

        // Enable Toolbar and their Navigation Ability
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroupLayout);
        editText1 = (EditText) findViewById(R.id.editText_Woche0);
        editText2 = (EditText) findViewById(R.id.editText_Woche1);
        ergField = (TextView) findViewById(R.id.textView_ergebnis);
        calc = (Button) findViewById(R.id.button_calc);

        // Init Fields
        onRadioButtonClicked(findViewById(R.id.radioButton_Strom));

        // Save values from first Textfield after change
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton_Strom:
                        editor.putString("wert1a", charSequence.toString());
                        break;
                    case R.id.radioButton_Gas:
                        editor.putString("wert2a", charSequence.toString());
                        break;
                    case R.id.radioButton_Wasser:
                        editor.putString("wert3a", charSequence.toString());
                        break;
                }
                editor.commit();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Save values from second Textfield after change
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton_Strom:
                        editor.putString("wert1b", charSequence.toString());
                        break;
                    case R.id.radioButton_Gas:
                        editor.putString("wert2b", charSequence.toString());
                        break;
                    case R.id.radioButton_Wasser:
                        editor.putString("wert3b", charSequence.toString());
                        break;
                }
                editor.commit();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Calculate the Energy Costs after Click
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wertA = "";
                String wertB = "";
                String preis = "";
                String jeWoche, jeMonat, jeJahr;
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(calc.getWindowToken(), 0);

                // Load values
                switch (getSelectedRadioButton()) {
                    case 0:
                        wertA = sharedPref.getString("wert1a", "ERR");
                        wertB = sharedPref.getString("wert1b", "ERR");
                        preis = sharedPref.getString("preis_strom", "ERR");

                        break;
                    case 1:
                        wertA = sharedPref.getString("wert2a", "ERR");
                        wertB = sharedPref.getString("wert2b", "ERR");
                        preis = sharedPref.getString("preis_gas", "ERR");
                        break;
                    case 2:
                        wertA = sharedPref.getString("wert3a", "ERR");
                        wertB = sharedPref.getString("wert3b", "ERR");
                        preis = sharedPref.getString("preis_wasser", "ERR");
                        break;
                }

                // Make sure Values are useable
                if (wertA.equals("ERR") || wertB.equals("ERR") || preis.equals("ERR"))
                    ergField.setText("Fehler beim Laden der Werte\nSind in beiden Feldern die Werte?");
                else if (wertA.equals("") || wertB.equals("") || preis.equals(""))
                    ergField.setText("Fehler:\nKein Werte eingetragen zur Berechnung");
                else {
                    Float amount = Float.parseFloat(wertB) - Float.parseFloat(wertA);
                    Float fpreis = Float.parseFloat(preis);
                    if (amount < 0 || fpreis < 0)
                        ergField.setText("Fehler!\nDer aktuelle Zählerstand muss größer sein, als der von letzter Woche");
                    else {

                        // Calculate Costs
                        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton_Gas) {
                            // Umrechnung von kWh = m^3 * Brennwert * Zustandszahl
                            // Brennwert = 10
                            // Zustandszahl = 0.95
                            amount *= fpreis * 10 * 95 / 100;
                        } else {
                            amount *= fpreis;
                        }

                        // Cent to Euro Calculation
                        amount /= 100;

                        jeWoche = String.format("%.2f", amount);
                        jeMonat = String.format("%.2f", amount * 4);
                        jeJahr = String.format("%.2f", amount * 52);

                        // Output
                        ergField.setText("Kosten je Woche: " + jeWoche + "€\n" +
                                "Kosten je Monat: " + jeMonat + "€\n" +
                                "Kosten je Jahr: " + jeJahr + "€\n");
                    }

                }
            }
        });

    }

    // Remind Button starting a Alarm which Triggers the Notification
    public void remindAlert(View view) {
//        long delay = 1000*60*60*24*7;
        long delay = 1000 * 5;


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        Intent intent = new Intent(this, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
//        Toast.makeText(this, "Erinnerung in einer Woche", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Erinnerung in 5 Sekunden", Toast.LENGTH_SHORT).show();

    }


    // Load the Values into the Edittexts on Change of Selection
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String[] einheiten = {"kWh", "m^3", "m^3"};
        TextView einheit1 = (TextView) findViewById(R.id.textView_einheit1);
        TextView einheit2 = (TextView) findViewById(R.id.textView_einheit2);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switch (view.getId()) {
            case R.id.radioButton_Strom:
                if (checked) {
//                    Toast.makeText(VerbrauchActivity.this, "button1", Toast.LENGTH_SHORT).show();
                    einheit1.setText(einheiten[0]);
                    einheit2.setText(einheiten[0]);
                    editText1.setText(sharedPref.getString("wert1a", ""));
                    editText2.setText(sharedPref.getString("wert1b", ""));
                }
                break;
            case R.id.radioButton_Gas:
                if (checked) {
//                    Toast.makeText(VerbrauchActivity.this, "button2", Toast.LENGTH_SHORT).show();
                    einheit1.setText(einheiten[1]);
                    einheit2.setText(einheiten[1]);
                    editText1.setText(sharedPref.getString("wert2a", ""));
                    editText2.setText(sharedPref.getString("wert2b", ""));
                }
                break;
            case R.id.radioButton_Wasser:
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


    protected int getSelectedRadioButton() {

        int selection = 0;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioButton_Strom:
                selection = 0;
                break;
            case R.id.radioButton_Gas:
                selection = 1;
                break;
            case R.id.radioButton_Wasser:
                selection = 2;
                break;
        }
        return selection;
    }

}
