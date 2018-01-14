package com.example.a2dam.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2dam.quicktrade.Model.Producto;
import com.example.a2dam.quicktrade.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mosProducto extends AppCompatActivity {

    DatabaseReference bd,bdu;
    ListView list;
    Spinner spinC;
    Button btnc;
    String correo,nombre;
    TextView usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mos_producto);

        list = (ListView) findViewById(R.id.listviewp);
        spinC = (Spinner) findViewById(R.id.spinnercat);
        btnc = (Button) findViewById(R.id.buttonBuscarc);
        usu = (TextView) findViewById(R.id.txtusu);

        nombre = getIntent().getStringExtra("usuario");
        correo = getIntent().getStringExtra("email");

        usu.setText(nombre);

        bd = FirebaseDatabase.getInstance().getReference("productos");


        String t = "Tecnologia";
        String c = "Coche";
        String h = "Hogar";

        ArrayAdapter<String> adaptadorc;
        ArrayList<String> listadoc = new ArrayList<String>();

        listadoc.add(t);
        listadoc.add(c);
        listadoc.add(h);

        adaptadorc = new ArrayAdapter<String>(mosProducto.this,android.R.layout.simple_list_item_1,listadoc);
        spinC.setAdapter(adaptadorc);

        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c = spinC.getSelectedItem().toString();

                Query q= bd.orderByChild("categoria").equalTo(c);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayAdapter<String> adapter;
                        ArrayList<String> lista = new ArrayList<String>();

                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            Producto p = datasnapshot.getValue(Producto.class);
                            String us = p.getUsuario();

                            if(us.equals(nombre)) {
                                String n = p.getNombre();
                                lista.add(n);
                            }

                        }

                        adapter = new ArrayAdapter<String>(mosProducto.this,android.R.layout.simple_list_item_1,lista);
                        list.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });



    }
}
