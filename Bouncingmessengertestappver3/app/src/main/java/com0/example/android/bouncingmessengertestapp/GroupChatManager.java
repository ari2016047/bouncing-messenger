package com0.example.android.bouncingmessengertestapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import android.os.Handler;
import android.util.Log;

public class GroupChatManager implements Runnable{

    public final int MESSAGE_SIZE = 1048576;
    private static final String TAG = "GroupChatManager";

    private Socket s=null;
    private Handler handler;

    private ArrayList socketList;
    private ArrayList userList;
    private String username;

    private InputStream iStream;
    private OutputStream oStream;

    GroupChatManager(Socket socket, Handler handler,ArrayList sl, ArrayList users){
        this.s = socket;
        this.handler = handler;
        this.socketList = sl;
        this.userList = users;
        socketList.add(s);
    }

    public void run(){
        try{
            iStream = s.getInputStream();
            byte[] buffer = new byte[MESSAGE_SIZE];
            int bytes;
            handler.obtainMessage(WiFiServiceDiscoveryActivity.MY_HANDLE,this)
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

}
