package com0.example.android.bouncingmessengertestapp;

/**
 * Created by AVI on 16-01-2018.
 */

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.android.wifidirect.discovery.R;

import java.util.ArrayList;
import java.util.List;
/**
 * This fragment handles chat related UI which includes a list view for messages
 * and a message entry field with send button.
 */
public class WiFiChatFragment extends Fragment {
    private View view;
    private ChatManager chatManager;
    private GroupChatManager groupChatManager;
    private TextView chatLine;
    private ListView listView;
    ChatMessageAdapter adapter = null;
    private List<String> items = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatLine = (TextView) view.findViewById(R.id.txtChatLine);
        listView = (ListView) view.findViewById(android.R.id.list);
        adapter = new ChatMessageAdapter(getActivity(), android.R.id.text1,
                items);
        listView.setAdapter(adapter);
        view.findViewById(R.id.button1).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (groupChatManager != null) {
                            pushMessage( "Me: " + chatLine.getText().toString());
                            groupChatManager.tellToEveryone((WiFiServiceDiscoveryActivity.username+": "+chatLine.getText().toString())
                                    .getBytes());
                            chatLine.setText("");
                            //chatLine.clearFocus();
                        }
                        else if(chatManager != null){
                            pushMessage("Me: " + chatLine.getText().toString());
                            chatManager.write((WiFiServiceDiscoveryActivity.username + ": " + chatLine.getText().toString())
                                    .getBytes());
                            chatLine.setText("");
                        }
                    }
                });
        return view;
    }
    public interface MessageTarget {
        public Handler getHandler();
    }
    public void setGroupChatManager(GroupChatManager obj){
        groupChatManager = obj;
    }
    public void setChatManager(ChatManager obj) {
        chatManager = obj;
    }
    public void pushMessage(String readMessage) {
        adapter.add(readMessage);
        adapter.notifyDataSetChanged();
    }
    public void forEveryone(byte[] buffer){
        if (groupChatManager != null) {
            groupChatManager.tellToEveryone(buffer);
        }
    }
    /**
     * ArrayAdapter to manage chat messages.
     */
    public class ChatMessageAdapter extends ArrayAdapter<String> {
        List<String> messages = null;
        public ChatMessageAdapter(Context context, int textViewResourceId,
                                  List<String> items) {
            super(context, textViewResourceId, items);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(android.R.layout.simple_list_item_1, null);
            }
            String message = items.get(position);
            if (message != null && !message.isEmpty()) {
                TextView nameText = (TextView) v
                        .findViewById(android.R.id.text1);
                if (nameText != null) {
                    nameText.setText(message);
                    if (message.startsWith("Me: ")) {
                        nameText.setTextAppearance(getActivity(),
                                R.style.normalText);
                    } else {
                        nameText.setTextAppearance(getActivity(),
                                R.style.boldText);
                    }
                }
            }
            return v;
        }
    }
}
