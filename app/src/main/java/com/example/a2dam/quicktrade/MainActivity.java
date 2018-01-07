package com.example.a2dam.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button btnreg,btnmod,btnmos;
    int Request_ValueProd=0, Request_ValueUsu=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnmod = (Button) findViewById(R.id.btnMod);
        btnreg = (Button) findViewById(R.id.btnReg);
        btnmos = (Button) findViewById(R.id.btnMostrar);

        btnmos.setOnClickListener(this);

        btnmod.setOnClickListener(this);

        btnreg.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.btnMostrar){

            PopupMenu popup = new PopupMenu(MainActivity.this, btnmos);

            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch(item.getItemId())
                    {
                        case R.id.regprod:
                            Intent i = new Intent(MainActivity.this, mosProducto.class);
                            startActivityForResult(i, Request_ValueProd);
                            break;
                        case R.id.regusu:
                            Intent i2 = new Intent(MainActivity.this, Mostrar.class);
                            startActivityForResult(i2, Request_ValueUsu);
                            break;
                    }

                    return true;
                }
            });

            popup.show();
        }

        if(view.getId()==R.id.btnMod){

            PopupMenu popup = new PopupMenu(MainActivity.this, btnmod);

            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch(item.getItemId())
                    {
                        case R.id.regprod:
                            Intent i = new Intent(MainActivity.this, modProducto.class);
                            startActivityForResult(i, Request_ValueProd);
                            break;
                        case R.id.regusu:
                            Intent i2 = new Intent(MainActivity.this, Modificar.class);
                            startActivityForResult(i2, Request_ValueUsu);
                            break;
                    }

                    return true;
                }
            });

            popup.show();
        }

        if(view.getId()==R.id.btnReg){

            PopupMenu popup = new PopupMenu(MainActivity.this, btnreg);

            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch(item.getItemId())
                    {
                        case R.id.regprod:
                            Intent i = new Intent(MainActivity.this, RegProductos.class);
                            startActivityForResult(i, Request_ValueProd);
                            break;
                        case R.id.regusu:
                            Intent i2 = new Intent(MainActivity.this, Registrar.class);
                            startActivityForResult(i2, Request_ValueUsu);
                            break;
                    }

                    return true;
                }
            });

            popup.show();
        }
    }

    }

