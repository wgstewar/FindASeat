package com.example.findaseat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import com.google.firebase.database.*;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    private FirebaseDatabase root;

//    public void setArguments(Bundle args) {
//        String username;
//        String password;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        root = FirebaseDatabase.getInstance();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
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
        EditText enterUsername = (EditText) findViewById(R.id.enterUsername);
        String username = enterUsername.getText().toString();
        EditText enterPassword = (EditText) findViewById(R.id.enterPassword);
        String pwd = enterPassword.getText().toString();

        DatabaseReference reference;
        reference = root.getReference(
                "users/" + username + "/password"
        );
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String realPwd = snapshot.getValue(String.class);
                Log.d("Login", "attempting login as " + username);
                Log.d("Login", "fetched pwd is " + realPwd);
                if (!pwd.equals(realPwd)) {
                    TextView tv = (TextView) findViewById(R.id.loginTip);
                    tv.setText(R.string.login_failed);
                    tv.setTextColor(Color.RED);
                } else {
                    // TODO: send to profile pg
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // display some error message to user
                TextView tv = (TextView) findViewById(R.id.loginTip);
                tv.setText(R.string.login_failed);
                tv.setTextColor(Color.RED);

                // log it as Warning for developers
                Log.w("SecondFragment", "valueEventListener:onCancelled()",
                        error.toException());
            }
        });
    }

    public void attemptRegister(View v) {
        boolean valid = true;
        String username = findViewById(R.id.enterUsername).toString();
        valid = !username.isEmpty();
        String pwd = findViewById(R.id.enterPassword).toString();
        valid = !pwd.isEmpty();
        String pwdCopy = findViewById(R.id.reenterPassword).toString();


        DatabaseReference reference;
        reference = root.getReference(
                "users/" + username + "/password"
        );
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String realPwd = snapshot.getValue(String.class);
                Log.d("Login", "attempting login as " + username);
                Log.d("Login", "fetched pwd is " + realPwd);
                if (!pwd.equals(realPwd)) {
                    TextView tv = (TextView) findViewById(R.id.loginTip);
                    tv.setText(R.string.login_failed);
                    tv.setTextColor(Color.RED);
                } else {
                    // TODO: send to profile pg
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // display some error message to user
                TextView tv = (TextView) findViewById(R.id.loginTip);
                tv.setText(R.string.login_failed);
                tv.setTextColor(Color.RED);

                // log it as Warning for developers
                Log.w("SecondFragment", "valueEventListener:onCancelled()",
                        error.toException());
            }
        });
    }

    public void toRegister(View view) {
        viewPager2.setCurrentItem(2);
    }

    public void toLogin(View view) {
        viewPager2.setCurrentItem(1);
    }

}