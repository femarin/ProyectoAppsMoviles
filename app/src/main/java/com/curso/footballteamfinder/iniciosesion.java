package com.curso.footballteamfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class iniciosesion extends AppCompatActivity {
    Button butregis,butsig;
    EditText email, pass;

    private String userID;
    FirebaseAuth.AuthStateListener mauthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion);

        butregis = (Button) findViewById(R.id.botreg);
        butsig = (Button) findViewById(R.id.botsign);
        email = (EditText) findViewById(R.id.login_email);
        pass = (EditText) findViewById(R.id.pass);

        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        userID=user.getUid();
        mauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //Aqui podria ir el start activity
                    System.out.println("Sesion Iniciada con mail: "+user.getEmail());
                    //se salta la actividad si se inicio sesion
                    Intent a = new Intent(iniciosesion.this,MainActivity.class);
                    a.putExtra("usuario",userID);
                    startActivity(a);
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
                    System.out.println("Usuario creado correctamente");
                }else {
                    System.out.println(task.getException().getMessage()+"");
                }
            }
        });
    }

    private void iniciarsesion(String email, String contr){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,contr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    System.out.println("Usuario creado correctamente");
                }else {
                    System.out.println(task.getException().getMessage()+"");
                }
            }
        });;
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.botsign:
                String ema = email.getText().toString();
                String con = pass.getText().toString();
                iniciarsesion(ema,con);
                break;
            case R.id.botreg:
                String ema2 = email.getText().toString();
                String con2 = pass.getText().toString();
                registrar(ema2,con2);
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
}
