package com.example.mywhatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTablayout;
    private SekmeErisimAdabtor mySekmeErisimAdabtor;


    //firebase
    private  FirebaseUser mevcutKullanici;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar=findViewById(R.id.ana_sayfa_toolbar); //aktive icin kutuphane ekleyebilirsin videoya bak 7 .dk sonra kutuphane eklemedim
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("WhatsApp");


        myViewPager=findViewById(R.id.ana_sekmeler_pager);
        mySekmeErisimAdabtor=new SekmeErisimAdabtor(getSupportFragmentManager());
        myViewPager.setAdapter(mySekmeErisimAdabtor);
        myTablayout=findViewById(R.id.ana_sekmeler);
        myTablayout.setupWithViewPager(myViewPager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mevcutKullanici==null){

            KullaniciyiLoginActiviteyeGonder();
        }
    }

    private void KullaniciyiLoginActiviteyeGonder() {

        Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);

    }
}