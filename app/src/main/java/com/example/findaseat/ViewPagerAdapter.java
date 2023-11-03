package com.example.findaseat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("tag", "kill yourself diew " + position);
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
