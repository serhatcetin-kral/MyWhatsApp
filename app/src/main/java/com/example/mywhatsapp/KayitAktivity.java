package com.example.mywhatsapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KayitAktivity extends AppCompatActivity {


    private  Button KayitOlusturmaButonu;
    private EditText KullaniciMail,KullaniciSifre;
    private TextView zatenHesabimVar;
//fire base
    private  FirebaseAuth mYetki;
   private DatabaseReference kokReference;

     private ProgressDialog yukleniyorDiolag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        //firebase
        mYetki=FirebaseAuth.getInstance();
        kokReference=FirebaseDatabase.getInstance().getReference();


        KayitOlusturmaButonu=findViewById(R.id.kayit_butonu);
        KullaniciMail=findViewById(R.id.kayit_email);
        KullaniciSifre=findViewById(R.id.kayit_sifre);

        zatenHesabimVar=findViewById(R.id.zaten_hesap_var);
        yukleniyorDiolag=new ProgressDialog(this);
        zatenHesabimVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginAktivityIntent=new Intent(KayitAktivity.this,LoginActivity.class);
                startActivity(loginAktivityIntent);
            }
        });


        KayitOlusturmaButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YeniHesapOlustur();
            }
        });



    }

    private void YeniHesapOlustur() {

        String email=KullaniciMail.getText().toString();
        String sifre=KullaniciSifre.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email can not be empty", Toast.LENGTH_LONG).show();

        }
        if(TextUtils.isEmpty(sifre)){
            Toast.makeText(this, "Password can not be empty", Toast.LENGTH_LONG).show();
        }
        else{
               yukleniyorDiolag.setTitle("crreating new account");
               yukleniyorDiolag.setMessage("please wait");
               yukleniyorDiolag.setCanceledOnTouchOutside(true);
               yukleniyorDiolag.show();

            mYetki.createUserWithEmailAndPassword(email,sifre)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                String mevcutKullaniciId=mYetki.getCurrentUser().getUid();
                                kokReference.child("Kullanicilar").child(mevcutKullaniciId).setValue("");



                                Intent anasayfa=new Intent(KayitAktivity.this,MainActivity.class);
                                anasayfa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//geriye basildigi zaman kayit sayfasina gitmemesi icin
                                startActivity(anasayfa);
                                    finish();
                                Toast.makeText(KayitAktivity.this, "successful NEW ACCOUNT CREATED", Toast.LENGTH_LONG).show();
                           yukleniyorDiolag.dismiss();
                            }
                            else {

                                String mesaj=task.getException().toString();
                                Toast.makeText(KayitAktivity.this, "error "+mesaj+" please check your info", Toast.LENGTH_LONG).show();
yukleniyorDiolag.dismiss();
                            }
                        }
                    });

        }
    }
}