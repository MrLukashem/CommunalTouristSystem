package com.example.mrlukashem.communaltouristsystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.mrlukashem.utils.XmlManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MapsActivity
        extends AppCompatActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;

    private SupportMapFragment mMapFragment;

    private final String mMapFragmentName = "MapFragment";

    private android.support.v4.app.FragmentManager mSupportFragmentManager;

    private FragmentManager mFragmentManager;

    private Toolbar mToolBar;

    private DrawerLayout mDrawer;

    private ActionBarDrawerToggle mToogle;

    private NavigationView mNavigationView;

    private void initFragmentManagers() {
        mSupportFragmentManager = getSupportFragmentManager();
        mFragmentManager = getFragmentManager();
    }

    /*
        Initialize map fragment and adding to activity view.
        Important to invoke this method after setContentView() function.
     */
    private void initMapFragment() {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                mSupportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_container, mapFragment, mMapFragmentName);
        fragmentTransaction.commit();
    }

    private void initToolBar() {

        mToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
    }

    private void initNavigationView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToogle = new ActionBarDrawerToggle(
                this, mDrawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.setDrawerListener(mToogle);
        mToogle.syncState();

        mNavigationView = (NavigationView)findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initFragmentManagers();
        initMapFragment();
        initToolBar();
        initNavigationView();

        try {
            String path = Environment.getExternalStorageDirectory().getPath() + "/xml_files/content.xml";
            Log.d("important -->", path);
            InputStream inputStream = new FileInputStream(path);
            XmlManager xmlManager = XmlManager.newXmlManager(inputStream);
            xmlManager.fillDataContainer();

            inputStream.close();
        } catch(IOException | XmlPullParserException exc) {
            exc.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //TODO: Moving camera onMapReady for place includes in xml file. Example: moveCamera(CameraUpdateFactory.newLatLng(new_place_latlng));
        mMap = googleMap;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getTitle().toString()) {
            case "Informacje":
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        mSupportFragmentManager.beginTransaction();

                Fragment mapFragment = mSupportFragmentManager.findFragmentByTag(mMapFragmentName);
                fragmentTransaction.hide(mapFragment);
     //           fragmentTransaction.remove(mapFragment);
                fragmentTransaction.commit();
                break;
        }
        return true;
    }
}
