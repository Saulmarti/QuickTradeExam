package com.example.a2dam.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a2dam.quicktrade.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mosUsuario extends AppCompatActivity {


    DatabaseReference bd;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra);


        list = (ListView) findViewById(R.id.listview);
        bd = FirebaseDatabase.getInstance().getReference("usuarios");

        bd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adapter;
                ArrayList<String> listausuarios = new ArrayList<String>();

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Usuario usuario = datasnapshot.getValue(Usuario.class);

                    String nusuario = usuario.getUsuario();
                    listausuarios.add(nusuario);

                }

                adapter = new ArrayAdapter<String>(mosUsuario.this,android.R.layout.simple_list_item_1,listausuarios);
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





            }

    }

