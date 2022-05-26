package com.example.mywhatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TelefonlaOturumActivity extends AppCompatActivity {


private Button DogrulamaKoduGondermeButtonu,DogrulamaButtonu;
private EditText TelefonNumarasiGirdisi,DogrulamaKoduGirdisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefonla_oturum);

        //tanimlamalar
        DogrulamaKoduGondermeButtonu=findViewById(R.id.dogrulama_kodu_gonder_buttonu);
        DogrulamaButtonu=findViewById(R.id.dogrulama_buttonu);
        TelefonNumarasiGirdisi=findViewById(R.id.telefonla_numarasi_girdi);
        DogrulamaKoduGirdisi=findViewById(R.id.dogrulama_kodu_girdisi);

        DogrulamaKoduGondermeButtonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DogrulamaKoduGondermeButtonu.setVisibility(View.INVISIBLE);
                DogrulamaButtonu.setVisibility(View.VISIBLE);

                TelefonNumarasiGirdisi.setVisibility(View.INVISIBLE);
                DogrulamaKoduGirdisi.setVisibility(View.VISIBLE);

            }
        });







    }
}