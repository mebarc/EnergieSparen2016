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
    }
/*
    Inkonsistente Darstellung von Dialogen etc:
    Dialog ist Systemstandard, Alert ist Materialdesign
    Problem ist, dass die Supportlib inkonsistenten und Bugs aufweist,
    dadurch ist eine z.B. keine Dialogeingabe von Ziffern ohne Workarounds
    und Wrapper möglich
    siehe:
    https://code.google.com/p/android/issues/detail?id=183376
    https://code.google.com/p/android/issues/detail?id=185164
*/

    public static class MyPreferenceFragment extends PreferenceFragment  {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            Preference reset = findPreference("resetbutton");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    if(preference.getKey().equals("resetbutton")) {

                        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
                        myAlert.setMessage("Stellt die Standardwerte für die Preise wieder her")
                                .setTitle("Preise zurücksetzen?")
                                .setPositiveButton("Zurücksetzen", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("preis_strom", "666");
                                        editor.apply();
                                        Toast.makeText(getActivity(), "Standard wiederhergestellt", Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                    }
                                })
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

/*
public class SettingsActivity  extends AppCompatActivity {
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager().beginTransaction().replace(R.id.settings_fragment, new MyPreferenceFragment()).commit();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if(getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

    }



    public static class MyPreferenceFragment extends PreferenceFragmentCompat implements android.support.v7.preference.Preference.OnPreferenceClickListener {



        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            addPreferencesFromResource(R.xml.preferences);
            findPreference("resetbutton").setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {

            if(preference.getKey().equals("resetbutton"))
            {

                AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
                myAlert.setMessage("Stellt die Standardwerte für die Preise wieder her")
                        .setTitle("Preise zurücksetzen?")
                        .setPositiveButton("Zurücksetzen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("preis_strom", "666");
                                editor.apply();
                                Toast.makeText(getActivity(), "Standard wiederhergestellt", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                myAlert.show();

                */
/*Fragment frg = new MyPreferenceFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                *//*
*/
/*ft.detach(frg);
                ft.attach(frg);*//*
*/
/*
                ft.replace(R.id.settings_fragment, new MyPreferenceFragment());
                ft.addToBackStack(MyPreferenceFragment.class.getSimpleName());
                ft.commit();*//*

                //getActivity().finish();

            } else if (preference.getKey().equals("preis_strom")) {
                EditTextPreference pref = (EditTextPreference) preference;


            }
            return false;
        }


    }
}
*/
