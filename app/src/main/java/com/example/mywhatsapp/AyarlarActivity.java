package com.example.mywhatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class AyarlarActivity extends AppCompatActivity {

    private Button HesapAyarlariniGuncelle;
    private EditText kullaniciAdi,kullaniciDurumu;
    private CircleImageView kullaniciProfilResmi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
       //kotrol tanimlamalari
        HesapAyarlariniGuncelle=findViewById(R.id.ayarlari_guncelleme_butonu);
        kullaniciAdi=findViewById(R.id.kullanici_adi_ayarla);
        kullaniciDurumu=findViewById(R.id.profil_durumu_ayarla);
        kullaniciProfilResmi=findViewById(R.id.profil_resmi);


    }
}