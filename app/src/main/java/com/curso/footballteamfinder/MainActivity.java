package com.curso.footballteamfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.curso.footballteamfinder.Objetos.Equipo;
import com.curso.footballteamfinder.Objetos.FirebaseReference;
import com.curso.footballteamfinder.Objetos.Jugador;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    String[] valoraciones;
    int valor;
    Button botjug;
    EditText nomc,edcap,acap,apocap;
    Equipo e = new Equipo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();
        final DatabaseReference baseref = database.getReference(FirebaseReference.TEAMS_REFERENCE);
        final DatabaseReference userref = database.getReference(userid);
        final Vector<Jugador> jugadores = new Vector<Jugador>();
        nomc = (EditText) findViewById(R.id.nomcap);
        acap = (EditText) findViewById(R.id.apcap);
        apocap = (EditText) findViewById(R.id.apodcap);
        edcap = (EditText) findViewById(R.id.edad);

        Spinner s1 = (Spinner) findViewById(R.id.val);
        valoraciones = getResources().getStringArray(R.array.valoraciones);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,valoraciones);
        s1.setAdapter(adapter);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valor = Integer.parseInt(valoraciones[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botjug = (Button) findViewById(R.id.botjugad);
        botjug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entradasvacias()){
                    final Jugador c = new Jugador(nomc.getText().toString(),acap.getText().toString(),apocap.getText().toString(),Integer.parseInt(edcap.getText().toString()),valor);
                    baseref.child(userid).child(FirebaseReference.PLAYERS_REFERENCE).child("Capitan").setValue(c);
                    Intent i = new Intent(MainActivity.this,ActividadGeneral.class);
                    startActivity(i);
                }else {
                    Toast.makeText(MainActivity.this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    boolean entradasvacias(){
        if (!nomc.getText().toString().equals("") && !edcap.getText().toString().equals("") && !acap.getText().toString().equals("") && !apocap.getText().toString().equals("")){
            return true;
        }else {
            return false;
        }
    }
}
