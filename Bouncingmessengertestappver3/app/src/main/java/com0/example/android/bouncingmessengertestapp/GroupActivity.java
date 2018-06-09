package com0.example.android.bouncingmessengertestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.wifidirect.discovery.R;

import java.util.List;

public class GroupActivity extends Activity {
    private RecyclerView groupMembers;
    private LinearLayout layoutManager;
    private Intent intent;
    private String meminfoma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        intent = getIntent();
        List<WiFiP2pService> service = (List<WiFiP2pService>) intent.getSerializableExtra("member_details");
        groupMembers = (RecyclerView) findViewById(R.id.group_members);
        groupMembers.setLayoutManager(new LinearLayoutManager(GroupActivity.this));
        groupMembers.setAdapter(new GroupAdapter(service));
        meminfoma = WiFiServiceDiscoveryActivity.mem_info;
        Log.d("GroupActivity","recycler view group members inflated");
    }
    public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.viewHolder>{
        private List<WiFiP2pService> serviceList;

        public GroupAdapter(List<WiFiP2pService> service){
            this.serviceList=service;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            int layouidforitem = R.layout.group_member_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;
            View view = inflater.inflate(layouidforitem,parent,shouldAttachToParentImmediately);
            Log.d("GroupActivity","view holder created and list item xml inflated");
            //viewHolder groupHolder = new viewHolder(view);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            WiFiP2pService service = serviceList.get(position);
            if(service!=null){
                holder.memberName.setText(service.device.deviceName);
                holder.memInfo.setText(meminfoma);
            }

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class viewHolder extends RecyclerView.ViewHolder{

            TextView memberName;
            TextView memInfo;

            public viewHolder(View itemView) {
                super(itemView);
                memberName = (TextView) findViewById(R.id.member_name);
                memInfo = (TextView) findViewById(R.id.meminfo);
            }
        }

    }
}
