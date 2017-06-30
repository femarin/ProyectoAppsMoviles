package com.curso.footballteamfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {
        Button butregis,butsig;
        EditText email, pass;
        boolean botpres;
        private String userID;
        FirebaseAuth.AuthStateListener mauthlistener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.reg);

            butregis = (Button) findViewById(R.id.botreg);
            butsig = (Button) findViewById(R.id.botsign);
            email = (EditText) findViewById(R.id.login_email);
            pass = (EditText) findViewById(R.id.pass);

            FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
            mauthlistener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null){
                        //Aqui podria ir el start activity
                        System.out.println("Sesion Iniciada con mail: "+user.getEmail());
                        //se salta la actividad si se inicio sesion
                        if (botpres){
                            final Intent a2 = new Intent(Registro.this,ActividadGeneral.class);
                            finish();
                            startActivity(a2);
                        }else {
                            final Intent a = new Intent(Registro.this,MainActivity.class);
                            finish();
                            startActivity(a);
                        }

                    }else {
                        System.out.println("Sesion Cerrada");
                    }
                }
            };
        }
        private void registrar(String email, String contr){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,contr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Registro.this,"Usuario registrado correctamente", 2000).show();
                    }else {
                        Toast.makeText(Registro.this,"Usuario registrado incorrectamente", 2000).show();
                    }
                }
            });
        }

        private void iniciarsesion(String email, String contr){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,contr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Registro.this,"Sesion iniciada correctamente", 2000).show();
                    }else {
                        Toast.makeText(Registro.this,"Usuario y/o contraseña incorrecta", 2000).show();
                    }
                }
            });
        }
        public void onClick(View view){
            switch (view.getId()){
                case R.id.botsign:
                    if (entradasvacias()){
                        String ema = email.getText().toString();
                        String con = pass.getText().toString();
                        iniciarsesion(ema,con);
                        botpres = true;
                    }else {
                        Toast.makeText(this,"Complete ambos campos",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.botreg:
                    if (entradasvacias()){
                        Toast.makeText(this,"Registrando usuario",Toast.LENGTH_SHORT).show();
                        String ema2 = email.getText().toString();
                        String con2 = pass.getText().toString();
                        registrar(ema2,con2);
                        botpres = false;
                    }else {
                        Toast.makeText(this,"Complete ambos campos",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

        @Override
        protected void onStart(){
            super.onStart();
            FirebaseAuth.getInstance().addAuthStateListener(mauthlistener);
        }

        @Override
        protected void onStop() {
            super.onStop();
            if (mauthlistener!=null){
                FirebaseAuth.getInstance().removeAuthStateListener(mauthlistener);
            }
        }

        boolean entradasvacias(){
            if (!email.getText().toString().equals("") && !pass.getText().toString().equals("")){
                return true;
            }else {
                return false;
            }
        }
    }
