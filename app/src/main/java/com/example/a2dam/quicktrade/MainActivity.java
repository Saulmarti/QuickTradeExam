package com.example.a2dam.quicktrade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "EmailPassword";
    Button btnlog,btnregistro;
    EditText pastxt, emailtxt;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        pastxt = (EditText) findViewById(R.id.password);
        emailtxt = (EditText) findViewById(R.id.email);

        btnlog = (Button) findViewById(R.id.btnlogear);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = emailtxt.getText().toString();
                String password = pastxt.getText().toString();


                signIn(email,password);

            }
        });

        btnregistro = (Button) findViewById(R.id.registro);
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, RegistrarUsuario.class);

                startActivity(i);
            }
        });
    }




    private boolean validateForm() {
        boolean valid = true;

        String email = emailtxt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailtxt.setError("Required.");
            valid = false;
        } else {
            emailtxt.setError(null);
        }

        String password = pastxt.getText().toString();
        if (TextUtils.isEmpty(password)) {
            pastxt.setError("Required.");
            valid = false;
        }else if(password.length()<6){
            pastxt.setError("Mínimo 6 carácteres.");
        } else {
            pastxt.setError(null);
        }

        return valid;
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }


        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String correo = emailtxt.getText().toString();

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent i = new Intent(MainActivity.this, Principal.class);
                            i.putExtra("email",correo);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Autenticacion fallida.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
    }


}

