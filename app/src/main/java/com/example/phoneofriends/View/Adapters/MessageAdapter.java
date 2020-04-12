package com.example.phoneofriends.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Activity.SingleChatBoardActivity;
import com.example.phoneofriends.View.Model.SingleChats;
import com.example.phoneofriends.View.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private ArrayList<SingleChats> chatList;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, ArrayList<SingleChats> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_LEFT){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_left, parent, false);
            return new MessageAdapter.ViewHolder(v);
        }
        else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_right, parent, false);
            return new MessageAdapter.ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        SingleChats singleChats = chatList.get(position);

        String time = singleChats.getSendingTime();
        String date = singleChats.getSendingDate();

        String dateAndTime = date + ", " + time;
        holder.showChat.setText(singleChats.getMessages());
        holder.timeAndTide.setText(dateAndTime);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView showChat , timeAndTide;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showChat = itemView.findViewById(R.id.show_message);
            timeAndTide = itemView.findViewById(R.id.dateAndTime);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSenderId().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else { return MSG_TYPE_LEFT;}
    }
}

