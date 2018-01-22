package com.example.a2dam.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a2dam.quicktrade.Model.Producto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class modProducto extends AppCompatActivity {
    EditText nom,desc,pre;
    Button btnmod,btnprod,btnborr;
    DatabaseReference bd,bd2;
    Spinner spin,cat;
    ArrayAdapter<String> adaptador;
    ArrayList<String> listado = new ArrayList<String>();
    String t = "Tecnologia";
    String c = "Coche";
    String h = "Hogar";

    String nombreusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_producto);

        nombreusuario = getIntent().getStringExtra("usuario");

        desc = (EditText) findViewById(R.id.descripciontxt2);
        cat = (Spinner) findViewById(R.id.spinnerCatr2);
        pre = (EditText) findViewById(R.id.preciotxt2);
        btnmod = (Button) findViewById(R.id.botonregistrarp2);
        btnprod = (Button) findViewById(R.id.buscarprod);
        btnborr = (Button) findViewById(R.id.buttonBorrar);
        spin = (Spinner) findViewById(R.id.nombreproducto);


        bd = FirebaseDatabase.getInstance().getReference("productos");
        bd2 = FirebaseDatabase.getInstance().getReference("usuarios");


        ArrayAdapter<String> adaptador2;
        ArrayList<String> list = new ArrayList<String>();
        list.add(t);
        list.add(c);
        list.add(h);
        adaptador2 = new ArrayAdapter<String>(modProducto.this,android.R.layout.simple_list_item_1,list);
        cat.setAdapter(adaptador2);



        Query q= bd.orderByChild("usuario").equalTo(nombreusuario);
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

                adapter = new ArrayAdapter<String>(modProducto.this,android.R.layout.simple_list_item_1,lista);
                spin.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        btnmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = spin.getSelectedItem().toString();

                    Query q= bd.orderByChild("nombre").equalTo(nombre);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                String clave=datasnapshot.getKey();
                                bd.child(clave).child("nombre").setValue(spin.getSelectedItem().toString());

                                String descripcion = desc.getText().toString();
                                if(!TextUtils.isEmpty(descripcion)) {
                                    bd.child(clave).child("descripcion").setValue(desc.getText().toString());
                                }

                                String precio = pre.getText().toString();
                                if(!TextUtils.isEmpty(precio)) {
                                    bd.child(clave).child("precio").setValue(pre.getText().toString());
                                }

                                String categoria = cat.getSelectedItem().toString();
                                if(!TextUtils.isEmpty(categoria)) {
                                    bd.child(clave).child("categoria").setValue(cat.getSelectedItem().toString());
                                }

                                    bd.child(clave).child("usuario").setValue(nombreusuario);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(modProducto.this, nombre+" se ha modificado con éxito", Toast.LENGTH_LONG).show();


            }
        });

        btnprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Query q=bd.orderByChild("nombre").equalTo(spin.getSelectedItem().toString());

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            Producto producto = datasnapshot.getValue(Producto.class);
                            String d = producto.getDescripcion();
                            desc.setText(d);
                            String c = producto.getCategoria();
                            String p = producto.getPrecio();
                            pre.setText(p);
                            String nu = producto.getNombre();
                            spin.setSelection(listado.indexOf(nu));
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        btnborr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference().child("productos");
                DatabaseReference currentUserBD = mDatabase.child(nom.getText().toString());
                currentUserBD.removeValue();

                Toast.makeText(modProducto.this, "Producto borrado", Toast.LENGTH_LONG).show();

                nom.setText("");
                desc.setText("");
                pre.setText("");

            }
        });

            }

    }

