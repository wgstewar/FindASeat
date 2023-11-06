package com.example.findaseat;

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
import com.example.findaseat.Adapters.IntervalListAdapter;
import com.example.findaseat.Classes.*;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;


public class Booking extends Fragment {

    ArrayList<int> shoppingCart;

    private FirebaseAuth auth;
    private FirebaseDatabase root;
    @Nullable
    public static User currentUser;
    public static DatabaseReference userRef;
    private ValueEventListener userListener;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
                            TextView fullNameView = (TextView) getActivity().findViewById(R.id.displayFullName);
                            TextView userInfoView = (TextView) getActivity().findViewById(R.id.displayUserInfo);
                            TextView uscIdView = (TextView) getActivity().findViewById(R.id.displayUscId);
                            fullNameView.setText(currentUser.getFullName());
                            userInfoView.setText(currentUser.getUsername() + ", " + currentUser.getAffiliation());
                            uscIdView.setText("USC ID: #" + currentUser.getUscId());

                            ReservationListAdapter adapter = new ReservationListAdapter(getActivity(), currentUser.getReservations());
                            ListView reservationView = getActivity().findViewById(R.id.reservationView);
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

        View inf = inflater.inflate(R.layout.fragment_profile, container, false);
        return inf;
    }
}