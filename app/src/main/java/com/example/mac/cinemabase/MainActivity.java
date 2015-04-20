package com.example.mac.cinemabase;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

    //TAG for debugging messages
    private final String TAG = "MyTag";

    //drawer options
    private final int SOCIAL    = 0;
    private final int SETTINGS  = 1;
    private final int HELP      = 2;
    private final int LOGOUT    = 3;

    //Drawer components
    private String[] mDrawerTitles;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean isSlideOpen;

    private boolean internetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawerComponents();

        OMDBRequest req = new OMDBRequest(this);
        req.requestMovie("Shrek");
    }

    /**
     * Initialize the components of the side drawer.
     */
    private void initDrawerComponents(){
        isSlideOpen = false;

        //setup functionality of drawer
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle = new CustomActionBarDrawerToggle(this,mDrawerLayout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //custom shadow which overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //setup drawer titles
        mDrawerTitles = getResources().getStringArray(R.array.drawerTitles);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item,mDrawerTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    }

    /**
     * private class which listens for user selected options
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG,"Selected " + position);
            selected(position);
        }
    }

    /**
     *display appropriate activity based on user selection
     * @param pos array position of user selected
     */
    private void selected(int pos){
        Intent intent = null;
        switch (pos){
            case SOCIAL:
                break;
            case SETTINGS:
                intent = new Intent(this, SettingsPage.class);
                break;
            case HELP:
                break;
            case LOGOUT:
                break;
            default:
                Log.e(TAG,"Error: position " + pos + " is out of range");
                return;
        }
        if(intent != null){
            startActivity(intent);
        }
    }

    //Extended ActionBarDrawer
    private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle{

        public CustomActionBarDrawerToggle(Activity mActivity,
                                           DrawerLayout mDrawerLayout) {
            super(mActivity, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        }

        @Override
        public void onDrawerClosed(View view){
            invalidateOptionsMenu();//call to onPrepareOptionsMenu
            isSlideOpen = false;
            Log.d(TAG,"Closing Drawer");
        }

        @Override
        public void onDrawerOpened(View view){
            invalidateOptionsMenu();//call to onPrepareOptionsMenu
            isSlideOpen = true;
            Log.d(TAG, "Opening Drawer");
        }
    }

    //methods onDrawerOpened and onDrawerClosed are called
    public void clickDrawerListener(View view){
        if(isSlideOpen){
            mDrawerLayout.closeDrawer(Gravity.START);
            isSlideOpen = false;
        } else {
            mDrawerLayout.openDrawer(Gravity.START);
            isSlideOpen = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
