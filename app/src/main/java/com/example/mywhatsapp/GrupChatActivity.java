package com.example.mywhatsapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;

public class GrupChatActivity extends AppCompatActivity {

    private Toolbar mToolBar;
     private ImageButton MesajGondermeButtonu;
     private EditText kullaniciMesajGirdisi;
     private ScrollView mScrollView;
    private TextView metinMesjlariniGoster;
    //intent degiskeni
    private String mevcutGrupAdi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grup_chat);

        //intenti al
        mevcutGrupAdi=getIntent().getExtras().get("grupAdi").toString();
        Toast.makeText(this, mevcutGrupAdi, Toast.LENGTH_LONG).show();

    //tanimlamalr
        mToolBar=findViewById(R.id.grup_chat_bar_layaut);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(mevcutGrupAdi);

   MesajGondermeButtonu=findViewById(R.id.mesaj_gonderme_buttonu);
   kullaniciMesajGirdisi=findViewById(R.id.grup_mesaji_girdisi);
   metinMesjlariniGoster=findViewById(R.id.grup_chat_metni_gosterme);
   mScrollView=findViewById(R.id.my_scroll_view);




    }
}