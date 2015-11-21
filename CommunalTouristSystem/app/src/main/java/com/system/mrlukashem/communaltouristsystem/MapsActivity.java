package com.system.mrlukashem.communaltouristsystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.Fragments.InfoFragment;
import com.system.mrlukashem.Fragments.PlacesListFragment;
import com.system.mrlukashem.Interfaces.FillContentCallback;
import com.system.mrlukashem.Interfaces.FillPlacesListCallback;
import com.system.mrlukashem.Interfaces.MapManager;
import com.system.mrlukashem.Interfaces.ServicesProvider;
import com.system.mrlukashem.datebase.DatabaseWrapper;
import com.system.mrlukashem.refbases.PlaceRefBase;
import com.system.mrlukashem.refbases.TrackingWayRefBase;
import com.system.mrlukashem.utils.GPSListener;
import com.system.mrlukashem.utils.XmlContentContainer;
import com.system.mrlukashem.utils.XmlManager;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity
        extends AppCompatActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener,
        FillContentCallback, ServicesProvider, FillPlacesListCallback {

    private final String INFORMATIONS = "Informacje";
    private final String TERREIN_MAP = "Mapa terenu";
    private final String SATELITE_MAP = "Mapa satelita";
    private final String SHOW_CURRENT_POS = "Pokaż aktualna pozycję";
    private final String WAY_TRACE = "Sledź trasę";
    private final String STOP_WAY_TRACE = "Zatrzymaj śledzenie trasy";
    private final String CAPTURE_PHOTO = "Zrób zdjęcie";
    private final String SETTINGS = "Ustawienia";
    private final String LOAD_CONF_FILE = "Wczytaj plik konfiguracyjny";
    private final String SHOW_WAYS_LIST = "Pokaż listę tras";
    private final String SHOW_PLACES_LIST = "Pokaż listę miejsc";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private GoogleMap mMap;

    private MapManager<PlaceRefBase> mMapManager;

    private final String mMapFragmentName = "MapFragment";

    private android.support.v4.app.FragmentManager mSupportFragmentManager;

    private FragmentManager mFragmentManager;

    private android.app.Fragment mCurrentFragment;

    private Toolbar mToolBar;

    private DrawerLayout mDrawer;

    private ActionBarDrawerToggle mToogle;

    private NavigationView mNavigationView;

    private GPSWayTracker mTrackerService;

    private ServicesProvider mServicesProvider;

    private boolean mServiceIsBound = false;

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

    private void initMapManager() {
        //TODO: A little changes in CustomMapManager architecture/
        try {
            CustomMapManager.setGoogleMap(mMap);
            mMapManager = CustomMapManager.getInstance();
        } catch(NullPointerException npe) {
            Log.e("initMapManager:" , npe.getMessage());
            npe.printStackTrace();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void fillInformationView(View view) {
        TextView name = (TextView)view.findViewById(R.id.communeName);
        TextView major = (TextView)view.findViewById(R.id.major);
        TextView history = (TextView)view.findViewById(R.id.history);
        TextView placement = (TextView)view.findViewById(R.id.placement);
        TextView others = (TextView)view.findViewById(R.id.others);
        TextView places = (TextView)view.findViewById(R.id.communalPlaces);

        CommuneDescriptor communeDescriptor = XmlContentContainer.getInstance().getCommuneDesc();
        name.setText(communeDescriptor.getName());
        major.setText(communeDescriptor.getMajor());
        history.setText(communeDescriptor.getHistory());
        placement.setText(communeDescriptor.getPlacement());
        others.setText(communeDescriptor.getOthers());
        places.setText(communeDescriptor.getCommunalPlaces().toString());
    }

    private void showMapFragment() {
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                mSupportFragmentManager.beginTransaction();

        Fragment mapFragment = mSupportFragmentManager.findFragmentByTag(mMapFragmentName);
        fragmentTransaction.show(mapFragment);
        fragmentTransaction.commit();
    }

    private void hideMapFragment() {
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                mSupportFragmentManager.beginTransaction();

        Fragment mapFragment = mSupportFragmentManager.findFragmentByTag(mMapFragmentName);
        if(mapFragment.isVisible()) {
            fragmentTransaction.hide(mapFragment);
            fragmentTransaction.commit();
        }
    }

    private void removeCurrentFragment() {
        FragmentTransaction fragmentTransaction =
                mFragmentManager.beginTransaction();

        fragmentTransaction.remove(mCurrentFragment);
        fragmentTransaction.commit();
    }

    private void showInfoFragment() {
        FragmentTransaction fragmentTransaction =
                mFragmentManager.beginTransaction();

        mCurrentFragment = new InfoFragment();
        fragmentTransaction.add(R.id.content_container, mCurrentFragment);
        fragmentTransaction.commit();
    }

    private void showPlacesListFragment() {
        FragmentTransaction fragmentTransaction =
                mFragmentManager.beginTransaction();

        mCurrentFragment = new PlacesListFragment();
        fragmentTransaction.add(R.id.content_container, mCurrentFragment);
        fragmentTransaction.commit();
    }

    private void pushElementsOnMap() {
        List<PlaceRefBase> list = XmlContentContainer.getInstance().getPlacesList();
        mMapManager.pushElement(list.get(0), "center");
    }

    private void setAdapters(View rootView) {
        final ListView listView = (ListView)rootView.findViewById(R.id.placesListView);
        PlacesListAdapter adapter = new PlacesListAdapter(
                getApplicationContext(),
                R.layout.places_list_element,
                mMapManager.getPlacesList());

        listView.setAdapter(adapter);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GPSWayTracker.LocalBinder localBinder = (GPSWayTracker.LocalBinder)service;
            mTrackerService = localBinder.getService();
            try {
                mTrackerService.startGPSListening(1000, 0, mServicesProvider);
            } catch(GPSListener.GPSListenerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mTrackerService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initFragmentManagers();
        initMapFragment();
        initToolBar();
        initNavigationView();
        mServicesProvider = this;

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

        try {
            DatabaseWrapper databaseWrapper = DatabaseWrapper.getInstanceAndInitDb(getApplicationContext());

            TrackingWayRefBase trackingWayRefBase = new TrackingWay();
            List<LatLng> list = new ArrayList<>();
            list.add(new LatLng(0, 1));
            list.add(new LatLng(1, 1));
            list.add(new LatLng(23, 11));
            trackingWayRefBase.pushPoints(list);

            trackingWayRefBase.setTag("droga");

  //          databaseWrapper.put(trackingWayRefBase);

   //         databaseWrapper.read(trackingWayRefBase, "droga");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
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
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
        initMapManager();

        pushElementsOnMap();
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
            case INFORMATIONS:
                hideMapFragment();
                showInfoFragment();
                break;
            case TERREIN_MAP:
                removeCurrentFragment();
                showMapFragment();
                break;
            case SATELITE_MAP:

                break;
            case SHOW_CURRENT_POS:
                break;
            case WAY_TRACE:
                if(!mServiceIsBound) {
                    Intent intent = new Intent(getApplicationContext(), GPSWayTracker.class);
                    startService(intent);
                    mServiceIsBound = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                }

                Log.i("Activity", Boolean.toString(mServiceIsBound));
                break;
            case STOP_WAY_TRACE:
                if(mTrackerService != null && mServiceIsBound) {
                    mTrackerService.stopUsingGPS();
                    List<LatLng> points = mTrackerService.getLastKnownTrackedPoints();

                    unbindService(mServiceConnection);
                    stopService(new Intent(getApplicationContext(), GPSWayTracker.class));

                    mServiceIsBound = false;
                }
                break;
            case CAPTURE_PHOTO:
                dispatchTakePictureIntent();
                break;
            case SHOW_WAYS_LIST:

                break;
            case SHOW_PLACES_LIST:
                hideMapFragment();
                showPlacesListFragment();
                break;
            case SETTINGS:
                break;
            case LOAD_CONF_FILE:
                break;
        }
        return true;
    }

    @Override
    public void invoke(View view) {
        fillInformationView(view);
    }

    @Override
    public LocationManager getLocationService() {
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        return locationManager;
    }

    @Override
    public View fillPlacesList(View view) {
        setAdapters(view);

        return view;
    }
}