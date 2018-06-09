package com0.example.android.bouncingmessengertestapp;

/**
 * Created by AVI on 16-01-2018.
 */

import android.net.wifi.p2p.WifiP2pDevice;

import java.io.Serializable;

/**
 * A structure to hold service information.
 */
public class WiFiP2pService extends WifiP2pDevice implements Serializable{
    WifiP2pDevice device;
    String instanceName = null;
    String serviceRegistrationType = null;
}
