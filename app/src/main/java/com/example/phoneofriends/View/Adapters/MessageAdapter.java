package com.example.phoneofriends.View.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Model.SingleChats;
import com.example.phoneofriends.View.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private ArrayList<SingleChats> chatList;
    private ArrayList<User> userInfoList;
    DatabaseReference userRef;
    String name = "";
    FirebaseUser firebaseUser;
    private User user;

    private static final String TAG = "MessageAdapter";
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
    public void onBindViewHolder(@NonNull final MessageAdapter.ViewHolder holder, final int position) {
        final SingleChats singleChats = chatList.get(position);

        final String time = singleChats.getSendingTime();
        final String date = singleChats.getSendingDate();
        final String receiverId = singleChats.getReceiverId();

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = firebaseUser.getUid();
        userRef = FirebaseDatabase.getInstance().getReference("User");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //userInfoList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);

                        if(user.getUserId().equals(chatList.get(position).getReceiverId()) &&
                                !userId.equals(chatList.get(position).getReceiverId()) ||
                                user.getUserId().equals(chatList.get(position).getSenderId()) &&
                                !userId.equals(chatList.get(position).getSenderId())){
                            name = user.getUserName();
                            holder.receiverName.setText(name);
                            Log.d(TAG, "onUserNameCheck: userName :" + name);
                        }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        String dateAndTime = date + ", " + time;
        holder.showChat.setText(singleChats.getMessages());
        holder.timeAndTide.setText(dateAndTime);
        holder.receiverName.setText(name);
        //Log.d(TAG, "onUserNameCheck: userName :" + name);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView showChat , timeAndTide, receiverName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showChat = itemView.findViewById(R.id.show_message);
            timeAndTide = itemView.findViewById(R.id.dateAndTime);
            receiverName = itemView.findViewById(R.id.senderName);
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

