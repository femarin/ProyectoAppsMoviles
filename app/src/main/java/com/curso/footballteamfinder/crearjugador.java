package com.curso.footballteamfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
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


public class crearjugador extends AppCompatActivity {
    String[] valoraciones;
    int val;
    EditText njug,ejug,ajug,apjug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creajugadorxml);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();
        final DatabaseReference baseref = database.getReference(FirebaseReference.TEAMS_REFERENCE);
        Bundle bun = getIntent().getExtras();
        final int i = bun.getInt("ta");
        Spinner s1 = (Spinner) findViewById(R.id.vlx);
        valoraciones = getResources().getStringArray(R.array.valoraciones);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,valoraciones);

        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                val = (Integer.parseInt(valoraciones[position]));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        njug = (EditText) findViewById(R.id.nomju);
        ejug = (EditText) findViewById(R.id.edju);
        ajug = (EditText) findViewById(R.id.apju);
        apjug = (EditText) findViewById(R.id.apodju);
        Button b = (Button) findViewById(R.id.botcrj);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seagregajugador(i)){
                    Jugador j = new Jugador();
                    j.setNombre(njug.getText().toString());
                    j.setApellido(ajug.getText().toString());
                    j.setApodo(apjug.getText().toString());
                    j.setEdad(Integer.parseInt(ejug.getText().toString()));
                    j.setValoracion(val);
                    baseref.child(userid).child("jugadores").child("Jugador"+(i)).setValue(j);
                    finish();
                }
            }
        });
    }


    boolean seagregajugador(int i){
        if (!njug.getText().toString().equals("") && !ajug.getText().toString().equals("") &&  !ejug.getText().toString().equals("") && !apjug.getText().toString().equals("")){
            if (i<=5){
                return true;
            }else {
                DisplayToast("Se ha agregado el maximo de jugadores");
                return false;
            }
        }else {
            DisplayToast("Los campos deben estar completos");
            return false;
        }

    }
    private void DisplayToast(String mensaje) {
        Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}
