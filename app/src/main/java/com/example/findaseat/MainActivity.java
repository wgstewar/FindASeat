package com.example.findaseat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findaseat.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import com.google.firebase.Firebase;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    private FirebaseDatabase root;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.setUserInputEnabled(false);

        root = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if (pos == 1) {
                    FirebaseUser currentUser = auth.getCurrentUser();
                    if (currentUser != null) {
                        pos = 2;
                    }
                }
                viewPager2.setCurrentItem(pos, false);
             }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        /*viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });*/


//        Intent intent = getIntent();
//        String username = intent.getStringExtra("com.example.username.MESSAGE");
//        if (username == "guest" || username == null){
//            username = "Hello, guest";
//        }
//        else {
//            username = "Hello, " + username;
//        }
//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText(username);

    }

    protected void onDestroy() {
        super.onDestroy();
        root = null;

    }

    public void attemptLogin(View v) {
        EditText enterEmail = (EditText) findViewById(R.id.enterEmail);
        String email = enterEmail.getText().toString();
        EditText enterPassword = (EditText) findViewById(R.id.enterPassword);
        String pwd = enterPassword.getText().toString();

        auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Successful sign-in.",
                                    Toast.LENGTH_SHORT).show();
                            viewPager2.setCurrentItem(2);
                        } else {
                            Toast.makeText(MainActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                            enterPassword.setText("");
                        }
                    }
                });
        /*reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                Log.d("Login", "attempting login as " + username);
                if (!pwd.equals(u.getPassword())) {
                    TextView tv = (TextView) findViewById(R.id.loginTip);
                    tv.setText(R.string.login_failed);
                    tv.setTextColor(Color.RED);
                } else {
                    tabLayout.selectTab(tabLayout.getTabAt(0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });*/
    }

    public void attemptRegister(View v) {
        boolean valid = true;
        EditText usernameEditText = (EditText) findViewById(R.id.enterUsername);
        String username = usernameEditText.getText().toString();

        if (username.isEmpty()) {
            valid = false;
            usernameEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            usernameEditText.setBackgroundColor(Color.WHITE);
        }

        EditText passwordEditText = (EditText) findViewById(R.id.enterPassword);
        String pwd = passwordEditText.getText().toString();
        if (pwd.length() < 6) {
            valid = false;
            passwordEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            passwordEditText.setBackgroundColor(Color.WHITE);
        }

        EditText passwordCopyEditText = (EditText) findViewById(R.id.reenterPassword);
        String pwdCopy = passwordCopyEditText.getText().toString();
        if (!pwd.equals(pwdCopy)) {
            valid = false;
            passwordCopyEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            passwordCopyEditText.setBackgroundColor(Color.WHITE);
        }

        EditText fullNameEditText = (EditText) findViewById(R.id.enterFullName);
        String fullName = fullNameEditText.getText().toString();
        if (fullName.isEmpty()) {

            valid = false;
            fullNameEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            fullNameEditText.setBackgroundColor(Color.WHITE);
        }

        EditText emailEditText = (EditText) findViewById(R.id.enterEmail);
        String email = emailEditText.getText().toString();
        if (email.isEmpty()) {
            valid = false;
            emailEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            emailEditText.setBackgroundColor(Color.WHITE);
        }

        EditText uscIdEditText = (EditText) findViewById(R.id.enterUscId);
        String uscId = uscIdEditText.getText().toString();
        if (uscId.length() != 10) {
            valid = false;
            uscIdEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            uscIdEditText.setBackgroundColor(Color.WHITE);
        }

        Spinner affiliationSpinner = (Spinner) findViewById(R.id.enterAffiliation);
        String affiliation = affiliationSpinner.getSelectedItem().toString();
        Log.d("test", "XDDD" + affiliation);
        if (!valid) {
            TextView tv = (TextView) findViewById(R.id.registerTip);
            tv.setText("Please correct the following fields.");
            tv.setTextColor(Color.RED);
            return;
        }

        auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference reference;
                            reference = root.getReference("users/" + email);
                            User newUser = new User(fullName, uscId, username, affiliation);
                            reference.setValue(newUser);
                            Toast.makeText(MainActivity.this, "Account created !", Toast.LENGTH_SHORT).show();
                            viewPager2.setCurrentItem(2);
                        } else {
                            Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void toRegister(View view) {
        viewPager2.setCurrentItem(3, false);
    }

    public void toLogin(View view) {
        viewPager2.setCurrentItem(1, false);
    }

}