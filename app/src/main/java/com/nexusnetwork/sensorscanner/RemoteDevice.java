package com.nexusnetwork.sensorscanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.ArrayAdapter;

import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.Collections;

public class RemoteDevice extends ListFragment {

    private BluetoothAdapter bluetoothAdapter;
    private final ArrayList<BluetoothDevice> listItems = new ArrayList<>();
    private ArrayAdapter<BluetoothDevice> listAdapter;


//    @Override
//    public void onResume() {
//        super.onResume();
//        if(bluetoothAdapter == null)
//            setEmptyText("<bluetooth not supported>");
//        else if(!bluetoothAdapter.isEnabled())
//            setEmptyText("<bluetooth is disabled>");
//        else
//            setEmptyText("<no bluetooth devices found>");
//        refresh();
//    }
//
//    void refresh() {
//        listItems.clear();
//        if(bluetoothAdapter != null) {
//            for (BluetoothDevice device : bluetoothAdapter.getBondedDevices())
//                if (device.getType() != BluetoothDevice.DEVICE_TYPE_LE)
//                    listItems.add(device);
//        }
//        Collections.sort(listItems, DevicesFragment::compareTo);
//        listAdapter.notifyDataSetChanged();
//    }


}
