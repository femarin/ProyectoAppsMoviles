package com.curso.footballteamfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.curso.footballteamfinder.Objetos.Equipo;
import com.curso.footballteamfinder.Objetos.FirebaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class PartidosFragment extends Fragment {
    Map<String,Equipo> eqr= new HashMap<>(),distancias = new HashMap<>();
    Equipo equs;
    Vector<Float> distord = new Vector<>();
    Map<String,Float> eqcercanos = new HashMap<>();
    Button b1, b2, b3, b4, b5,bbuscar;
    Vector<String> aux = new Vector<String>();
    HashMap<String,Float> distordfinal= new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_partidos,container,false);
        final Vector<TextView> eqps = new Vector<TextView>();
        ((ActividadGeneral) getActivity()).t1 = (TextView) view.findViewById(R.id.tx1);
        ((ActividadGeneral) getActivity()).t1.setText("Nombre: "+"\n"+"Valoracion: "+"\n"+"Ciudad: "+"\n"+"Edad Promedio: ");
        eqps.add(0,((ActividadGeneral) getActivity()).t1);
        ((ActividadGeneral) getActivity()).t2 = (TextView) view.findViewById(R.id.tx2);
        ((ActividadGeneral) getActivity()).t2.setText("Nombre: "+"\n"+"Valoracion: "+"\n"+"Ciudad: "+"\n"+"Edad Promedio: ");
        eqps.add(1,((ActividadGeneral) getActivity()).t2);
        ((ActividadGeneral) getActivity()).t3 = (TextView) view.findViewById(R.id.tx3);
        ((ActividadGeneral) getActivity()).t3.setText("Nombre: "+"\n"+"Valoracion: "+"\n"+"Ciudad: "+"\n"+"Edad Promedio: ");
        eqps.add(2,((ActividadGeneral) getActivity()).t3);
        ((ActividadGeneral) getActivity()).t4 = (TextView) view.findViewById(R.id.tx4);
        ((ActividadGeneral) getActivity()).t4.setText("Nombre: "+"\n"+"Valoracion: "+"\n"+"Ciudad: "+"\n"+"Edad Promedio: ");
        eqps.add(3,((ActividadGeneral) getActivity()).t4);
        ((ActividadGeneral) getActivity()).t5 = (TextView) view.findViewById(R.id.tx5);
        ((ActividadGeneral) getActivity()).t5.setText("Nombre: "+"\n"+"Valoracion: "+"\n"+"Ciudad: "+"\n"+"Edad Promedio: ");
        eqps.add(4,((ActividadGeneral) getActivity()).t5);
        b1 = (Button) view.findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distancias.size()>0){
                    Intent in = new Intent(getActivity(),contactajugador.class);
                    in.putExtra("equiporival",aux.get(0));
                    startActivity(in);
                }
            }
        });
        b2 = (Button) view.findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distancias.size()>1){
                    Intent in = new Intent(getActivity(),contactajugador.class);
                    in.putExtra("equiporival",aux.get(1));
                    startActivity(in);
                }
            }
        });
        b3 = (Button) view.findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distancias.size()>2){
                    Intent in = new Intent(getActivity(),contactajugador.class);
                    in.putExtra("equiporival",aux.get(2));
                    startActivity(in);
                }
            }
        });
        b4 = (Button) view.findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distancias.size()>3){
                    Intent in = new Intent(getActivity(),contactajugador.class);
                    in.putExtra("equiporival",aux.get(3));
                    startActivity(in);
                }
            }
        });
        b5 = (Button) view.findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distancias.size()>4){
                    Intent in = new Intent(getActivity(),contactajugador.class);
                    in.putExtra("equiporival",aux.get(4));
                    startActivity(in);
                }
            }
        });
        bbuscar = (Button) view.findViewById(R.id.botbusc);
        bbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("numero de equipos : "+((ActividadGeneral) getActivity()).numequipos);
                Map<String,Float> asd = creadistancia(equs,eqr,distord,distordfinal);
                obtenerdistancia(equs,distord,eqr,distancias);
                Set<String> str = asd.keySet();
                Set<String> str2 = eqr.keySet();
                aux = new Vector<String>();
                int i = 0;
                for (String s :
                        str) {
                    for (String s2 :
                            str2) {
                        if(s.equals(s2)){
                            aux.add(i,s);
                            i++;
                        }
                    }

                }
                for (int j = 0;j<((ActividadGeneral) getActivity()).numequipos;j++){
                    Equipo a = eqr.get(aux.get(j));
                    eqps.get(j).setText("Nombre: "+a.getName()+"\n"+"Valoracion: "+a.getScore()+"\n"+"Ciudad: "+a.getCity()+"\n"+"Edad Promedio: "+a.getAvage());
                }

            }
        });
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();
        final DatabaseReference baseref = database.getReference(FirebaseReference.TEAMS_REFERENCE);
        baseref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int a = 0;
                        for (DataSnapshot d: dataSnapshot.getChildren()
                             ) {
                            if (!d.getKey().equals(userid)){
                                if (d.hasChild("Equipo")){
                                    Equipo eq = d.child("Equipo").getValue(Equipo.class);
                                    eqr.put(d.getKey(),eq);
                                    eqcercanos.put(d.getKey(),eq.getScore());
                                    a++;
                                    ((ActividadGeneral) getActivity()).numequipos = a;
                                }
                            }else {
                                equs = d.child("Equipo").getValue(Equipo.class);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return view;
    }


    Map creadistancia(Equipo eq, Map<String,Equipo> meq, Vector<Float> v, HashMap<String,Float> ordenado){
        v.removeAllElements();
        Set<String> s = meq.keySet();
        for (String str:
             s) {
            v.add(Math.abs(eq.getScore()-meq.get(str).getScore()));
            ordenado.put(str,Math.abs(eq.getScore()-meq.get(str).getScore()));
        }
        Collections.sort(v);
        Map<Integer, String> map = sortByValues(ordenado);
        return map;
    }
    void obtenerdistancia(Equipo eq, Vector<Float> v,Map<String,Equipo> meq,Map<String,Equipo> ord){
        ord.clear();
        for (String s :
                meq.keySet()) {
            int i = 0;
            int j = 0;
            while (i<((ActividadGeneral) getActivity()).numequipos)
            {
                if (Math.abs(eq.getScore()-meq.get(s).getScore()) == v.get(j) ){
                    ord.put(s,meq.get(s));
                    i++;
                }
                if (j==((ActividadGeneral) getActivity()).numequipos-1){
                    j=0;
                }else {
                    j++;
                }

            }
        }

    }

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }


}
