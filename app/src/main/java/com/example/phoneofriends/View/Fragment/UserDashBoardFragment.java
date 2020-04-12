package com.example.phoneofriends.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Activity.GroupChatActivity;
import com.example.phoneofriends.View.Activity.SingleChatActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDashBoardFragment extends Fragment {
    private Context context;
    private CardView singleChatView, groupChatView, globalChatView, profileView;
    public UserDashBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        singleChatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, SingleChatActivity.class);
                context.startActivity(intent);
            }
        });

        groupChatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, GroupChatActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void inItView(View view) {
        singleChatView = view.findViewById(R.id.chatStartButton);
        groupChatView = view.findViewById(R.id.groupChatButton);
        globalChatView = view.findViewById(R.id.globalGroupButton);
        profileView = view.findViewById(R.id.profileButton);
    }
}
