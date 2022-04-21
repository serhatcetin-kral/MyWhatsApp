package com.example.mywhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTablayout;
    private SekmeErisimAdabtor mySekmeErisimAdabtor;


    //firebase
    private  FirebaseUser mevcutKullanici;

    private FirebaseAuth mYetki;


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

    //firebase
        mYetki=FirebaseAuth.getInstance();
        mevcutKullanici=mYetki.getCurrentUser();



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);


        getMenuInflater().inflate(R.menu.secenekler_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

if(item.getItemId()==R.id.ana_arkadas_bulma_secenegi){

}
        if(item.getItemId()==R.id.ana_ayarlar_secenegi){

        }
        if(item.getItemId()==R.id.ana_cikis_secenegi){


            mYetki.signOut();
            Intent giris=new Intent(MainActivity.this,LoginActivity.class);
             startActivity(giris);
        }
 return true;
    }
}