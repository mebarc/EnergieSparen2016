package luh.energiesparen;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


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
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
            setTitle(titles[0]);
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
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
                                mCurrentSelectedPosition = 0;
                                break;

                            case R.id.nav_item_02_heizen:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragment.newInstance(getResources().getStringArray(R.array.sTabtitles),
                                        getResources().getStringArray(R.array.HEIZENBAD), getResources().getStringArray(R.array.HEIZENKUECHE), getResources().getStringArray(R.array.HEIZENWOHNUNG))).commit();
                                mCurrentSelectedPosition = 1;
                                break;

                            case R.id.nav_item_03_lueften:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragment.newInstance(getResources().getStringArray(R.array.sTabtitles),
                                        getResources().getStringArray(R.array.LUFTBAD), getResources().getStringArray(R.array.LUFTKUECHE), getResources().getStringArray(R.array.LUFTWOHNUNG))).commit();
                                mCurrentSelectedPosition = 2;
                                break;

                            case R.id.nav_item_04_strom:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragment.newInstance(getResources().getStringArray(R.array.sTabtitles),
                                        getResources().getStringArray(R.array.STROMBAD), getResources().getStringArray(R.array.STROMKUECHE), getResources().getStringArray(R.array.STROMWOHNUNG))).commit();
                                mCurrentSelectedPosition = 3;
                                break;

                            case R.id.nav_item_05_wasser:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragment.newInstance(getResources().getStringArray(R.array.sTabtitles),
                                        getResources().getStringArray(R.array.WASSERBAD), getResources().getStringArray(R.array.WASSERKUECHE), getResources().getStringArray(R.array.WASSERWOHNUNG))).commit();
                                mCurrentSelectedPosition = 4;
                                break;

                            case R.id.nav_sub_item_01_settings:
                                mCurrentSelectedPosition = settingsposition;
                                startSettings();

                        }
                        return false;
                    }
                });

    }

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
        } else if (id == R.id.action_settings) {
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
