package com0.example.android.bouncingmessengertestapp;

/**
 * Created by AVI on 16-01-2018.
 */

import android.os.Handler;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Handles reading and writing of messages with socket buffers. Uses a Handler
 * to post messages to UI thread for UI updates.
 */
public class ChatManager implements Runnable {
    private Socket socket = null;
    private Handler handler;
    private String userName;
    private WiFiServiceDiscoveryActivity activity;

    public final int MESSAGE_SIZE = 1048576;


    public ChatManager(Socket socket, Handler handler){
        this.socket = socket;
        this.handler = handler;
    }

    private InputStream iStream;
    private OutputStream oStream;
    private static final String TAG = "ChatManager";

    @Override
    public void run() {
        try {
            iStream = socket.getInputStream();
            oStream = socket.getOutputStream();
            byte[] buffer = new byte[MESSAGE_SIZE];
            int bytes;
            write((WiFiServiceDiscoveryActivity.usercode + WiFiServiceDiscoveryActivity.username).getBytes());

            handler.obtainMessage(WiFiServiceDiscoveryActivity.MY_HANDLE, this)
                    .sendToTarget();
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = iStream.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    // Send the obtained bytes to the UI Activity
                    Log.d(TAG, "Rec:" + String.valueOf(buffer));
                    String temp = new String(buffer);
                    Log.d(TAG,"Rec: " + temp);

                    handler.obtainMessage(WiFiServiceDiscoveryActivity.MESSAGE_READ,
                                bytes, -1, buffer).sendToTarget();

                } catch (IOException e) {
                    Log.e(TAG, "disconnected ", e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void write(byte[] buffer) {
        try {
            oStream.write(buffer);
        } catch (IOException e) {
            Log.e(TAG, "Exception during write", e);
        }
    }
}