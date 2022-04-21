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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

public class LoginActivity extends AppCompatActivity {

   private Button girisButonu,telefonlaGirisButonu;
   private EditText KullaniciMail,KullaniciSifre;
   private TextView YeniHesapAlma,SifreUnutmaBaglanti;

   //firebase
    //private FirebaseUser mevcutKullanici;
    private FirebaseAuth mYetki;

    //dialog
    ProgressDialog girisDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //kotrol tanimlama
        girisButonu=findViewById(R.id.giris_butonu);
        telefonlaGirisButonu=findViewById(R.id.telefonla_giris_butonu);
        KullaniciMail=findViewById(R.id.giris_email);
        KullaniciSifre=findViewById(R.id.giris_sifre);
        YeniHesapAlma=findViewById(R.id.yeni_hesap_alma);
        SifreUnutmaBaglanti=findViewById(R.id.sifre_unutma_baglantisi);

        //firabase
        mYetki=FirebaseAuth.getInstance();
       // mevcutKullanici=mYetki.getCurrentUser();

        //progressdialog
        girisDialog=new ProgressDialog(this);

        YeniHesapAlma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent kayitAktivityIntent=new Intent(LoginActivity.this,KayitAktivity.class);
                startActivity(kayitAktivityIntent);
            }
        });

        girisButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KullaniciyaGirisIzniVer();
            }
        });
    }

    private void KullaniciyaGirisIzniVer() {

        String email=KullaniciMail.getText().toString();
        String sifre=KullaniciSifre.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email can not be embty", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(sifre)){
            Toast.makeText(this, "pasword can not be embty", Toast.LENGTH_SHORT).show();
        }
        else {

            //PROGRESS DIALOG
 girisDialog.setTitle("logining");
 girisDialog.setMessage("please wait");
 girisDialog.setCanceledOnTouchOutside(true);
girisDialog.show();



   mYetki.signInWithEmailAndPassword(email,sifre)
           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {

     if(task.isSuccessful()){

         Intent anasayfa=new Intent(LoginActivity.this,MainActivity.class);
         anasayfa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         startActivity(anasayfa);
         finish();
         Toast.makeText(LoginActivity.this, "login is success", Toast.LENGTH_SHORT).show();

girisDialog.dismiss();

     }
     else {

         String mesaj=task.getException().toString();
         Toast.makeText(LoginActivity.this, "error "+mesaj+" login is success", Toast.LENGTH_SHORT).show();
girisDialog.dismiss();
     }


               }
           });
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(mevcutKullanici!=null){
//            KullaniciyiAnaAktiviteyeGonder();
//        }
//    }

    private void KullaniciyiAnaAktiviteyeGonder() {
        Intent AnaAktiviteIntent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(AnaAktiviteIntent);
    }
}