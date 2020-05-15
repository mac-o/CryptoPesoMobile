package com.example.cryptopesomobile20;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


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

    @RequiresApi(api = Build.VERSION_CODES.P)
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

        final Executor executor = Executors.newSingleThreadExecutor();

        final BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Ingreasa tu Huella")
                .setSubtitle("CryptoPeso")
                .setDescription("Usar tu huella te brinda más seguridad a la hora de realizar transacciones.")
                .setNegativeButton("Cancel", executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).build();

        Button huella = findViewById(R.id.biometrics);

        final login activity = this;

        huella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(login.this, "Verificado", Toast.LENGTH_LONG).show();

                                Intent i3 = new Intent(login.this, MainActivity.class);
                                startActivity(i3);
                            }
                        });
                    }
                });
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