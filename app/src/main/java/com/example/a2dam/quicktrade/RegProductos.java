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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegProductos extends AppCompatActivity {

    Spinner spin;
    EditText nom,desc,cat,pre;
    Button btnreg;
    DatabaseReference bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        nom = (EditText) findViewById(R.id.nombreproductotxt);
        desc = (EditText) findViewById(R.id.descripciontxt);
        cat = (EditText) findViewById(R.id.categoriatxt);
        pre = (EditText) findViewById(R.id.preciotxt);
        spin = (Spinner) findViewById(R.id.spinner);
        btnreg = (Button) findViewById(R.id.botonregistrarp);
        bd = FirebaseDatabase.getInstance().getReference("usuarios");

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

                adaptador = new ArrayAdapter<String>(RegProductos.this,android.R.layout.simple_list_item_1,listado);
                spin.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnreg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                String nombre = nom.getText().toString();
                String usuario = spin.getSelectedItem().toString();
                String descripcion = desc.getText().toString();
                String precio = pre.getText().toString();
                String categoria = cat.getText().toString();

                if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) &&
                        !TextUtils.isEmpty(categoria) && !TextUtils.isEmpty(precio)){

                    Producto p = new Producto(nombre,usuario,descripcion,precio,categoria);
                    DatabaseReference bd2 = FirebaseDatabase.getInstance().getReference("productos");
                    bd2.child(nombre).setValue(p);

                    Toast.makeText(RegProductos.this, "Producto añadido", Toast.LENGTH_LONG).show();

                    nom.setText("");
                    desc.setText("");
                    cat.setText("");
                    pre.setText("");

                }else{
                    Toast.makeText(RegProductos.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }

    });
}}
