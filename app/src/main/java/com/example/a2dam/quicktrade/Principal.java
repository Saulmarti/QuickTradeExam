package com.example.a2dam.quicktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.a2dam.quicktrade.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Principal extends AppCompatActivity {

    private TextView mTextMessage,textousuario;
    int Request_ValueProd=0, Request_ValueUsu=1;
    BottomNavigationView navigation;
    String nombreusuario, correo;
    private DatabaseReference db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        mTextMessage = (TextView) findViewById(R.id.message);
        textousuario = (TextView) findViewById(R.id.txtusuario);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        correo = getIntent().getStringExtra("email");
        obtenerUsuario(correo);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navMod:

                    PopupMenu popupmod = new PopupMenu(Principal.this, navigation);

                    popupmod.getMenuInflater().inflate(R.menu.menu, popupmod.getMenu());

                    popupmod.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch(item.getItemId())
                            {
                                case R.id.regprod:
                                    Intent i = new Intent(Principal.this, modProducto.class);
                                    i.putExtra("usuario",nombreusuario);
                                    startActivityForResult(i, Request_ValueProd);
                                    break;
                                case R.id.regusu:
                                    Intent i2 = new Intent(Principal.this, modUsuario.class);
                                    i2.putExtra("usuario",nombreusuario);
                                    startActivityForResult(i2, Request_ValueUsu);
                                    break;
                            }

                            return true;
                        }
                    });

                    popupmod.show();
                    return true;



                case R.id.navMos:

                    PopupMenu popupmos = new PopupMenu(Principal.this, navigation);

                    popupmos.getMenuInflater().inflate(R.menu.menu, popupmos.getMenu());

                    popupmos.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch(item.getItemId())
                            {
                                case R.id.regprod:
                                    Intent i = new Intent(Principal.this, mosProducto.class);
                                    i.putExtra("usuario",nombreusuario);
                                    startActivityForResult(i, Request_ValueProd);
                                    break;
                                case R.id.regusu:
                                    Intent i2 = new Intent(Principal.this, mosUsuario.class);
                                    startActivityForResult(i2, Request_ValueUsu);
                                    break;
                            }

                            return true;
                        }
                    });

                    popupmos.show();
                    return true;


                case R.id.navReg:


                                    Intent i = new Intent(Principal.this, RegProductos.class);
                                    i.putExtra("usuario",nombreusuario);
                                    startActivity(i);



            }
            return false;
        }

    };
        public void obtenerUsuario(String correo){

            db = FirebaseDatabase.getInstance().getReference("usuarios");

            Query q = db.orderByChild("correo").equalTo(correo);

            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot datasnapshot: dataSnapshot.getChildren()){

                        Usuario u = datasnapshot.getValue(Usuario.class);
                        nombreusuario= u.getUsuario();

                        textousuario.setText(nombreusuario);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

}
