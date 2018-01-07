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
import com.example.a2dam.quicktrade.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class modProducto extends AppCompatActivity {
    EditText nom,desc,cat,pre;
    Button btnmod,btnprod;
    DatabaseReference bd,bd2;
    Spinner spin;
    ArrayAdapter<String> adaptador;
    ArrayList<String> listado = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_producto);


        nom = (EditText) findViewById(R.id.nombreproductotxt2);
        desc = (EditText) findViewById(R.id.descripciontxt2);
        cat = (EditText) findViewById(R.id.categoriatxt2);
        pre = (EditText) findViewById(R.id.preciotxt2);
        btnmod = (Button) findViewById(R.id.botonregistrarp2);
        btnprod = (Button) findViewById(R.id.buscarprod);
        spin = (Spinner) findViewById(R.id.spinner2);


        bd = FirebaseDatabase.getInstance().getReference("productos");
        bd2 = FirebaseDatabase.getInstance().getReference("usuarios");



        btnmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = nom.getText().toString();

                if(!TextUtils.isEmpty(nombre)){
                    Query q= bd.orderByChild("nombre").equalTo(nombre);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                String clave=datasnapshot.getKey();
                                bd.child(clave).child("nombre").setValue(nom.getText().toString());

                                String descripcion = desc.getText().toString();
                                if(!TextUtils.isEmpty(descripcion)) {
                                    bd.child(clave).child("descripcion").setValue(desc.getText().toString());
                                }

                                String precio = pre.getText().toString();
                                if(!TextUtils.isEmpty(precio)) {
                                    bd.child(clave).child("precio").setValue(pre.getText().toString());
                                }

                                String categoria = cat.getText().toString();
                                if(!TextUtils.isEmpty(categoria)) {
                                    bd.child(clave).child("categoria").setValue(cat.getText().toString());
                                }


                                    bd.child(clave).child("usuario").setValue(spin.getSelectedItem().toString());

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(modProducto.this, nombre+" se ha modificado con éxito", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Query q=bd.orderByChild("nombre").equalTo(nom.getText().toString());

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            Producto producto = datasnapshot.getValue(Producto.class);
                            String n = producto.getNombre();
                            nom.setText(n);
                            String d = producto.getDescripcion();
                            desc.setText(d);
                            String c = producto.getCategoria();
                            cat.setText(c);
                            String p = producto.getPrecio();
                            pre.setText(p);
                            String nu = producto.getUsuario();
                            spin.setSelection(listado.indexOf(nu));
                        }
                        bd2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){

                                    Usuario u = datasnapshot.getValue(Usuario.class);
                                    String usu = u.getUsuario();
                                    listado.add(usu);

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        adaptador = new ArrayAdapter<String>(modProducto.this,android.R.layout.simple_list_item_1,listado);
                        spin.setAdapter(adaptador);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });


            }

    }

