package com.example.findaseat.Adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.findaseat.Login;
import com.example.findaseat.Map;
import com.example.findaseat.Profile;
import com.example.findaseat.Register;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return new Map();
            case 1: return new Login();
            case 2: return new Profile();
            case 3: return new Register();
            default: return new Map();
        }
    }

    @Override
    public int getItemCount() {
        return 4 ;
    }
}
