package com.example.phoneofriends.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Activity.GroupChatBoardActivity;
import com.example.phoneofriends.View.Model.GroupDetail;

import java.util.ArrayList;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<GroupDetail> groupList;

    public GroupListAdapter(Context context, ArrayList<GroupDetail> groupList) {
        this.context = context;
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public GroupListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list, parent, false);
        return new GroupListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupListAdapter.ViewHolder holder, int position) {
        final GroupDetail groupInfo = groupList.get(position);

        String group_name = groupInfo.getGroupName();

        holder.groupNameTV.setText(group_name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupChatBoardActivity.class);
                intent.putExtra("groupInfo", groupInfo);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView groupNameTV;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            groupNameTV = itemView.findViewById(R.id.groupName);
        }
    }
}
