package com.nexusnetwork.sensorscanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexusnetwork.sensorscanner.R;

import java.util.ArrayList;
import java.util.Collections;

public class DeviceList extends ListFragment {
    private BluetoothAdapter bluetoothAdapter;
    private final ArrayList<BluetoothDevice> listItems = new ArrayList<>();
    private ArrayAdapter<BluetoothDevice> listAdapter;

    private final MainActivity instance;

    public DeviceList(MainActivity instance) {
        this.instance = instance;
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH))
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        listAdapter = new ArrayAdapter<BluetoothDevice>(getActivity(), 0, listItems) {
            @NonNull
            @Override
            public View getView(int position, View view, @NonNull ViewGroup parent) {
                BluetoothDevice device = listItems.get(position);
                if (view == null)
                    view = getActivity().getLayoutInflater().inflate(R.layout.device_list, parent, false);
                TextView text1 = view.findViewById(R.id.text1);
                TextView text2 = view.findViewById(R.id.text2);

                checkPermission("Manifest.permission.BLUETOOTH_CONNECT", 1);
                String name = device.getName();
                if (name == null) {
                    name = "";
                }
                text1.setText(name);


                text2.setText(device.getAddress());
                return view;
            }
        };
    }


    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[] { permission }, requestCode);
            requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT);
        }
        else {
            Toast.makeText(getActivity(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(null);
        View header = getActivity().getLayoutInflater().inflate(R.layout.device_list_header, null, false);
        getListView().addHeaderView(header, null, false);
        setEmptyText("initializing...");
        ((TextView) getListView().getEmptyView()).setTextSize(18);
        setListAdapter(listAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_devices, menu);
//        if(bluetoothAdapter == null)
//            menu.findItem(R.id.bt_settings).setEnabled(false);
    }



    @Override
    public void onResume() {
        super.onResume();
        if(bluetoothAdapter == null)
            setEmptyText("<bluetooth not supported>");
        else if(!bluetoothAdapter.isEnabled())
            setEmptyText("<bluetooth is disabled>");
        else
            setEmptyText("<no bluetooth devices found>");
        refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.bt_settings) {
//            Intent intent = new Intent();
//            intent.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
//            startActivity(intent);
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
//        }
        return super.onOptionsItemSelected(item);
    }

    void refresh() {
        listItems.clear();
        if(bluetoothAdapter != null) {
            checkPermission("Manifest.permission.BLUETOOTH_CONNECT", 1);
            for (BluetoothDevice device : bluetoothAdapter.getBondedDevices())
                if (device.getType() != BluetoothDevice.DEVICE_TYPE_LE)
                    listItems.add(device);
        }
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        BluetoothDevice device = listItems.get(position-1);
        instance.setBtAddress(device.getAddress());
        //ragment fragment = new TerminalFragment();
        //fragment.setArguments(args);
        //getFragmentManager().beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit();
    }





}
