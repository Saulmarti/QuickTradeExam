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

public class Registrar extends AppCompatActivity {

    EditText nusuario,nom,ape,co,dir;
    Button btnguarda;
    DatabaseReference bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        nusuario = (EditText) findViewById(R.id.nombreusuariotxt);
        nom = (EditText) findViewById(R.id.nombretxt);
        ape = (EditText) findViewById(R.id.apellidostxt);
        co = (EditText) findViewById(R.id.correotxt);
        dir =(EditText) findViewById(R.id.direcciontxt);
        btnguarda = (Button) findViewById(R.id.botonregistrar);

        bd = FirebaseDatabase.getInstance().getReference("usuarios");

        btnguarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bd = FirebaseDatabase.getInstance().getReference("usuarios");

                bd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<String> listausuarios = new ArrayList<String>();

                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            Usuario usuario = datasnapshot.getValue(Usuario.class);

                            String nusuario = usuario.getUsuario();
                            listausuarios.add(nusuario);

                        }

                       if(!listausuarios.contains(nusuario.getText().toString())){


                           String usuario = nusuario.getText().toString();
                           String nombre = nom.getText().toString();
                           String apellidos = ape.getText().toString();
                           String correo = co.getText().toString();
                           String direccion = dir.getText().toString();

                           if(!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellidos) &&
                                   !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(direccion)){

                               Usuario u = new Usuario(usuario,nombre,correo,apellidos,direccion);
                               bd.child(usuario).setValue(u);

                               Toast.makeText(Registrar.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                               nusuario.setText("");
                               nom.setText("");
                               ape.setText("");
                               co.setText("");
                               dir.setText("");

                           }else{
                               Toast.makeText(Registrar.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                           }
                       }
                        Toast.makeText(Registrar.this, "Nombre de usuario ya existente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
