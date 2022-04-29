package com.example.mywhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AyarlarActivity extends AppCompatActivity {

    private Button HesapAyarlariniGuncelle;
    private EditText kullaniciAdi,kullaniciDurumu;
    private CircleImageView kullaniciProfilResmi;

    //firebase
    private FirebaseAuth mYetki;
    private String mevcutKullaniciId;
 private DatabaseReference veriYolu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        //firebase
        mYetki=FirebaseAuth.getInstance();
       veriYolu= FirebaseDatabase.getInstance().getReference();
        mevcutKullaniciId=mYetki.getCurrentUser().getUid();



       //kotrol tanimlamalari
        HesapAyarlariniGuncelle=findViewById(R.id.ayarlari_guncelleme_butonu);
        kullaniciAdi=findViewById(R.id.kullanici_adi_ayarla);
        kullaniciDurumu=findViewById(R.id.profil_durumu_ayarla);
        kullaniciProfilResmi=findViewById(R.id.profil_resmi);

        HesapAyarlariniGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AyarlariGuncelle();
            }
        });
     kullaniciAdi.setVisibility(View.INVISIBLE);
     KullaniciBilgisiAl();

    }

    private void KullaniciBilgisiAl() {

veriYolu.child("Kullanicilar").child(mevcutKullaniciId).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if((dataSnapshot.exists()) && (dataSnapshot.hasChild("ad") && (dataSnapshot.hasChild("resim")))){

            String kullaniciAdiniAl=dataSnapshot.child("ad").getValue().toString();
            String kullaniciDurumuAl=dataSnapshot.child("durum").getValue().toString();
            String kullaniciResimAl=dataSnapshot.child("resim").getValue().toString();


            kullaniciAdi.setText(kullaniciAdiniAl);
            kullaniciDurumu.setText(kullaniciDurumuAl);

        }
        else if((dataSnapshot.exists()) && (dataSnapshot.hasChild("ad"))){


            String kullaniciAdiniAl=dataSnapshot.child("ad").getValue().toString();
            String kullaniciDurumuAl=dataSnapshot.child("durum").getValue().toString();



            kullaniciAdi.setText(kullaniciAdiniAl);
            kullaniciDurumu.setText(kullaniciDurumuAl);

        }
        else{
            kullaniciAdi.setVisibility(View.VISIBLE);
            Toast.makeText(AyarlarActivity.this,"please enter profil info",Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

    }

    private void AyarlariGuncelle() {
        String kullaniciAdiAyarla=kullaniciAdi.getText().toString();
        String kullniciDurumuAyarla=kullaniciDurumu.getText().toString();
        if(TextUtils.isEmpty(kullaniciAdiAyarla)){

            Toast.makeText(this, "please enter your name", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(kullniciDurumuAyarla)){

            Toast.makeText(this, "please write your condition", Toast.LENGTH_LONG).show();
        }
        else{


            HashMap<String,String> profilHaritasi=new HashMap<>();
            profilHaritasi.put("uid",mevcutKullaniciId);
            profilHaritasi.put("ad",kullaniciAdiAyarla);
            profilHaritasi.put("durum",kullniciDurumuAyarla);
            veriYolu.child("Kullanicilar").child(mevcutKullaniciId).setValue(profilHaritasi).
            addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){


                        Toast.makeText(AyarlarActivity.this, "your profil update is success", Toast.LENGTH_LONG).show();
                        Intent anaSayfa=new Intent(AyarlarActivity.this,MainActivity.class);
                        anaSayfa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(anaSayfa);
                        finish();
                    }
                    else {

                        String mesaj=task.getException().toString();
                        Toast.makeText(AyarlarActivity.this, "error "+mesaj, Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }
}