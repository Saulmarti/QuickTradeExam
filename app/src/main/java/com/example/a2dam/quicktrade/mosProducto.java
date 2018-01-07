package com.example.a2dam.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

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

    DatabaseReference bd;
    ListView list;
    Spinner spinC,spinU;
    Button btnu,btnc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mos_producto);

        list = (ListView) findViewById(R.id.listviewp);
        spinC = (Spinner) findViewById(R.id.spinnercat);
        spinU = (Spinner) findViewById(R.id.spinnerusu);
        btnu = (Button) findViewById(R.id.buttonBuscaru);
        btnc = (Button) findViewById(R.id.buttonBuscarc);

        bd = FirebaseDatabase.getInstance().getReference("productos");

        bd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Usuario u = datasnapshot.getValue(Usuario.class);
                    String usu = u.getUsuario();
                    listado.add(usu);
                }

                adaptador = new ArrayAdapter<String>(mosProducto.this,android.R.layout.simple_list_item_1,listado);
                spinU.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        bd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotc) {

                ArrayAdapter<String> adaptadorc;
                ArrayList<String> listadoc = new ArrayList<String>();

                for(DataSnapshot datasnapshotc: dataSnapshotc.getChildren()){
                    Producto producto = datasnapshotc.getValue(Producto.class);

                    String categoria = producto.getCategoria();
                    listadoc.add(categoria);
                }

                adaptadorc = new ArrayAdapter<String>(mosProducto.this,android.R.layout.simple_list_item_1,listadoc);
                spinC.setAdapter(adaptadorc);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String u = spinU.getSelectedItem().toString();

        Query q= bd.orderByChild("usuario").equalTo(u);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adapter;
                ArrayList<String> lista = new ArrayList<String>();

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Producto p = datasnapshot.getValue(Producto.class);

                    String n = p.getNombre();
                    lista.add(n);

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

                            String n = p.getNombre();
                            lista.add(n);

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
