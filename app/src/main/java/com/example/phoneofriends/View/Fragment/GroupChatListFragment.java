package com.example.phoneofriends.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Adapters.GroupListAdapter;
import com.example.phoneofriends.View.Adapters.UserListAdapter;
import com.example.phoneofriends.View.Model.GroupDetail;
import com.example.phoneofriends.View.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupChatListFragment extends Fragment {

    private FloatingTextButton fabBtn;
    private RecyclerView groupNameRecyclerView;
    private GroupListAdapter groupListAdapter;
    private ArrayList<GroupDetail> mGroupList;
    private Context context;
    private DatabaseReference groupNameRef;
    private DatabaseReference groupMemberRef;
    public GroupChatListFragment() {
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
        return inflater.inflate(R.layout.fragment_group_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        mGroupList = new ArrayList<>();
        groupNameRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        groupListAdapter = new GroupListAdapter(context, mGroupList);
        groupNameRecyclerView.setAdapter(groupListAdapter);

        retrieveGroupName();

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameForGroupChat, new SelectGroupMemberFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        //retrieveGroupMember();
    }

    private void retrieveGroupMember() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = firebaseUser.getUid();
        groupMemberRef = FirebaseDatabase.getInstance().getReference("Members");

        groupMemberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void retrieveGroupName() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = firebaseUser.getUid();
        groupNameRef = FirebaseDatabase.getInstance().getReference("GroupDetail");

        groupNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    GroupDetail groupDetail = userSnapshot.getValue(GroupDetail.class);

                    if(userId.equals(groupDetail.getAdminId())){

                        mGroupList.add(groupDetail);
                    }

                    }

                groupListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inItView(View view) {
        fabBtn = view.findViewById(R.id.groupFabBt);
        groupNameRecyclerView = view.findViewById(R.id.groupNameRecycleView);
    }
}
