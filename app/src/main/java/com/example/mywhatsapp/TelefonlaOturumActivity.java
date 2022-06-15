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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
//

//import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;



public class TelefonlaOturumActivity extends AppCompatActivity {


    private Button DogrulamaKoduGondermeButtonu,DogrulamaButtonu;
    private EditText TelefonNumarasiGirdisi,DogrulamaKoduGirdisi;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String mDogrulamaId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    //FIREBASE
    FirebaseAuth mYetki;
    //yulleniyor penceresi
    private ProgressDialog yuklemeBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefonla_oturum);

        //tanimlamalar
        DogrulamaKoduGondermeButtonu = findViewById(R.id.dogrulama_kodu_gonder_buttonu);
        DogrulamaButtonu = findViewById(R.id.dogrulama_buttonu);
        TelefonNumarasiGirdisi = findViewById(R.id.telefonla_numarasi_girdi);
        DogrulamaKoduGirdisi = findViewById(R.id.dogrulama_kodu_girdisi);


        //progres diolog tanimlama
        yuklemeBar=new ProgressDialog(this);
        mYetki=FirebaseAuth.getInstance();
        mAuth=FirebaseAuth.getInstance();

        DogrulamaKoduGondermeButtonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String telefonNumarasi = TelefonNumarasiGirdisi.getText().toString();

                if (TextUtils.isEmpty(telefonNumarasi)) {

                    Toast.makeText(TelefonlaOturumActivity.this, "phone number can not be empty", Toast.LENGTH_LONG).show();
                } else {


                    //yuklenityor penceresi
                    yuklemeBar.setTitle("verification by phone");
                    yuklemeBar.setMessage("please wait");

                    yuklemeBar.setCanceledOnTouchOutside(false);
                    yuklemeBar.show();

                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(telefonNumarasi)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(TelefonlaOturumActivity.this)                 // Activity (for callback binding)
                                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });

        DogrulamaButtonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DogrulamaKoduGondermeButtonu.setVisibility(View.INVISIBLE);

                TelefonNumarasiGirdisi.setVisibility(View.INVISIBLE);
                String dogrulamaKodu=DogrulamaKoduGirdisi.getText().toString();
                if (TextUtils.isEmpty(dogrulamaKodu)){
                    Toast.makeText(TelefonlaOturumActivity.this,"VERIFICATION KODE CAN NOT BE EMPTY",Toast.LENGTH_LONG).show();

                }else {
                    //yukleniyor penceresi
                    yuklemeBar.setTitle("verification by code");
                    yuklemeBar.setMessage("please wait");
                    yuklemeBar.setCanceledOnTouchOutside(false);
                    yuklemeBar.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mDogrulamaId, dogrulamaKodu);
                    telefonlaGirisYap(credential);

                }
            }
        });
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
            {
     telefonlaGirisYap(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {
               //yukleme penceresi
                yuklemeBar.dismiss();

                Toast.makeText(TelefonlaOturumActivity.this,"invalid phone number! PLEASE ENTER ENTER   WITH COUNTRY CODE",Toast.LENGTH_LONG).show();
                DogrulamaKoduGondermeButtonu.setVisibility(View.VISIBLE);
                DogrulamaButtonu.setVisibility(View.INVISIBLE);

                TelefonNumarasiGirdisi.setVisibility(View.VISIBLE);
                DogrulamaKoduGirdisi.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {


                // Save verification ID and resending token so we can use them later
                mDogrulamaId = verificationId;
                mResendToken = token;

                //yukleme penceresi
                yuklemeBar.dismiss();

                Toast.makeText(TelefonlaOturumActivity.this,"code sent",Toast.LENGTH_LONG).show();
                //gorunurluk ayarlari
                DogrulamaKoduGondermeButtonu.setVisibility(View.INVISIBLE);
                DogrulamaButtonu.setVisibility(View.VISIBLE);

                TelefonNumarasiGirdisi.setVisibility(View.INVISIBLE);
                DogrulamaKoduGirdisi.setVisibility(View.VISIBLE);

            }
        };







    }
    private void telefonlaGirisYap(PhoneAuthCredential credential) {
        mYetki.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            yuklemeBar.dismiss();
                            Toast.makeText(TelefonlaOturumActivity.this, "accounted is created", Toast.LENGTH_LONG).show();
                             KullaniciyiAnaSayfayaGonder();
                        } else {


                            String hataMesaji=task.getException().toString();
                            Toast.makeText(TelefonlaOturumActivity.this,"hata:"+hataMesaji,Toast.LENGTH_LONG).show();

                            }
                        }

                });
    }

    private void KullaniciyiAnaSayfayaGonder() {

        Intent anasayfa=new Intent(TelefonlaOturumActivity.this,MainActivity.class);
        anasayfa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(anasayfa);
        finish();
    }
}