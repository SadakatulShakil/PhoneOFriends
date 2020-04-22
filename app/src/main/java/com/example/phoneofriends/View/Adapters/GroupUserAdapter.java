package com.example.phoneofriends.View.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Model.User;

import java.util.ArrayList;
import java.util.List;

public class GroupUserAdapter extends RecyclerView.Adapter<GroupUserAdapter.ViewHolder> {
    private static final String TAG = "GroupUserAdapter";
    private Context context;
    private ArrayList<User> groupUserArrayList;
    private List<User> userList = new ArrayList<>();





    public GroupUserAdapter(Context context, ArrayList<User> groupUserArrayList) {
        this.context = context;
        this.groupUserArrayList = groupUserArrayList;
    }

    @NonNull
    @Override
    public GroupUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectable_user_list, parent, false);
        return new GroupUserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupUserAdapter.ViewHolder holder, int position) {
        final User userInfo = groupUserArrayList.get(position);

        String name = userInfo.getUserName();
        final String contact = userInfo.getUserContact();

        holder.nameTV.setText(name);
        holder.contactTV.setText(contact);

        /// Here checkBox task should be done///

        holder.selectBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.selectBox.isChecked()){
                    addUser(userInfo);
                }else {
                    deleteUser(userInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupUserArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTV, contactTV;
        private CheckBox selectBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.userName);
            contactTV = itemView.findViewById(R.id.userContact);
            selectBox = itemView.findViewById(R.id.checkBox);

        }
    }
    public void addUser(User user) {
        Log.d(TAG, "addUser: "+user.toString());
        userList.add(user);
    }

    public void deleteUser(User user){
        Log.d(TAG, "deleteUser: ok");
        userList.remove(user);
    }

    public List<User> getUserList() {
        return userList;
    }




}
