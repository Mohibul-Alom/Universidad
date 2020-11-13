package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TutorialAdapter extends FragmentPagerAdapter {

    public TutorialAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new TutorialFragment();
            case 1:
                return  new fragment_tutorial_2();
            case 2:
                return new fragment_tutorial_3();
            case 3:
                return new frangment_tutorial4();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 4;
    }


}
