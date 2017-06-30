package com.curso.footballteamfinder;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.curso.footballteamfinder.Objetos.Equipo;
import com.curso.footballteamfinder.Objetos.FirebaseReference;
import com.curso.footballteamfinder.Objetos.Jugador;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class contactajugador extends AppCompatActivity {
    Button botSO, botll;
    Equipo q;
    Jugador cap;
    TextView neq,ceq,valeq,edpr,nomcap,coleq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rel);
        Bundle b =  getIntent().getExtras();
        String er = b.getString("equiporival");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference baseref = database.getReference(FirebaseReference.TEAMS_REFERENCE);
        neq = (TextView) findViewById(R.id.tx1);
        valeq = (TextView) findViewById(R.id.tx2);
        ceq = (TextView) findViewById(R.id.tx3);
        edpr = (TextView) findViewById(R.id.tx4);
        nomcap = (TextView) findViewById(R.id.tx5);
        coleq = (TextView) findViewById(R.id.coltex);
        baseref.child(er).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Equipo")){
                     q = dataSnapshot.child("Equipo").getValue(Equipo.class);
                    neq.setText("Nombre Equipo: "+q.getName());
                    valeq.setText("Valorizacion: "+String.valueOf(q.getScore()));
                    ceq.setText("Ciudad: "+q.getCity());
                    edpr.setText("Edad Promedio: "+String.valueOf(q.getAvage()));
                    coleq.setText("Color 1: "+q.getColor1()+"\n"+"Color 2: "+q.getColor2());
                    for (DataSnapshot d :
                            dataSnapshot.child("jugadores").getChildren()) {
                        if(d.getKey().equals("Capitan")){
                            cap = d.getValue(Jugador.class);
                            nomcap.setText("Nombre Capitan: "+cap.getNombre());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        botSO = (Button) findViewById(R.id.b1);
        botSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        botll = (Button) findViewById(R.id.bllamar);
        botll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+q.getNumcontacto()));
                finish();
                startActivity(intent);
            }
        });




    }
}
