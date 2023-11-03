package com.example.findaseat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.findaseat.Classes.User;
import com.google.firebase.Firebase;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

public class Profile extends Fragment {
    private FirebaseAuth auth;
    private FirebaseDatabase root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(firebaseAuth -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                String username = user.getDisplayName();
                DatabaseReference reference = root.getReference("users/" + username);
                Log.d("here", username + "huhhh");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User u = snapshot.getValue(User.class);
                        Log.d("testtest", username + "huhhh");
                        if (u != null) {
                            TextView fullNameView = (TextView) getActivity().findViewById(R.id.displayFullName);
                            TextView usernameView = (TextView) getActivity().findViewById(R.id.displayUsername);
                            fullNameView.setText(u.getFullName());
                            usernameView.setText(u.getUsername());
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