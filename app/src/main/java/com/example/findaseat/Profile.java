package com.example.findaseat;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Context;
import android.os.Bundle;
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

import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Profile extends Fragment {
    private FirebaseAuth auth;
    private FirebaseDatabase root;
    @Nullable
    public static User currentUser;
    public static DatabaseReference userRef;
    private ValueEventListener userListener;
    private View inf;
    private Context ctx;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        root = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(firebaseAuth -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                String uid = user.getUid();
                userRef = root.getReference("users/" + uid);
                userListener = userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentUser = snapshot.getValue(User.class);
                        if (currentUser != null) {
                            boolean update = Profile.currentUser.updateActiveReservation(LocalTime.now());
                            if (update) userRef.setValue(Profile.currentUser);
                            TextView fullNameView = (TextView) inf.findViewById(R.id.displayFullName);
                            TextView userInfoView = (TextView) inf.findViewById(R.id.displayUserInfo);
                            TextView uscIdView = (TextView) inf.findViewById(R.id.displayUscId);
                            fullNameView.setText(currentUser.getFullName());
                            userInfoView.setText(currentUser.getUsername() + ", " + currentUser.getAffiliation());
                            uscIdView.setText("USC ID: #" + currentUser.getUscId());

                            ReservationListAdapter adapter = new ReservationListAdapter(ctx, currentUser.getReservations());
                            ListView reservationView = inf.findViewById(R.id.reservationView);
                            reservationView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            } else {
                if (userRef != null && userListener != null) {
                    userRef.removeEventListener(userListener);
                    currentUser = null;
                    userRef = null;
                    userListener = null;
                }
            }
        });

        inf = inflater.inflate(R.layout.fragment_profile, container, false);
        return inf;
    }
}