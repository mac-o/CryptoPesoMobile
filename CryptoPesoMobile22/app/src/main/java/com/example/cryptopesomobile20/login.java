package com.example.cryptopesomobile20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class login extends Activity {

    private static final String TAG = "CryptoPeso";
    DatabaseReference myDBR = FirebaseDatabase.getInstance().getReference();

    /*@Override
    public String[] databaseList() {
        return super.databaseList();
    }*/

    private FirebaseAuth myAuth;
    private EditText myEmailField, myPasswordField;
    private Button myLoginBtn;
    private FirebaseAuth.AuthStateListener myAuthListener;

    TextView contboton;
    TextView huellaboton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myEmailField = (EditText) findViewById(R.id.email);
        myPasswordField = (EditText) findViewById(R.id.password);


        // Comenzar Firebase Auth
        myAuth = FirebaseAuth.getInstance();

        myLoginBtn = (Button) findViewById(R.id.login);

        myAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(login.this, MainActivity.class));
                } else {
                    //Toast.makeText(login.this, "Informacion Incorrecta", Toast.LENGTH_SHORT).show();
                }

            }
        };

        myLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUsuario();
            }
        });

       contboton = (TextView) findViewById(R.id.continuar);

        contboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this, MainActivity.class);
                startActivity(i);
            }
        });

        huellaboton = (TextView) findViewById(R.id.biometrics);

        huellaboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(login.this, biometrics.class);
                startActivity(i2);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myAuth.addAuthStateListener(myAuthListener);
    }

    private void LoginUsuario() {

        String email = myEmailField.getText().toString();
        String password = myPasswordField.getText().toString();

        myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "singInWithEmail:onComplete:" + task.isSuccessful());

                        // error login, mensaje al user.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}