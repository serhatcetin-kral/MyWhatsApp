package com.example.mywhatsapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

public class LoginActivity extends AppCompatActivity {


    private FirebaseUser mevcutKullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mevcutKullanici!=null){
            KullaniciyiAnaAktiviteyeGonder();
        }
    }

    private void KullaniciyiAnaAktiviteyeGonder() {
        Intent AnaAktiviteIntent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(AnaAktiviteIntent);
    }
}