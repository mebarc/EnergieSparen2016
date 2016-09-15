package luh.energiesparen;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private FragmentManager mFragmentManager;
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private int mCurrentSelectedPosition;
    private boolean mFromSavedInstanceState;
    private String[] titles;
    private int settingsposition = 5;
    //Variablen für die JSON-Decodierung
    private static final String TAG_VALUE="Value";
    private static final String TAG_YEAR="Year";
    private static final String TAG_MONTH="Month";
    private static final String TAG_DAY="Day";

    //Ergebnisse der Datenbankabfrage für Strom, Gas , Wasser
    JSONArray treffer = null;
    JSONArray treffer2 = null;
    JSONArray treffer3 = null;
    //Arrays für die Visualisierung der Daten
    String[] valueArrayStrom;
    String[] dateArrayStrom;
    String[] valueArray_gas;
    String[] dateArrayGas;
    String[] valueArray_wasser;
    String[] dateArrayWasser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Default Values for Preferences on first Startup of the App
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Enable Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nvView);

        // Enable Icon functions from Toolbar
        mActionBarDrawerToggle = setupDrawerToggle();

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mFragmentManager = getSupportFragmentManager();

        // Array of NavigationDrawer Menu Items
        titles = getResources().getStringArray(R.array.navTitles);

        // Set Toolbar Title to last selected Item from NavigationDrawer
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
            setTitle(titles[mCurrentSelectedPosition]);
        } else {
            // Initial Startup: Starting HomeFragment and setting Title in Toolbar
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String username = sharedPref.getString("username", "");
            if (username == ""){
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new LoginFragment()).commit();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
            }
        }

        // App Navigation as reaction to click on Item in the Navigation Drawer
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if(menuItem.isCheckable()) {
                            menuItem.setChecked(true);
                        }
                        if(menuItem.isChecked()) {
                            setTitle(menuItem.getTitle());
                        }
                        mDrawerLayout.closeDrawers();

                        // Load new Activity or replace the fragment
                        switch (menuItem.getItemId()) {
                            case R.id.nav_item_01_home:
                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String username = sharedPref.getString("username", "");
                                //Einloggen des Nutzers fordern
                                if (username == ""){
                                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new LoginFragment()).commit();
                                    setTitle("Login");
                                }
                                else {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
                                    setTitle("EnergieSparen");
                                }

                            case R.id.nav_item_notizen:
                                SharedPreferences sharedPref3 = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String username3 = sharedPref3.getString("username", "");
                                //Einloggen des Nutzers fordern
                                if (username3 == ""){
                                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new NotizenFragment()).commit();
                                    setTitle("Login");
                                }
                                else {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new NotizenFragment()).commit();
                                    setTitle("EnergieSparen");
                                }

                                mCurrentSelectedPosition = 0;
                                break;
                            case R.id.nav_item_mein_verbrauch:
                                SharedPreferences sharedPref1 = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String username1 = sharedPref1.getString("username", "");

                                //Verbräuche nur anzeigen wenn Nutzer eingeloggt ist
                                if (username1 == ""){
                                    Toast login = Toast.makeText(getApplicationContext(), R.string.PleaseLogin, Toast.LENGTH_LONG);
                                    login.show();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new LoginFragment()).commit();
                                    setTitle("Login");
                                    break;
                                }
                                else {
                                    //Aufruf der AsyncTasks mit Datenbankabfrage.
                                    DownloadData downloadData1 = new DownloadData(getApplicationContext());
                                    DownloadData downloadData2 = new DownloadData(getApplicationContext());
                                    DownloadData downloadData3 = new DownloadData(getApplicationContext());
                                    //Nutzerdaten in Tabellen strom, gas und wasser suchen
                                    downloadData1.execute(username1, "strom");
                                    downloadData2.execute(username1, "gas");
                                    downloadData3.execute(username1, "wasser");
                                    String result_strom = "";
                                    String result_gas = "";
                                    String result_wasser = "";
                             //Strom: Resultat des AsyncTask abfragen
                                    try {
                                        result_strom = downloadData1.get(5, TimeUnit.SECONDS);
                                    } catch (InterruptedException e) {
                                        Toast toast0 = Toast.makeText(getApplicationContext(), R.string.ErrTimeOut, Toast.LENGTH_LONG);
                                        toast0.show();
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        Toast toast0 = Toast.makeText(getApplicationContext(), R.string.ErrTimeOut, Toast.LENGTH_LONG);
                                        toast0.show();
                                        e.printStackTrace();
                                    } catch (TimeoutException e) {
                                        Toast toast0 = Toast.makeText(getApplicationContext(), R.string.ErrTimeOut, Toast.LENGTH_LONG);
                                        toast0.show();
                                        e.printStackTrace();
                                    }
                                    //PHP-Skript liefert JSON-Array -> Decodierung
                                    try {
                                        JSONObject jsonObj = new JSONObject(result_strom);
                                        treffer = jsonObj.getJSONArray("result");
                                        // g.setMyEntries(treffer);
                                        valueArrayStrom = new String[treffer.length()];
                                        dateArrayStrom = new String[treffer.length()];
                                        // valueArrayStrom[0]= " Keine Einträge";
                                        for (int i = 0; i < treffer.length(); i++) {
                                            JSONObject c = treffer.getJSONObject(i);
                                            //Werte aus der Datenbank in umgekehrter Reihenfolge speichern um den neusten Wert zuerst zu sehen
                                            valueArrayStrom[treffer.length() - 1 - i] = c.getString(TAG_VALUE);
                                            dateArrayStrom[treffer.length() - 1 - i] = c.getString(TAG_DAY) + "." + c.getString(TAG_MONTH) + "." + c.getString(TAG_YEAR);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                              //Gas: Resultat auswerten

                                    try {
                                        result_gas = downloadData2.get(5, TimeUnit.SECONDS);
                                    } catch (InterruptedException e) {
                                        Toast toast0 = Toast.makeText(getApplicationContext(), R.string.ErrTimeOut, Toast.LENGTH_LONG);
                                        toast0.show();
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        Toast toast0 = Toast.makeText(getApplicationContext(), R.string.ErrTimeOut, Toast.LENGTH_LONG);
                                        toast0.show();
                                        e.printStackTrace();
                                    } catch (TimeoutException e) {
                                        Toast toast0 = Toast.makeText(getApplicationContext(), R.string.ErrTimeOut, Toast.LENGTH_LONG);
                                        toast0.show();
                                        e.printStackTrace();
                                    }
                                    //PHP-Skript liefert JSON-Array -> Decodierung
                                    try {
                                        JSONObject jsonObj = new JSONObject(result_gas);
                                        treffer2 = jsonObj.getJSONArray("result");
                                        //g.setMyEntries(treffer);
                                        valueArray_gas = new String[treffer2.length()];
                                        dateArrayGas = new String[treffer2.length()];
                                        // valueArray_gas[0]= " Keine Einträge";
                                        for (int i = 0; i < treffer2.length(); i++) {
                                            JSONObject c = treffer2.getJSONObject(i);
                                            valueArray_gas[treffer2.length() - 1 - i] = c.getString(TAG_VALUE);
                                            dateArrayGas[treffer2.length() - 1 - i] = c.getString(TAG_DAY) + "." + c.getString(TAG_MONTH) + "." + c.getString(TAG_YEAR);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                 //Wasser: Resultat auswerten

                                    try {
                                        result_wasser = downloadData3.get(5, TimeUnit.SECONDS);
                                    } catch (InterruptedException e) {
                                        Toast toast0 = Toast.makeText(getApplicationContext(), R.string.ErrTimeOut, Toast.LENGTH_LONG);
                                        toast0.show();
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        Toast toast0 = Toast.makeText(getApplicationContext(), R.string.ErrTimeOut, Toast.LENGTH_LONG);
                                        toast0.show();
                                        e.printStackTrace();
                                    } catch (TimeoutException e) {
                                        Toast toast0 = Toast.makeText(getApplicationContext(), R.string.ErrTimeOut, Toast.LENGTH_LONG);
                                        toast0.show();
                                        e.printStackTrace();
                                    }
                                    //PHP-Skript liefert JSON-Array -> Decodierung
                                    try {
                                        JSONObject jsonObj = new JSONObject(result_wasser);
                                        treffer3 = jsonObj.getJSONArray("result");
                                        //g.setMyEntries(treffer);
                                        valueArray_wasser = new String[treffer3.length()];
                                        dateArrayWasser = new String[treffer3.length()];
                                        // valueArray_wasser[0]= " Keine Einträge";
                                        for (int i = 0; i < treffer3.length(); i++) {
                                            JSONObject c = treffer3.getJSONObject(i);
                                            valueArray_wasser[treffer3.length() - 1 - i] = c.getString(TAG_VALUE);
                                            dateArrayWasser[treffer3.length() - 1 - i] = c.getString(TAG_DAY) + "." + c.getString(TAG_MONTH) + "." + c.getString(TAG_YEAR);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    if (dateArrayStrom == null && dateArrayWasser ==null&& dateArrayGas == null && valueArray_wasser==null&& valueArray_gas== null && valueArrayStrom==null){
                                        Toast nodata = Toast.makeText(getApplicationContext(), R.string.NoData, Toast.LENGTH_LONG);
                                        nodata.show();
                                        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new ZaehlerstandFragment()).commit();
                                        mCurrentSelectedPosition = 2;
                                        break;
                                    }
                                    else {
                                        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragmentVerbrauch.newInstance(
                                                getResources().getStringArray(R.array.sTabtitlesDaten),
                                                dateArrayStrom, valueArrayStrom, dateArrayGas, valueArray_gas, dateArrayWasser, valueArray_wasser)).commit();
                                        mCurrentSelectedPosition = 1;
                                        break;
                                    }
                                }
                            case R.id.nav_item_mein_zaehlerstand:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new ZaehlerstandFragment()).commit();
                                mCurrentSelectedPosition = 2;
                                break;

                            case R.id.nav_item_02_heizen:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragmentTipps.newInstance(getResources().getStringArray(R.array.sTabtitles),
                                        getResources().getStringArray(R.array.HEIZENBAD), getResources().getStringArray(R.array.HEIZENKUECHE), getResources().getStringArray(R.array.HEIZENWOHNUNG))).commit();
                                mCurrentSelectedPosition = 3;
                                break;

                            case R.id.nav_item_03_lueften:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragmentTipps.newInstance(getResources().getStringArray(R.array.sTabtitles),
                                        getResources().getStringArray(R.array.LUFTBAD), getResources().getStringArray(R.array.LUFTKUECHE), getResources().getStringArray(R.array.LUFTWOHNUNG))).commit();
                                mCurrentSelectedPosition = 4;
                                break;

                            case R.id.nav_item_04_strom:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragmentTipps.newInstance(getResources().getStringArray(R.array.sTabtitles),
                                        getResources().getStringArray(R.array.STROMBAD), getResources().getStringArray(R.array.STROMKUECHE), getResources().getStringArray(R.array.STROMWOHNUNG))).commit();
                                mCurrentSelectedPosition = 5;
                                break;

                            case R.id.nav_item_05_wasser:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragmentTipps.newInstance(getResources().getStringArray(R.array.sTabtitles),
                                        getResources().getStringArray(R.array.WASSERBAD), getResources().getStringArray(R.array.WASSERKUECHE), getResources().getStringArray(R.array.WASSERWOHNUNG))).commit();
                                mCurrentSelectedPosition = 6;
                                break;

                            case R.id.nav_sub_item_01_settings:
                                mCurrentSelectedPosition = settingsposition;
                                startSettings();


                        }
                        return false;
                    }
                });

        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean isFirstStart = sharedPref.getBoolean("first_startup", true);

                if(isFirstStart) {
                    mDrawerLayout.openDrawer(mNavigationView);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("first_startup", false);
                    editor.commit();
                }
            }
        });
    mThread.start();
    }

    private void startNotizen() {startActivity(new Intent(this,NotizenFragment.class));}
    private void startSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void startImpressum() {
        startActivity(new Intent(this, ImpressumActivity.class));
    }

    private void startRechtliches() {
        startActivity(new Intent(this, RechtlichesActivity.class));
    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    public void onBackPressed() {
        // Make Drawer close on backpress if opened instead of closing the App
        if (mDrawerLayout.isDrawerOpen(findViewById(R.id.nvView))) {
            mDrawerLayout.closeDrawer(findViewById(R.id.nvView));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // Navigation with the Dropdown OptionsMenu
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else if (id == R.id.action_settings) {
            mCurrentSelectedPosition = settingsposition;
            startSettings();
        } else if (id == R.id.action_impressum) {
            startImpressum();
        } else if (id == R.id.action_rechtliches) {
            startRechtliches();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION, 0);
        Menu menu = mNavigationView.getMenu();
        menu.getItem(mCurrentSelectedPosition).setChecked(true);
    }
}
