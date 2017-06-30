package com.curso.footballteamfinder;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class JugadoresFragment extends Fragment {
    Button b;
    float valprom = 0;
    int edprom = 0;
    int numjug=1;
    boolean tieneequipo=false;
    Equipo eq = new Equipo();
    Vector<Jugador> vj = new Vector<Jugador>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_jugadores,container,false);
        ConstraintLayout cl = (ConstraintLayout) view.findViewById(R.id.cr);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();
        final DatabaseReference baseref = database.getReference(FirebaseReference.TEAMS_REFERENCE);
        final Vector<TextView> jugs = new Vector<TextView>(5);
        TextView t1 = (TextView) view.findViewById(R.id.tx1);
        t1.setText("Jugador 1"+": "+"\n"+"Nombre: "+"\n"+"Apellido: "+"\n"+"Apodo: "+"\n"+"Valoracion: "+"\n"+"Edad: ");
        jugs.add(0,t1);
        TextView t2 = (TextView) view.findViewById(R.id.tx2);
        t2.setText("Jugador 2"+": "+"\n"+"Nombre: "+"\n"+"Apellido: "+"\n"+"Apodo: "+"\n"+"Valoracion: "+"\n"+"Edad: ");
        jugs.add(1,t2);
        TextView t3 = (TextView) view.findViewById(R.id.tx3);
        t3.setText("Jugador 3"+": "+"\n"+"Nombre: "+"\n"+"Apellido: "+"\n"+"Apodo: "+"\n"+"Valoracion: "+"\n"+"Edad: ");
        jugs.add(2,t3);
        TextView t4 = (TextView) view.findViewById(R.id.tx4);
        t4.setText("Jugador 4"+": "+"\n"+"Nombre: "+"\n"+"Apellido: "+"\n"+"Apodo: "+"\n"+"Valoracion: "+"\n"+"Edad: ");
        jugs.add(3,t4);
        TextView t5 = (TextView) view.findViewById(R.id.tx5);
        t5.setText("Jugador 5"+": "+"\n"+"Nombre: "+"\n"+"Apellido: "+"\n"+"Apodo: "+"\n"+"Valoracion: "+"\n"+"Edad: ");
        jugs.add(4,t5);

        b = (Button) view.findViewById(R.id.addplayer);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seagregajugador(vj.size())){
                    vj.removeAllElements();
                    Intent a = new Intent(getActivity(),crearjugador.class);
                    a.putExtra("ta",numjug);
                    startActivity(a);
                }
            }
        });
        baseref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Equipo")){
                    tieneequipo=true;
                }
                int i = 0;
                valprom = 0;
                edprom = 0;
                vj.removeAllElements();
                for (DataSnapshot d : dataSnapshot.child("jugadores").getChildren()){
                    Jugador j = d.getValue(Jugador.class);
                    vj.add(i,j);
                    valprom = valprom + j.getValoracion();
                    edprom = edprom + j.getEdad();
                    numjug = vj.size();
                    float prom = valprom/numjug;
                    int prom2 = edprom/numjug;
                    jugs.get(i).setText("Jugador "+(i+1)+"\n"+"Nombre: "+j.getNombre()+"\n"+"Apellido: "+j.getApellido()+"\n"+"Apodo: "+j.getApodo()+"\n"+"Valoracion: "+j.getValoracion()+"\n"+"Edad: "+j.getEdad());
                    i++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    boolean seagregajugador(int i){
            if (i<5){
                if (tieneequipo){

                    return true;
                }else {
                    DisplayToast("Debe crear equipo en primer lugar");
                    return false;
                }
            }else {
                DisplayToast("Se ha agregado el maximo de jugadores");
                return false;
            }
    }

    private void DisplayToast(String mensaje) {
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT).show();
    }
}
