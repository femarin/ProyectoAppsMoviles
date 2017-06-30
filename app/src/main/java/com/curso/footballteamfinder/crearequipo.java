package com.curso.footballteamfinder;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class crearequipo extends AppCompatActivity {
    String[] coloressel=new String[2],ubicaciones;
    String[] colores;
    String ubic;
    EditText nteam,numcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearequipoxml);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();
        final DatabaseReference baseref = database.getReference(FirebaseReference.TEAMS_REFERENCE);
        Spinner s1 = (Spinner) findViewById(R.id.colx1);
        Spinner s11 = (Spinner) findViewById(R.id.colx2), s2 = (Spinner) findViewById(R.id.ubx);
        colores = getResources().getStringArray(R.array.colores);
        ubicaciones = getResources().getStringArray(R.array.ubicaciones);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,colores);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,ubicaciones);

        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coloressel[0] = (colores[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s11.setAdapter(adapter);
        s11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coloressel[1] = colores[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s2.setAdapter(adapter2);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ubic = ubicaciones[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nteam = (EditText) findViewById(R.id.nomeq);
        numcon = (EditText) findViewById(R.id.numc);
        Button b = (Button) findViewById(R.id.botcr);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entradascorrectas()){
                    Equipo e = new Equipo();
                    e.setName(nteam.getText().toString());
                    e.setColor1(coloressel[0]);
                    e.setColor2(coloressel[1]);
                    e.setCity(ubic);
                    e.setNumcontacto("+569"+numcon.getText().toString());
                    baseref.child(userid).child("Equipo").setValue(e);
                    finish();
                }
            }
        });
    }


    boolean entradascorrectas(){
        if (!nteam.getText().toString().equals("")){
            if (!coloressel[0].equals(coloressel[1])){
                if (numcon.getText().toString().length()==8){
                    return true;
                }else {
                    DisplayToast("Numero de celular incorrecto, ingrese numero sin +569");
                    return false;
                }

            }else {
                DisplayToast("Colores deben ser distintos");
                return false;
            }
        }else {
            DisplayToast("Equipo no posee nombre");
            return false;
        }

    }
    private void DisplayToast(String mensaje) {
        Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}
