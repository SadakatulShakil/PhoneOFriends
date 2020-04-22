package com.example.phoneofriends.View.Fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Activity.GroupChatActivity;
import com.example.phoneofriends.View.Activity.SingleChatBoardActivity;
import com.example.phoneofriends.View.Adapters.UserListAdapter;
import com.example.phoneofriends.View.Model.GroupDetail;
import com.example.phoneofriends.View.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupFragment extends Fragment {
    private FloatingActionButton createNow;
    private Context context;
    private static final String TAG = "GroupFragment";
    private User user;
    private TextView memberCount;
    private ArrayList<User> memberInfoList;
    private RecyclerView memberRecyclerView;
    private UserListAdapter selectedMemberAdapter;
    private GroupDetail groupDetail;
    private FirebaseUser firebaseUser;
    private DatabaseReference groupRef;
    private EditText groupNameET;
    private String member;
    private DatabaseReference groupMemberRef;

    public CreateGroupFragment() {
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
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        memberInfoList = new ArrayList<>();

        Bundle bundle = getArguments();
        if( bundle != null){
            memberInfoList.clear();
            memberInfoList = (ArrayList<User>) bundle.getSerializable("memberList");
            member = String.valueOf(memberInfoList.size());
            memberCount.setText(member);
           /* for(int i = 0; i< memberInfoList.size(); i++){
                memberInfoList.get(i);
                Log.d(TAG, "onSelected: UserList"+memberInfoList.get(i));
            }*/
            selectedMemberAdapter = new UserListAdapter(context, memberInfoList);
            memberRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            memberRecyclerView.setAdapter(selectedMemberAdapter);
            selectedMemberAdapter.notifyDataSetChanged();

        }


        createNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupChatActivity.class);
                startActivity(intent);
                getActivity().finish();

                storeGroupDetail();
            }
        });
    }

    private void storeGroupDetail() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String adminId = firebaseUser.getUid();
        String groupName = groupNameET.getText().toString().trim();
        groupRef = FirebaseDatabase.getInstance().getReference("GroupDetail");
        String groupId = groupRef.push().getKey();

        groupDetail = new GroupDetail(groupId, adminId, groupName, member);

        groupRef.child(groupId).setValue(groupDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Successful.......", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void inItView(View view) {
        createNow = view.findViewById(R.id.createNowtFAB);
        memberCount = view.findViewById(R.id.participantsCount);
        memberRecyclerView = view.findViewById(R.id.selectedMembersRecycleView);
        groupNameET = view.findViewById(R.id.groupName);
    }
}
