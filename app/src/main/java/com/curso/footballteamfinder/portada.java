package com.curso.footballteamfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.curso.footballteamfinder.Objetos.FirebaseReference;
import com.curso.footballteamfinder.Objetos.Jugador;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class portada extends AppCompatActivity {
    private String userID;
    FirebaseAuth.AuthStateListener mauthlistener;
    int salta;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.port);
      //getSupportActionBar().hide();//Ocultar ActivityBar anterior
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bref = database.getReference(FirebaseReference.TEAMS_REFERENCE);

        mauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                System.out.print(user);
                if (user != null){
                    //Aqui podria ir el start activity
                    System.out.println("Sesion Iniciada con mail: "+user.getEmail());
                    //se salta la actividad si se inicio sesion
                   Toast.makeText(portada.this,"Cargando", 2000).show();

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {

                        public void run() {


                            final Intent a2 = new Intent(portada.this,ActividadGeneral.class);
                            finish();
                            startActivity(a2);


                            //here you can start your Activity B.
                        }

                    }, 2000);


                }else {
                    System.out.println("Inicie sesion o Registrese");
                    final Intent a = new Intent(portada.this,Registro.class);
                    Toast.makeText(portada.this,"Cargando", 2000).show();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        public void run() {
                            finish();
                            startActivity(a);
                            //here you can start your Activity B.
                        }

                    }, 2000);
                }
            }
        };
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
}