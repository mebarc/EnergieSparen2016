package luh.energiesparen;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getFragmentManager().beginTransaction().replace(R.id.settings_fragment, new MyPreferenceFragment()).commit();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if(getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        setTitle(getResources().getString(R.string.action_settings));
    }


    public static class MyPreferenceFragment extends PreferenceFragment  {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            Preference username = findPreference("username");
            username.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getString("username","Login"));
            // Resetbutton Implementation
            Preference reset = findPreference("resetbutton");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    if(preference.getKey().equals("resetbutton")) {

                        // Make user confirm resetting
                        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
                        myAlert.setMessage("Stellt die Standardwerte für die Preise wieder her")
                                .setTitle("Preise zurücksetzen?")
                                .setPositiveButton("Zurücksetzen", new DialogInterface.OnClickListener() {

                                    // Reset the values to Default if user confirmed
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("preis_strom", getResources().getString(R.string.strom_defaultValue));
                                        editor.putString("preis_gas", getResources().getString(R.string.gas_defaultValue));
                                        editor.putString("preis_wasser", getResources().getString(R.string.wasser_defaultValue));
                                        editor.putString("push_notification", getResources().getString(R.string.notification_defaultValue));
                                        editor.apply();
                                        Toast.makeText(getActivity(), "Standard wiederhergestellt", Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                    }
                                })

                                // Keep the values if user declines
                                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        myAlert.show();
                    }

                    return false;
                }
            });
        }

    }
}


