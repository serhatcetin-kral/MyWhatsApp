package com.example.mywhatsapp;
//import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


  class SekmeErisimAdabtor  extends FragmentPagerAdapter {
    public SekmeErisimAdabtor(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                ChatFragment chatFragment=new ChatFragment();
                return chatFragment;
            case 1:
                GruplarFragment  gruplarFragment=new GruplarFragment();
                return gruplarFragment;
            case 2:
                KisilerFragment kisilerFragment=new KisilerFragment();
                return kisilerFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:

                return "SOHBETLER";
            case 1:

                return "Gruplar";
            case 2:

                return "kisiler";
            default:
                return null;
        }
    }
}