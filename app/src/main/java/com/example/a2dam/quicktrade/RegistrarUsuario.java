package com.example.a2dam.quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a2dam.quicktrade.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistrarUsuario extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    EditText nusuario, nom, ape, co, dir, contra;
    Button btnguarda;
    DatabaseReference bd;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        mAuth = FirebaseAuth.getInstance();

        nusuario = (EditText) findViewById(R.id.nombreusuariotxt);
        contra = (EditText) findViewById(R.id.contraseñatxt);
        nom = (EditText) findViewById(R.id.nombretxt);
        ape = (EditText) findViewById(R.id.apellidostxt);
        co = (EditText) findViewById(R.id.correotxt);
        dir = (EditText) findViewById(R.id.direcciontxt);
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
                        ArrayList<String> listacorreo = new ArrayList<String>();

                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                            Usuario usuario = datasnapshot.getValue(Usuario.class);

                            String nusuario = usuario.getUsuario();
                            String correo = usuario.getUsuario();
                            listausuarios.add(nusuario);
                            listacorreo.add(correo);

                        }

                        if (!listausuarios.contains(nusuario.getText().toString()) && !listacorreo.contains(co.getText().toString()) ) {

                            String usuario = nusuario.getText().toString();
                            String nombre = nom.getText().toString();
                            String password = contra.getText().toString();
                            String apellidos = ape.getText().toString();
                            String email = co.getText().toString();
                            String direccion = dir.getText().toString();

                            if (validateForm()) {

                                createAccount(email,password);

                                Usuario u = new Usuario(usuario, nombre, email, apellidos, direccion);
                                bd.child(usuario).setValue(u);

                                Toast.makeText(RegistrarUsuario.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegistrarUsuario.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegistrarUsuario.this, "Cuenta creada.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrarUsuario.this, "Error al crear la cuenta.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String user = nusuario.getText().toString();
        if (TextUtils.isEmpty(user)) {
            nusuario.setError("Required.");
            valid = false;
        } else {
            nusuario.setError(null);
        }

        String password = contra.getText().toString();
        if (TextUtils.isEmpty(password)) {
            contra.setError("Required.");
            valid = false;
        }else if(password.length()<6){
            contra.setError("Mínimo 6 carácteres.");
        } else {
            contra.setError(null);
        }

        String nombre = nom.getText().toString();
        if (TextUtils.isEmpty(nombre)) {
            nom.setError("Required.");
            valid = false;
        } else {
            nom.setError(null);
        }

        String apellidos = ape.getText().toString();
        if (TextUtils.isEmpty(apellidos)) {
            ape.setError("Required.");
            valid = false;
        } else {
            ape.setError(null);
        }

        String correo = co.getText().toString();
        if (TextUtils.isEmpty(correo)) {
            co.setError("Required.");
            valid = false;
        } else {
            co.setError(null);
        }

        String direccion = dir.getText().toString();
        if (TextUtils.isEmpty(direccion)) {
            dir.setError("Required.");
            valid = false;
        } else {
            dir.setError(null);
        }

        return valid;
    }
}
