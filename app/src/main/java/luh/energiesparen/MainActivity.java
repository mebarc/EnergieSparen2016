package luh.energiesparen;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private int mCurrentSelectedPosition;
    private boolean mFromSavedInstanceState;
    private String[] titles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nvView);

        mActionBarDrawerToggle = setupDrawerToggle();

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mFragmentManager = getSupportFragmentManager();

        titles = getResources().getStringArray(R.array.navTitles);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
            setTitle(titles[mCurrentSelectedPosition]);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
            setTitle(titles[0]);
        }

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

                        switch (menuItem.getItemId()) {
                            case R.id.nav_item_01_home:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
                                mCurrentSelectedPosition = 0;
                                break;
                            case R.id.nav_item_02_etc:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new BlankFragment()).commit();
                                mCurrentSelectedPosition = 1;
                                break;
                            case R.id.nav_item_03_tab1:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragment.newInstance(getResources().getStringArray(R.array.tabtitles1),
                                        getResources().getStringArray(R.array.STROMBAD), getResources().getStringArray(R.array.STROMKUECHE), getResources().getStringArray(R.array.STROMwohnung))).commit();
                                mCurrentSelectedPosition = 2;
                                break;
                            case R.id.nav_item_04_tab2:
                                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TabsFragment.newInstance(getResources().getStringArray(R.array.tabtitles1),getResources().getStringArray(R.array.STROMKUECHE),
                                        getResources().getStringArray(R.array.STROMwohnung), getResources().getStringArray(R.array.STROMBAD))).commit();
                                mCurrentSelectedPosition = 3;
                                break;
                        }
                        return false;
                    }
                });

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    public void onBackPressed() {
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "settings pressed", Toast.LENGTH_SHORT).show();
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION, 0);
        Menu menu = mNavigationView.getMenu();
        menu.getItem(mCurrentSelectedPosition).setChecked(true);
    }
}
