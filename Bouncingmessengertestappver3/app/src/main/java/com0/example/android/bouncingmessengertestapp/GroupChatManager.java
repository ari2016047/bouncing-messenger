package com0.example.android.bouncingmessengertestapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import android.os.Handler;
import android.util.Log;

import static com0.example.android.bouncingmessengertestapp.WiFiServiceDiscoveryActivity.usercode;

public class GroupChatManager implements Runnable{

    public final int MESSAGE_SIZE = 1048576;
    private static final String TAG = "GroupChatManager";

    private Socket s=null;
    private Handler handler;

    private ArrayList socketList;
    private ArrayList userList;
    private String username;
    private WiFiServiceDiscoveryActivity activity;

    private InputStream iStream;
    private OutputStream oStream;

    GroupChatManager(Socket socket, Handler handler,ArrayList sl, ArrayList users){
        this.s = socket;
        this.handler = handler;
        this.socketList = sl;
        this.userList = users;
        try {
            iStream = s.getInputStream();
            byte[] buffer = new byte[MESSAGE_SIZE];
            int bytes;

            String readMessage = new String(buffer);
            if (readMessage.substring(0, 4).equals(usercode)) {
                Log.d(TAG, "got the name of client" + readMessage);
                userList.add(readMessage);
                UpdateUserList();
            }
        }catch (IOException e){
            Log.e(TAG, "failed to get username ", e);
        }
        socketList.add(s);
    }

    public void run(){
        try{
            iStream = s.getInputStream();
            byte[] buffer = new byte[MESSAGE_SIZE];
            int bytes;
            tellToEveryone((WiFiServiceDiscoveryActivity.usercode + WiFiServiceDiscoveryActivity.username).getBytes());
            handler.obtainMessage(WiFiServiceDiscoveryActivity.MY_HANDLE, this)
                    .sendToTarget();
            while(true){
                try{
                    bytes = iStream.read(buffer);

                    if(bytes==-1){
                        break;
                    }

                    handler.obtainMessage(WiFiServiceDiscoveryActivity.MESSAGE_READ,
                            bytes, -1, buffer).sendToTarget();

                }catch (IOException e){
                    Log.e(TAG, "disconnected ", e);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                s.close();
            }catch (IOException e){
                Log.e(TAG, "Exception during write");
            }
        }
    }

    public void tellToEveryone(byte[] buffer){
        Iterator i=socketList.iterator();
        while(i.hasNext())
        {
            try{
                Socket temp=(Socket)i.next();
                oStream = temp.getOutputStream();
                oStream.write(buffer);
                oStream.flush();
                //System.out.println("sent to : "+temp.getPort()+"  : "+ s1);
            }
            catch(Exception e){
                Log.e(TAG,"SEND failed"+e);
            }
        }
    }
    public void UpdateUserList(){
        tellToEveryone(userList.toString().getBytes());
        activity.setUserList(userList.toString());
    }
}
