package com.example.a2dam.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a2dam.quicktrade.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Modificar extends AppCompatActivity {

    EditText nusuario,nom,ape,co,dir;
    Button btnguarda,btnusu;
    DatabaseReference bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica);


        nusuario = (EditText) findViewById(R.id.nombreusuariotxt2);
        nom = (EditText) findViewById(R.id.nombretxt2);
        ape = (EditText) findViewById(R.id.apellidostxt2);
        co = (EditText) findViewById(R.id.correotxt2);
        dir =(EditText) findViewById(R.id.direcciontxt2);
        btnguarda = (Button) findViewById(R.id.botonregistrar2);
        btnusu = (Button) findViewById(R.id.buscarusu);

        bd = FirebaseDatabase.getInstance().getReference("usuarios");

        btnguarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usu = nusuario.getText().toString();

                if(!TextUtils.isEmpty(usu)){
                    Query q= bd.orderByChild("usuario").equalTo(usu);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                String clave=datasnapshot.getKey();
                                bd.child(clave).child("usuario").setValue(nusuario.getText().toString());

                                String nombre = nom.getText().toString();
                                if(!TextUtils.isEmpty(nombre)){
                                bd.child(clave).child("nombre").setValue(nom.getText().toString());
                                }

                                String correo = co.getText().toString();
                                if(!TextUtils.isEmpty(correo)) {
                                    bd.child(clave).child("correo").setValue(co.getText().toString());
                                }

                                String apellidos = ape.getText().toString();
                                if(!TextUtils.isEmpty(apellidos)) {
                                    bd.child(clave).child("apellidos").setValue(ape.getText().toString());
                                }

                                String direccion = dir.getText().toString();
                                if(!TextUtils.isEmpty(direccion)) {
                                    bd.child(clave).child("direccion").setValue(dir.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(Modificar.this, usu+" se ha modificado con éxito", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Modificar.this, "Debes de introducir un nombre de usuario valido", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query q=bd.orderByChild("usuario").equalTo(nusuario.getText().toString());

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            Usuario usuario = datasnapshot.getValue(Usuario.class);

                            String n = usuario.getNombre();
                            nom.setText(n);
                            String ap = usuario.getApellidos();
                            ape.setText(ap);
                            String c = usuario.getCorreo();
                            co.setText(c);
                            String d = usuario.getDireccion();
                            dir.setText(d);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
