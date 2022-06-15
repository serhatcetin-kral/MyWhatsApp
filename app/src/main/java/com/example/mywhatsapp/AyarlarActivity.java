package com.example.mywhatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.Manifest;
import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;



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
 //resim secme
    private static int GaleriSecme=1;
    private static int PICK_IMAGE=1;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        //firebase
        mYetki = FirebaseAuth.getInstance();
        veriYolu = FirebaseDatabase.getInstance().getReference();
        mevcutKullaniciId = mYetki.getCurrentUser().getUid();


        //kotrol tanimlamalari
        HesapAyarlariniGuncelle = findViewById(R.id.ayarlari_guncelleme_butonu);
        kullaniciAdi = findViewById(R.id.kullanici_adi_ayarla);
        kullaniciDurumu = findViewById(R.id.profil_durumu_ayarla);
        kullaniciProfilResmi = findViewById(R.id.profil_resmi);

        HesapAyarlariniGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AyarlariGuncelle();
            }
        });
        kullaniciAdi.setVisibility(View.INVISIBLE);
        KullaniciBilgisiAl();


        ///////////////////////////////////////////////////////////resim


     kullaniciProfilResmi.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {



//tikladiginda galeriyi ac
//Intent galeriIntent=new Intent();
//galeriIntent.setAction(Intent.ACTION_GET_CONTENT);
//galeriIntent.setType("image/*");
//startActivityForResult(galeriIntent,GaleriSecme);

             //   bu kod resim secmek ve crop icin
             CropImage.startPickImageActivity(AyarlarActivity.this);


         }
     });

    }
//resim secme kodu
@RequiresApi(api = Build.VERSION_CODES.M)
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK)
    {

        Uri imageuri=CropImage.getPickImageResultUri(this,data);
        if(CropImage.isReadExternalStoragePermissionsRequired(this,imageuri))
        {
            uri=imageuri;
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }else {

            startCrop(imageuri);
        }

    }
    if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
    {
        CropImage.ActivityResult result=CropImage.getActivityResult(data);
        if(requestCode==RESULT_OK)
        {
            kullaniciProfilResmi.setImageURI(result.getUri());
            Toast.makeText(this, "profil picture update is successfully", Toast.LENGTH_LONG).show();
        }
    }
}

    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri).setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);


    }
    ///////////////


//bu kisim ise yaramadi ama tutuyom

//   @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//if(requestCode==GaleriSecme && requestCode==RESULT_OK && data!=null){
//
//   Uri ResimUri=data.getData();
//
//
//  CropImage.activity()
//          .setDuidelines(CropImage.Guidelines.ON)
//          .setAspectRatio(1,1)
//          .start(this);
//
//
//}
//
//if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
//{
//
//
//    CropImage.ActivityResult result=CropImage.getActivityResult(data);
//
//
//}
//
//    }
////////////////////////////////////////////////resim

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