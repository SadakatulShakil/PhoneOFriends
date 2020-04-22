package com.example.phoneofriends.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Adapters.GroupUserAdapter;
import com.example.phoneofriends.View.Adapters.UserListAdapter;
import com.example.phoneofriends.View.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectGroupMemberFragment extends Fragment {
    private FloatingTextButton fabCreateBtn;
    private RecyclerView selectableMemberRecyclerView;
    private ArrayList<User> groupUserInfoList;
    private GroupUserAdapter groupUserAdapter;
    private DatabaseReference groupUserRef;
    private AutoCompleteTextView searchBox;
    private Context context;
    private User user;
    private ArrayList<User> userList;
    private static final String TAG = "SelectMemberFragment";
    public SelectGroupMemberFragment() {
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
        return inflater.inflate(R.layout.fragment_select_group_member, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        groupUserInfoList = new ArrayList<>();
        selectableMemberRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        groupUserAdapter = new GroupUserAdapter(context, groupUserInfoList);
        selectableMemberRecyclerView.setAdapter(groupUserAdapter);



        RetrievedAllSelectableUserData();

        fabCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = new ArrayList<>(groupUserAdapter.getUserList());
                Bundle bundle = new Bundle();
                bundle.putSerializable("memberList", userList);
                Log.d(TAG, "onSelect: UserList"+userList.size());

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateGroupFragment createGroupFragment = new CreateGroupFragment();

                createGroupFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frameForGroupChat, createGroupFragment);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                /* for(int i =0; i< groupUserAdapter.getUserList().size(); i++){

                    Log.d(TAG, "onClick: UserList"+groupUserAdapter.getUserList().get(i));
                }*/
            }
        });
    }

    private void RetrievedAllSelectableUserData() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = firebaseUser.getUid();
        groupUserRef = FirebaseDatabase.getInstance().getReference("User");

        groupUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //userInfoList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    assert user != null;
                    assert firebaseUser != null;
                    if(!user.getUserId().equals(userId)){

                        groupUserInfoList.add(user);
                    }
                }
                groupUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inItView(View view) {

        fabCreateBtn = view.findViewById(R.id.createFabBt);
        selectableMemberRecyclerView = view.findViewById(R.id.selectableMemberRecycleView);

    }
}
