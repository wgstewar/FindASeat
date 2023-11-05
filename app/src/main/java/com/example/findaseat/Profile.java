package com.example.findaseat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.findaseat.Adapters.ReservationListAdapter;
import com.example.findaseat.Classes.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class Profile extends Fragment {
    private FirebaseAuth auth;
    private FirebaseDatabase root;
    @Nullable
    public static User u;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(firebaseAuth -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                String uid = user.getUid();
                DatabaseReference userRef = root.getReference("users/" + uid);
                CountDownLatch doneLoad = new CountDownLatch(1);
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        u = snapshot.getValue(User.class);
                        if (u != null) {
                            TextView fullNameView = (TextView) getActivity().findViewById(R.id.displayFullName);
                            TextView userInfoView = (TextView) getActivity().findViewById(R.id.displayUserInfo);
                            TextView uscIdView = (TextView) getActivity().findViewById(R.id.displayUscId);
                            fullNameView.setText(u.getFullName());
                            userInfoView.setText(u.getUsername() + ", " + u.getAffiliation());
                            uscIdView.setText("USC ID: #" + u.getUscId());

                            ReservationListAdapter adapter = new ReservationListAdapter(getActivity(), u.getReservations());
                            ListView reservationView = getActivity().findViewById(R.id.reservationView);
                            reservationView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });

        View inf = inflater.inflate(R.layout.fragment_profile, container, false);
        return inf;
    }
}