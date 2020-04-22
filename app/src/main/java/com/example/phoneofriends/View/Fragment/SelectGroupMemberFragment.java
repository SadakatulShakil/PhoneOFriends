package com.example.phoneofriends.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.phoneofriends.R;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectGroupMemberFragment extends Fragment {
    private FloatingTextButton fabCreateBtn;
    private RecyclerView selectableMember;
    private AutoCompleteTextView searchBox;
    private Context context;
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
        fabCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameForGroupChat, new CreateGroupFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });
    }

    private void inItView(View view) {

        fabCreateBtn = view.findViewById(R.id.createFabBt);
    }
}
