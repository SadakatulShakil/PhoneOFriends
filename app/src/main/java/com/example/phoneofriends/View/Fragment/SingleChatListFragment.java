package com.example.phoneofriends.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Adapters.UserListAdapter;
import com.example.phoneofriends.View.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleChatListFragment extends Fragment {
    private Context context;
    private AutoCompleteTextView bloodSearchBox;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private User user;
    private RecyclerView recyclerView;
    private ArrayList<User> userInfoList;
    private UserListAdapter appUserAdapter;
    DatabaseReference userRef;
    private static final String TAG = "SingleChatListFragment";
    public SingleChatListFragment() {
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
        return inflater.inflate(R.layout.fragment_single_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        userInfoList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        appUserAdapter = new UserListAdapter(context, userInfoList);
        recyclerView.setAdapter(appUserAdapter);

        RetrievedAllUserData();


       /* bloodSearchBox.setThreshold(1);
        bloodSearchBox.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.districts)));*/


    }

    private void RetrievedAllUserData() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = firebaseUser.getUid();
        userRef = FirebaseDatabase.getInstance().getReference("User");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //userInfoList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    assert user != null;
                    assert firebaseUser != null;
                    if(!user.getUserId().equals(userId)){

                        userInfoList.add(user);

                    }
                }
                appUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void inItView(View view) {
        bloodSearchBox = view.findViewById(R.id.searchBox);
        recyclerView = view.findViewById(R.id.userListRecycleView);
    }
}
