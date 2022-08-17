package com.nexusnetwork.sensorscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            Toast.makeText(this, R.string.bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new DeviceList(), "devices").commit();
        else
            onBackStackChanged();
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
}