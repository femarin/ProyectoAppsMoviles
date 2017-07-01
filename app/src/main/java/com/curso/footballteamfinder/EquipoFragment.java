package com.curso.footballteamfinder;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.curso.footballteamfinder.Objetos.Equipo;
import com.curso.footballteamfinder.Objetos.FirebaseReference;
import com.curso.footballteamfinder.Objetos.Jugador;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class EquipoFragment extends Fragment {
    Button b;
    float valprom = 0;
    int edprom = 0;
    int numjug=1;
    Equipo eq = new Equipo();
    float prom;
    int prom2;
    Vector<Jugador> vj = new Vector<Jugador>();
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_equipo,container,false);
        ConstraintLayout cl = (ConstraintLayout) view.findViewById(R.id.cr);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();
        final DatabaseReference baseref = database.getReference(FirebaseReference.TEAMS_REFERENCE);
        final TextView name = new TextView(getActivity());
        name.setText("Nombre: ");
        name.setTextColor(Color.WHITE);
        name.setX(100);
        name.setY(100);
        final TextView edad = new TextView(getActivity());
        edad.setText("Edad promedio: ");
        edad.setTextColor(Color.WHITE);
        edad.setX(100);
        edad.setY(200);
        final TextView score = new TextView(getActivity());
        score.setText("Valoracion: ");
        score.setTextColor(Color.WHITE);
        score.setX(100);
        score.setY(300);
        final TextView ubic = new TextView(getActivity());
        ubic.setText("Ubicacion: ");
        ubic.setTextColor(Color.WHITE);
        ubic.setX(100);
        ubic.setY(400);
        final TextView col1 = new TextView(getActivity());
        col1.setText("Color 1: ");
        col1.setTextColor(Color.WHITE);
        col1.setX(100);
        col1.setY(500);
        final TextView col2 = new TextView(getActivity());
        col2.setText("Color 2: ");
        col2.setTextColor(Color.WHITE);
        col2.setX(100);
        col2.setY(600);
        b = (Button) view.findViewById(R.id.addteam);
        cl.addView(name);
        cl.addView(edad);
        cl.addView(score);
        cl.addView(ubic);
        cl.addView(col1);
        cl.addView(col2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!b.getText().equals("Editar")){
                    vj.removeAllElements();
                    valprom = 0;
                    edprom = 0;
                    Intent a = new Intent(getActivity(),crearequipo.class);
                    startActivity(a);
                }
                else {
                    vj.removeAllElements();
                    valprom = 0;
                    edprom = 0;
                    Intent a = new Intent(getActivity(),crearequipo.class);
                    startActivity(a);
                }
            }
        });

       baseref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 if (dataSnapshot.hasChild("Equipo")){
                    b.setText("Editar");
                    Equipo e = dataSnapshot.child("Equipo").getValue(Equipo.class);
                    name.setText("Nombre: "+e.getName());
                    getActivity().setTitle(e.getName());
                     e.setScore(prom);
                     e.setAvage(prom2);
                     baseref.child(userid).child("Equipo").setValue(e);
                    ubic.setText("Ubicacion: "+e.getCity());
                    col1.setText("Color 1: "+e.getColor1());
                    col2.setText("Color 2: "+e.getColor2());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        baseref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 1;
                valprom = 0;
                edprom = 0;
                for (DataSnapshot d : dataSnapshot.child("jugadores").getChildren()){
                    Jugador j = d.getValue(Jugador.class);
                    valprom = valprom + j.getValoracion();
                    edprom = edprom + j.getEdad();
                    numjug = i;
                    prom = valprom/numjug;
                    prom2 = edprom/numjug;
                    score.setText("Valorizacion: "+Float.toString(prom));
                    edad.setText("Edad promedio: "+Integer.toString(prom2));
                    i++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
