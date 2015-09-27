package luh.energiesparen;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
    private Button remind;
    private TextView ergField;


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
        ergField = (TextView) findViewById(R.id.textView7);
        calc = (Button) findViewById(R.id.button);

        // Init Fields
        onRadioButtonClicked(findViewById(R.id.radioButton));

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


        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wertA = "";
                String wertB = "";
                String preis = "";
                String jeWoche, jeMonat, jeJahr;
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

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

                if (wertA.equals("ERR") || wertB.equals("ERR") || preis.equals("ERR"))
                    ergField.setText("Fehler in der Berechnung0");
                else if (wertA.equals("") || wertB.equals("") || preis.equals(""))
                    ergField.setText("Fehler in der Berechnung1");
                else {
                    Float value = Float.parseFloat(wertB) - Float.parseFloat(wertA);
                    Float fpreis = Float.parseFloat(preis);
                    if (value < 0 || fpreis < 0) ergField.setText("Fehler in der Berechnung2");
                    else {
                        value *= fpreis;
                        jeWoche = String.format("%.2f", value);
                        jeMonat = String.format("%.2f", value * 4);
                        jeJahr = String.format("%.2f", value * 52);
                        ergField.setText("Kosten je Woche: " + jeWoche + "€\n" +
                                "Kosten je Monat: " + jeMonat + "€\n" +
                                "Kosten je Jahr: " + jeJahr + "€\n");
                    }

                }
            }
        });

    }

    public void remindAlert(View view) {
//        long delay = 1000*60*60*24*7;
        long delay = 1000 * 5;

        /*Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText("Notification text");
        builder.setSmallIcon(R.drawable.ic_strom);
        builder.setAutoCancel(true);


        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, builder.build());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);*/

        Intent intent = new Intent(this, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1000 * 5), pendingIntent);
        Toast.makeText(this, "Alarm Started", Toast.LENGTH_SHORT).show();

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


    protected int getSelectedRadioButton() {

        int selection = 0;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioButton:
                selection = 0;
                break;
            case R.id.radioButton2:
                selection = 1;
                break;
            case R.id.radioButton3:
                selection = 2;
                break;
        }
        return selection;
    }

}
