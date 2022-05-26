package com.example.mywhatsapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;


public class GrupChatActivity extends AppCompatActivity {

   private Toolbar mToolBar;
     private ImageButton MesajGondermeButtonu;
     private EditText kullaniciMesajGirdisi;
     private ScrollView mScrollView;
    private TextView metinMesjlariniGoster;
    //intent degiskeni
    private String mevcutGrupAdi,aktifKullaniciId,aktifKullaniciAdi,aktifTarih,aktifZaman;

    //firebase
    private FirebaseAuth mYetki;
    private DatabaseReference kullaniciYolu,grupAdiYolu,grupMesajAnahtariYolu;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grup_chat);

        //intenti al
        mevcutGrupAdi=getIntent().getExtras().get("grupAdi").toString();
        Toast.makeText(this, mevcutGrupAdi, Toast.LENGTH_LONG).show();

        //firebase
        mYetki=FirebaseAuth.getInstance();
        aktifKullaniciId=mYetki.getCurrentUser().getUid();
        kullaniciYolu= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");

        grupAdiYolu= FirebaseDatabase.getInstance().getReference().child("Gruplar").child(mevcutGrupAdi);



    //tanimlamalr
        mToolBar=findViewById(R.id.grup_chat_bar_layaut);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(mevcutGrupAdi);

   MesajGondermeButtonu=findViewById(R.id.mesaj_gonderme_buttonu);
   kullaniciMesajGirdisi=findViewById(R.id.grup_mesaji_girdisi);
   metinMesjlariniGoster=findViewById(R.id.grup_chat_metni_gosterme);
   mScrollView=findViewById(R.id.my_scroll_view);

//kullanici bilgisi alama
        kullaniciBilgisiAL();

        //mesaji veritabanina kayit
        MesajGondermeButtonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesajiVeriTabaninaKaydet();
                kullaniciMesajGirdisi.setText("");
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        grupAdiYolu.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {


                if(dataSnapshot.exists() ){
                    mesajlariGoster(dataSnapshot);
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

                if(dataSnapshot.exists() ){

                    mesajlariGoster(dataSnapshot);

                }}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void mesajiVeriTabaninaKaydet() {
       String mesaj=kullaniciMesajGirdisi.getText().toString();
        String mesajAnahtari=grupAdiYolu.push().getKey();
       if(TextUtils.isEmpty(mesaj)){

           Toast.makeText(this, "please enter a message", Toast.LENGTH_LONG).show();
       }
       else {

           Calendar tarihIcinTak=Calendar.getInstance();
           SimpleDateFormat aktifTarihFormat=new SimpleDateFormat("MMM dd,yyyy");
          aktifTarih=aktifTarihFormat.format(tarihIcinTak.getTime());
          Calendar zamanIcinTak=Calendar.getInstance();
          SimpleDateFormat aktifZamanFormati=new SimpleDateFormat("hh:mm:ss a");
          aktifZaman=aktifZamanFormati.format(zamanIcinTak.getTime());


           HashMap<String,Object> grupMesajAnahtari=new HashMap<>();
           grupAdiYolu.updateChildren(grupMesajAnahtari);

           grupMesajAnahtariYolu=grupAdiYolu.child(mesajAnahtari);

           HashMap<String,Object> mesajBilgisiMap=new HashMap<>();
           mesajBilgisiMap.put("ad",aktifKullaniciAdi);
           mesajBilgisiMap.put("mesaj",mesaj);
           mesajBilgisiMap.put("tarih",aktifTarih);
           mesajBilgisiMap.put("zaman",aktifZaman);

  grupMesajAnahtariYolu.updateChildren(mesajBilgisiMap);



       }



    }

    private void kullaniciBilgisiAL() {

  kullaniciYolu.child(aktifKullaniciId).addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


          if(dataSnapshot.exists()){

              aktifKullaniciAdi=dataSnapshot.child("ad").getValue().toString();
          }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseErrorerror) {

      }
  });

    }


    private void mesajlariGoster(DataSnapshot dataSnapshot) {


        Iterator iterator=dataSnapshot.getChildren().iterator();
        while (iterator.hasNext()){
            String sohbetTarihi=(String) ((DataSnapshot)iterator.next()).getValue();
            String sohbetMesaji=(String) ((DataSnapshot)iterator.next()).getValue();
            String sohbetAdi=(String) ((DataSnapshot)iterator.next()).getValue();
            String sohbetZamani=(String) ((DataSnapshot)iterator.next()).getValue();

            metinMesjlariniGoster.append(sohbetAdi+":\n"+sohbetMesaji+"\n"+sohbetZamani+" "+sohbetTarihi+"\n\n\n");


            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);


        }
    }
    }