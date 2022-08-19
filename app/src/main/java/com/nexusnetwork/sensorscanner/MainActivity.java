package com.nexusnetwork.sensorscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView nv;

    private DeviceList deviceFragment;
    private SensorFragment SensorFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceFragment = new DeviceList(this);
        SensorFragment = new SensorFragment();


        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            Toast.makeText(this, R.string.bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, deviceFragment, "device").commit();

        else
            onBackStackChanged();

        nv = findViewById(R.id.navview);
        nv.setNavigationItemSelectedListener(this);




    }


    @Override
    public void onBackStackChanged() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount()>0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void setBtAddress(String address) {
        SensorFragment = new SensorFragment();
        SensorFragment.setAddress(address);
        if (SensorFragment == null)
            SensorFragment = new SensorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, SensorFragment, "Sensor").commit();
        getSupportActionBar().setTitle("Sensor");
        drawerLayout.closeDrawers();
        //terminalFragment.connect();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        System.out.println(id);
        switch(id)
        {
            case R.id.device_list:
                if (deviceFragment == null)
                    deviceFragment = new DeviceList(this);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, deviceFragment, "devices").commit();
                getSupportActionBar().setTitle("Devices");
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.sensorOutput:
                if (SensorFragment == null)
                    SensorFragment = new SensorFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, SensorFragment, "Terminal").commit();
                getSupportActionBar().setTitle("Terminal");
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            default:
                return true;
        }


        return true;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}